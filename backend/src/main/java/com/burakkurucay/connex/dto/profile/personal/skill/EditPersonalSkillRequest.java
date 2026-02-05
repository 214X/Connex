package com.burakkurucay.connex.dto.profile.personal.skill;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class EditPersonalSkillRequest {

    private String name;
    private String description;

    @Min(value = 0, message = "Level must be at least 0")
    @Max(value = 10, message = "Level must be at most 10")
    private Integer level;

    public EditPersonalSkillRequest() {
    }

    public EditPersonalSkillRequest(String name, String description, Integer level) {
        this.name = name;
        this.description = description;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public static EditPersonalSkillRequestBuilder builder() {
        return new EditPersonalSkillRequestBuilder();
    }

    public static class EditPersonalSkillRequestBuilder {
        private String name;
        private String description;
        private Integer level;

        EditPersonalSkillRequestBuilder() {
        }

        public EditPersonalSkillRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EditPersonalSkillRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public EditPersonalSkillRequestBuilder level(Integer level) {
            this.level = level;
            return this;
        }

        public EditPersonalSkillRequest build() {
            return new EditPersonalSkillRequest(name, description, level);
        }
    }
}
