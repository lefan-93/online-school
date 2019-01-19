package net.thumbtack.school.hiring.request;

import net.thumbtack.school.hiring.model.Requirement;

import java.util.ArrayList;
import java.util.List;

public class VacancyDtoRequest {

    private String jobTitle;
    private int salary;
    private List<Requirement> requirements;
    private boolean active = true;


    VacancyDtoRequest(String jobTitle, int salary) {
        this.jobTitle = jobTitle;
        this.salary = salary;
        requirements = new ArrayList<>();
    }

    public void setRequirement(Requirement requirement) {
        requirements.add(requirement);
    }

    public int getSalary() {
        return salary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public boolean isActive() {
        return active;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }
}
