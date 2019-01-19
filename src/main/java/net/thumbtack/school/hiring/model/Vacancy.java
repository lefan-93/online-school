package net.thumbtack.school.hiring.model;

import java.util.ArrayList;
import java.util.List;


public class Vacancy implements Comparable<Vacancy>{

    private String jobTitle;
    private int salary;
    private List<Requirement> requirements;
    private boolean active = true;

    public Vacancy(String jobTitle, int salary) {
        this.jobTitle = jobTitle;
        this.salary = salary;
        requirements = new ArrayList<>();
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public int getSalary() {
        return salary;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements.addAll(requirements);
    }

    public boolean isActive() {
        return active;
    }

    public void setRequirement(Requirement requirement) {
        requirements.add(requirement);
    }

    public void setActive(boolean activite) {
        this.active = activite;
    }

    public void deleteRequirements(List<Requirement> requirementList) {
        requirements.removeAll(requirementList);
    }

    @Override
    public int compareTo(Vacancy o) {
        return getJobTitle().compareTo(o.getJobTitle());

    }
    public void changeRequirement(Requirement requirement, Requirement updateRequirement) {
        for (Requirement r : requirements) {
            if (r.getName().equals(requirement.getName())) {
                r.setSkill(updateRequirement.getName());
                r.setRequired(updateRequirement.isRequired());
                r.setLevel(updateRequirement.getLevel());
            }
        }
    }

}
