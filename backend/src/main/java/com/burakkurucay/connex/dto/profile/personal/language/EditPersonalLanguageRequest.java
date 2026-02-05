package com.burakkurucay.connex.dto.profile.personal.language;

public class EditPersonalLanguageRequest {

    private String language;
    private String level;

    public EditPersonalLanguageRequest() {
    }

    public EditPersonalLanguageRequest(String language, String level) {
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

    public static EditPersonalLanguageRequestBuilder builder() {
        return new EditPersonalLanguageRequestBuilder();
    }

    public static class EditPersonalLanguageRequestBuilder {
        private String language;
        private String level;

        EditPersonalLanguageRequestBuilder() {
        }

        public EditPersonalLanguageRequestBuilder language(String language) {
            this.language = language;
            return this;
        }

        public EditPersonalLanguageRequestBuilder level(String level) {
            this.level = level;
            return this;
        }

        public EditPersonalLanguageRequest build() {
            return new EditPersonalLanguageRequest(language, level);
        }
    }
}
