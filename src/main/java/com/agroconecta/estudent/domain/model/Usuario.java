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

     Long idUsuario;
     Integer idRol;
     String username;
     String email;
     String passwordHash;
     String nombres;
     String apellidos;
     Long idComprador;
     LocalDateTime ultimoAcceso;

    @Builder.Default
     boolean activo = true;
}