package com.burakkurucay.connex.dto.profile.personal.project;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CreatePersonalProjectRequest {

    @NotBlank(message = "Project name is required")
    private String name;

    private String shortDescription;
    private String description;
    private String link;
    private LocalDate startDate;
    private LocalDate endDate;

    public CreatePersonalProjectRequest() {
    }

    public CreatePersonalProjectRequest(String name, String shortDescription, String description, String link,
            LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public static CreatePersonalProjectRequestBuilder builder() {
        return new CreatePersonalProjectRequestBuilder();
    }

    public static class CreatePersonalProjectRequestBuilder {
        private String name;
        private String shortDescription;
        private String description;
        private String link;
        private LocalDate startDate;
        private LocalDate endDate;

        CreatePersonalProjectRequestBuilder() {
        }

        public CreatePersonalProjectRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CreatePersonalProjectRequestBuilder shortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public CreatePersonalProjectRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CreatePersonalProjectRequestBuilder link(String link) {
            this.link = link;
            return this;
        }

        public CreatePersonalProjectRequestBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public CreatePersonalProjectRequestBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public CreatePersonalProjectRequest build() {
            return new CreatePersonalProjectRequest(name, shortDescription, description, link, startDate, endDate);
        }
    }
}
