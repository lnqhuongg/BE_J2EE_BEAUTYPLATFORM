package com.beautyplatform.beauty_service.Helper;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
}
