package com.burakkurucay.connex.dto.profile.personal.project;

import java.time.LocalDate;

public class EditPersonalProjectRequest {

    private String name;
    private String shortDescription;
    private String description;
    private String link;
    private LocalDate startDate;
    private LocalDate endDate;

    public EditPersonalProjectRequest() {
    }

    public EditPersonalProjectRequest(String name, String shortDescription, String description, String link,
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

    public static EditPersonalProjectRequestBuilder builder() {
        return new EditPersonalProjectRequestBuilder();
    }

    public static class EditPersonalProjectRequestBuilder {
        private String name;
        private String shortDescription;
        private String description;
        private String link;
        private LocalDate startDate;
        private LocalDate endDate;

        EditPersonalProjectRequestBuilder() {
        }

        public EditPersonalProjectRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EditPersonalProjectRequestBuilder shortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public EditPersonalProjectRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public EditPersonalProjectRequestBuilder link(String link) {
            this.link = link;
            return this;
        }

        public EditPersonalProjectRequestBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public EditPersonalProjectRequestBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public EditPersonalProjectRequest build() {
            return new EditPersonalProjectRequest(name, shortDescription, description, link, startDate, endDate);
        }
    }
}
