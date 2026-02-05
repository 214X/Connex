package com.burakkurucay.connex.service.profile.personal;

import com.burakkurucay.connex.dto.profile.personal.project.CreatePersonalProjectRequest;
import com.burakkurucay.connex.dto.profile.personal.project.EditPersonalProjectRequest;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.project.PersonalProfileProject;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonalProfileProjectService {

    private final PersonalProfileProjectRepository projectRepository;
    private final PersonalProfileService personalProfileService;

    public PersonalProfileProjectService(
            PersonalProfileProjectRepository projectRepository,
            PersonalProfileService personalProfileService) {
        this.projectRepository = projectRepository;
        this.personalProfileService = personalProfileService;
    }

    public List<PersonalProfileProject> getMyProjects() {
        PersonalProfile profile = personalProfileService.getMyProfile();
        return projectRepository.findAllByProfile(profile);
    }

    public List<PersonalProfileProject> getProjectsByProfile(PersonalProfile profile) {
        return projectRepository.findAllByProfile(profile);
    }

    public PersonalProfileProject addProject(CreatePersonalProjectRequest request) {
        PersonalProfile profile = personalProfileService.getMyProfile();

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
        PersonalProfile profile = personalProfileService.getMyProfile();

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
        PersonalProfile profile = personalProfileService.getMyProfile();

        PersonalProfileProject project = projectRepository.findByIdAndProfile(projectId, profile)
                .orElseThrow(() -> new BusinessException("Project not found", ErrorCode.PROFILE_PROJECT_NOT_FOUND));

        projectRepository.delete(project);
    }
}
