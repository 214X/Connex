package com.burakkurucay.connex.dto.profile.personal.skill;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreatePersonalSkillRequest {

    @NotBlank(message = "Skill name is required")
    private String name;

    private String description;

    @Min(value = 0, message = "Level must be at least 0")
    @Max(value = 10, message = "Level must be at most 10")
    private Integer level;

    public CreatePersonalSkillRequest() {
    }

    public CreatePersonalSkillRequest(String name, String description, Integer level) {
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

    public static CreatePersonalSkillRequestBuilder builder() {
        return new CreatePersonalSkillRequestBuilder();
    }

    public static class CreatePersonalSkillRequestBuilder {
        private String name;
        private String description;
        private Integer level;

        CreatePersonalSkillRequestBuilder() {
        }

        public CreatePersonalSkillRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CreatePersonalSkillRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CreatePersonalSkillRequestBuilder level(Integer level) {
            this.level = level;
            return this;
        }

        public CreatePersonalSkillRequest build() {
            return new CreatePersonalSkillRequest(name, description, level);
        }
    }
}
