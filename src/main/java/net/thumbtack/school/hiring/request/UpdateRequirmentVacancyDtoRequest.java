package net.thumbtack.school.hiring.request;

import net.thumbtack.school.hiring.model.Requirement;

import java.util.UUID;

public class UpdateRequirmentVacancyDtoRequest {

    private UUID token;
    private String jobTitle;
    private Requirement requirement;
    private Requirement updateRequirement;

    public UpdateRequirmentVacancyDtoRequest(UUID token, String jobTitle, Requirement requirement, Requirement requirement1) {
        this.token = token;
        this.jobTitle = jobTitle;
        this.requirement = requirement;
        this.updateRequirement = requirement1;
    }
}
