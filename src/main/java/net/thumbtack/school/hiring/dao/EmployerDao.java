package net.thumbtack.school.hiring.dao;


import net.thumbtack.school.hiring.exception.EmployeeException;
import net.thumbtack.school.hiring.exception.EmployerException;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.response.EmployeeForVacancyDtoResponse;

import java.util.*;

public interface EmployerDao {

    void insert(Employer employer) throws EmployerException;

    Employer select(UUID token);

    Employer select(String login, String password) throws EmployerException;

    void delete(UUID token);

    Map<String, NavigableMap<Skill, Set<Employee>>> getSkillsEmployeesMap();

    void deleteRequirement(Requirement requirement, Vacancy vacancy);

    UUID getTokenOfEmployer(Employer employer) throws EmployerException;

    void addVacancy(Vacancy vacancy);

    void addRequirementVacancy(Requirement requirement, Vacancy vacancy);

    void changeRequirementVacancy(Requirement oldRequirement, Requirement newRequirement, Vacancy vacancy);
}

