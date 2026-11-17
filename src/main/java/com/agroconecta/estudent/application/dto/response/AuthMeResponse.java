package com.agroconecta.estudent.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthMeResponse(
        boolean authenticated,
        AuthUserResponse user,
        String csrfToken
) {
    public static AuthMeResponse unauthenticated() {
        return new AuthMeResponse(false, null, null);
    }

    public static AuthMeResponse authenticated(AuthUserResponse user, String csrfToken) {
        return new AuthMeResponse(true, user, csrfToken);
    }
}
