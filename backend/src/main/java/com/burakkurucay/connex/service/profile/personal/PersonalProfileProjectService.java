package com.burakkurucay.connex.service.profile.personal;

import com.burakkurucay.connex.dto.profile.personal.project.CreatePersonalProjectRequest;
import com.burakkurucay.connex.dto.profile.personal.project.EditPersonalProjectRequest;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.project.PersonalProfileProject;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileProjectRepository;
import com.burakkurucay.connex.service.profile.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonalProfileProjectService {

    private final PersonalProfileProjectRepository projectRepository;
    private final ProfileService profileService;

    public PersonalProfileProjectService(
            PersonalProfileProjectRepository projectRepository,
            ProfileService profileService) {
        this.projectRepository = projectRepository;
        this.profileService = profileService;
    }

    public List<PersonalProfileProject> getMyProjects() {
        PersonalProfile profile = profileService.getMyPersonalProfile();
        return projectRepository.findAllByProfile(profile);
    }

    public List<PersonalProfileProject> getProjectsByProfile(PersonalProfile profile) {
        return projectRepository.findAllByProfile(profile);
    }

    public PersonalProfileProject addProject(CreatePersonalProjectRequest request) {
        PersonalProfile profile = profileService.getMyPersonalProfile();

        PersonalProfileProject project = PersonalProfileProject.builder()
                .profile(profile)
                .name(request.getName())
                .shortDescription(request.getShortDescription())
                .description(request.getDescription())
                .link(request.getLink())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        return projectRepository.save(project);
    }

    public PersonalProfileProject updateProject(Long projectId, EditPersonalProjectRequest request) {
        PersonalProfile profile = profileService.getMyPersonalProfile();

        PersonalProfileProject project = projectRepository.findByIdAndProfile(projectId, profile)
                .orElseThrow(() -> new BusinessException("Project not found", ErrorCode.PROFILE_PROJECT_NOT_FOUND));

        if (request.getName() != null) {
            project.setName(request.getName());
        }
        if (request.getShortDescription() != null) {
            project.setShortDescription(request.getShortDescription());
        }
        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }
        if (request.getLink() != null) {
            project.setLink(request.getLink());
        }
        if (request.getStartDate() != null) {
            project.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            project.setEndDate(request.getEndDate());
        }

        return projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        PersonalProfile profile = profileService.getMyPersonalProfile();

        PersonalProfileProject project = projectRepository.findByIdAndProfile(projectId, profile)
                .orElseThrow(() -> new BusinessException("Project not found", ErrorCode.PROFILE_PROJECT_NOT_FOUND));

        projectRepository.delete(project);
    }
}
