package com.burakkurucay.connex.controller;

import com.burakkurucay.connex.dto.common.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestSecureController {

    @GetMapping("/secure")
    public ApiResponse<String> secureEndpoint(
        @AuthenticationPrincipal Object principal
    ) {
        // principal = JwtAuthenticationFilter’da koyduğumuz şey (userId)
        return ApiResponse.success(
            "Access granted. principal=" + principal
        );
    }
}
