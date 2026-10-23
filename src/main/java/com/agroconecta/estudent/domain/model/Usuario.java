package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends BaseModel {

    private Long idUsuario;
    private Integer idRol;
    private String username;
    private String email;
    private String passwordHash;
    private String nombres;
    private String apellidos;
    private Long idComprador;
    private LocalDateTime ultimoAcceso;

    @Builder.Default
    private boolean activo = true;
}