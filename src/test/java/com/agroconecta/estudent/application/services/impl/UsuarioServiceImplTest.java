package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.UsuarioRequest;
import com.agroconecta.estudent.application.dto.response.UsuarioResponse;
import com.agroconecta.estudent.application.mapper.UsuarioMapper;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParRolEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.UsuarioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.CompradorRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParRolRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ParRolRepository parRolRepository;

    @Mock
    private CompradorRepository compradorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UsuarioMapper usuarioMapper;
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        usuarioMapper = Mappers.getMapper(UsuarioMapper.class);
        usuarioService = new UsuarioServiceImpl(
                usuarioRepository,
                parRolRepository,
                compradorRepository,
                usuarioMapper,
                passwordEncoder
        );
    }

    @Test
    @DisplayName("Al editar usuario sin enviar nombres ni apellidos, se deben conservar los nombres reales preexistentes")
    void actualizar_sinNombresNiApellidos_conservaNombresReales() {
        UsuarioEntity existente = new UsuarioEntity();
        existente.setIdUsuario(10L);
        existente.setUsername("juanperez");
        existente.setEmail("juan@agroconecta.com");
        existente.setNombres("Juan Carlos");
        existente.setApellidos("Pérez Gómez");
        existente.setActivo(true);

        ParRolEntity rol = new ParRolEntity();
        rol.setIdRol(2);
        rol.setCodigo("OPERADOR");
        rol.setNombre("Operador");
        existente.setRol(rol);

        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(existente));
        when(parRolRepository.findById(2)).thenReturn(Optional.of(rol));
        when(usuarioRepository.existsByUsernameAndIdUsuarioNot("juanperez_mod", 10L)).thenReturn(false);
        when(usuarioRepository.existsByEmailAndIdUsuarioNot("juan_mod@agroconecta.com", 10L)).thenReturn(false);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioRequest request = new UsuarioRequest(
                2,
                "juanperez_mod",
                "juan_mod@agroconecta.com",
                null,
                null, // nombres omitidos (null)
                null, // apellidos omitidos (null)
                null,
                true
        );

        UsuarioResponse response = usuarioService.actualizar(10L, request);

        assertEquals("juanperez_mod", response.username());
        assertEquals("juan_mod@agroconecta.com", response.email());
        assertEquals("Juan Carlos", response.nombres());
        assertEquals("Pérez Gómez", response.apellidos());
        assertEquals(2, response.idRol());
    }
}
