package net.thumbtack.school.hiring.request;

import net.thumbtack.school.hiring.model.Requirement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddRequirementEmployerDtoRequest {

    private UUID token;
    private String jobTitle;
    private List<Requirement> requirements;

    public AddRequirementEmployerDtoRequest (UUID token, String jobTitle){
        this.token = token;
        this.jobTitle = jobTitle;
        requirements = new ArrayList<>();
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void setRequirement(Requirement requirement) {
        requirements.add(requirement);
    }
}
