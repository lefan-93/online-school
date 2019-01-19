package net.thumbtack.school.hiring.model;

import java.util.*;

public class Employee extends User {

    private boolean active;
    private Set<Skill> skills;

    public Employee(String firstName, String surName, String middleName, String login, String password, String email) {
        super(firstName, surName, middleName, login, password, email);
        skills = new HashSet<>();
        active = true;

    }

    public void addSkill(String skill, int level) {
        Skill skillEmployee = new Skill(skill, level);
        skills.add(skillEmployee);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void deleteSkill(Skill skill) {
        skills.remove(skill);
    }

    public void changeSkill(Skill oldSkill, Skill newSkill) {
        skills.remove(oldSkill);
        skills.add(newSkill);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;

        Employee employee = (Employee) o;

        return isActive() == employee.isActive() && (getSkills() != null ? getSkills().equals(employee.getSkills()) : employee.getSkills() == null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isActive() ? 1 : 0);
        result = 31 * result + (getSkills() != null ? getSkills().hashCode() : 0);
        return result;
    }

    public int getLavelForSkill(String skill) {
        for (Skill s : skills) {
            if (s.getName().equals(skill))
                return s.getLevel();
        }
        return 0;
    }
}
