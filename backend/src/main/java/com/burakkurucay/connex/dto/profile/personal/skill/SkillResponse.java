package com.burakkurucay.connex.dto.profile.personal.skill;

import com.burakkurucay.connex.entity.profile.personal.skill.PersonalProfileSkill;

public class SkillResponse {
    private Long id;
    private String name;
    private String description;
    private Integer level;

    public SkillResponse() {
    }

    public SkillResponse(Long id, String name, String description, Integer level) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static SkillResponse from(PersonalProfileSkill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .description(skill.getDescription())
                .level(skill.getLevel())
                .build();
    }

    public static SkillResponseBuilder builder() {
        return new SkillResponseBuilder();
    }

    public static class SkillResponseBuilder {
        private Long id;
        private String name;
        private String description;
        private Integer level;

        SkillResponseBuilder() {
        }

        public SkillResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SkillResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SkillResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public SkillResponseBuilder level(Integer level) {
            this.level = level;
            return this;
        }

        public SkillResponse build() {
            return new SkillResponse(id, name, description, level);
        }
    }
}
