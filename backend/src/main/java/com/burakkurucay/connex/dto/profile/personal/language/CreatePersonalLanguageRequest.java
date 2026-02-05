package com.burakkurucay.connex.dto.profile.personal.language;

import jakarta.validation.constraints.NotBlank;

public class CreatePersonalLanguageRequest {

    @NotBlank(message = "Language is required")
    private String language;

    @NotBlank(message = "Level is required")
    private String level;

    public CreatePersonalLanguageRequest() {
    }

    public CreatePersonalLanguageRequest(String language, String level) {
        this.language = language;
        this.level = level;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public static CreatePersonalLanguageRequestBuilder builder() {
        return new CreatePersonalLanguageRequestBuilder();
    }

    public static class CreatePersonalLanguageRequestBuilder {
        private String language;
        private String level;

        CreatePersonalLanguageRequestBuilder() {
        }

        public CreatePersonalLanguageRequestBuilder language(String language) {
            this.language = language;
            return this;
        }

        public CreatePersonalLanguageRequestBuilder level(String level) {
            this.level = level;
            return this;
        }

        public CreatePersonalLanguageRequest build() {
            return new CreatePersonalLanguageRequest(language, level);
        }
    }
}
