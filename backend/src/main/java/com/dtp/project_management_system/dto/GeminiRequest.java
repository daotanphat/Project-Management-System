package com.dtp.project_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeminiRequest {
    private Content[] contents;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private Part[] parts;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Part {
        private String text;
    }
}
