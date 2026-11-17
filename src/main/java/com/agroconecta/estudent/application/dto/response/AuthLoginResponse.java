package com.agroconecta.estudent.application.dto.response;

public record AuthLoginResponse(
        AuthUserResponse user,
        String csrfToken
) {}
