package com.burakkurucay.connex.dto.profile.personal.language;

import com.burakkurucay.connex.entity.profile.personal.language.PersonalProfileLanguage;

public class LanguageResponse {
    private Long id;
    private String language;
    private String level;

    public LanguageResponse() {
    }

    public LanguageResponse(Long id, String language, String level) {
        this.id = id;
        this.language = language;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static LanguageResponse from(PersonalProfileLanguage language) {
        return LanguageResponse.builder()
                .id(language.getId())
                .language(language.getLanguage())
                .level(language.getLevel())
                .build();
    }

    public static LanguageResponseBuilder builder() {
        return new LanguageResponseBuilder();
    }

    public static class LanguageResponseBuilder {
        private Long id;
        private String language;
        private String level;

        LanguageResponseBuilder() {
        }

        public LanguageResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public LanguageResponseBuilder language(String language) {
            this.language = language;
            return this;
        }

        public LanguageResponseBuilder level(String level) {
            this.level = level;
            return this;
        }

        public LanguageResponse build() {
            return new LanguageResponse(id, language, level);
        }
    }
}
