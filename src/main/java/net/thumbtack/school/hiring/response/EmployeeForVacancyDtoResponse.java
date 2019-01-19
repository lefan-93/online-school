package net.thumbtack.school.hiring.response;

import net.thumbtack.school.hiring.model.Skill;

import java.util.Map;
import java.util.Set;

public class EmployeeForVacancyDtoResponse {


    private String firstName;
    private String surName;
    private String email;
    Set<Skill> skills;

    public EmployeeForVacancyDtoResponse(String firstName, String lastName, String email, Set<Skill> skills) {
        this.firstName = firstName;
        this.surName = lastName;
        this.email = email;
        this.skills = skills;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
