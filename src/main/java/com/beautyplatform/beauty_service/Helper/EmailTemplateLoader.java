package com.beautyplatform.beauty_service.Helper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class EmailTemplateLoader {

    public String loadTemplate(String fileName) {
        try {
            ClassPathResource resource =
                    new ClassPathResource("email-templates/" + fileName);

            return new String(resource.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Không load được template: " + fileName);
        }
    }
}

