package com.burakkurucay.connex.dto.profile.personal.project;

import com.burakkurucay.connex.entity.profile.personal.project.PersonalProfileProject;

import java.time.LocalDate;

public class ProjectResponse {
    private Long id;
    private String name;
    private String shortDescription;
    private String description;
    private String link;
    private LocalDate startDate;
    private LocalDate endDate;

    public ProjectResponse() {
    }

    public ProjectResponse(Long id, String name, String shortDescription, String description, String link,
            LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static ProjectResponse from(PersonalProfileProject project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .shortDescription(project.getShortDescription())
                .description(project.getDescription())
                .link(project.getLink())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public static ProjectResponseBuilder builder() {
        return new ProjectResponseBuilder();
    }

    public static class ProjectResponseBuilder {
        private Long id;
        private String name;
        private String shortDescription;
        private String description;
        private String link;
        private LocalDate startDate;
        private LocalDate endDate;

        ProjectResponseBuilder() {
        }

        public ProjectResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProjectResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProjectResponseBuilder shortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public ProjectResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProjectResponseBuilder link(String link) {
            this.link = link;
            return this;
        }

        public ProjectResponseBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ProjectResponseBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public ProjectResponse build() {
            return new ProjectResponse(id, name, shortDescription, description, link, startDate, endDate);
        }
    }
}
