package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.response.BootstrapResponse;
import org.springframework.security.core.Authentication;

public interface BootstrapService {
    BootstrapResponse obtenerSnapshot(Authentication authentication);
}
