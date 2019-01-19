package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.exception.EmployeeException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.UUID;

public interface EmployeeDao {

    void insert(Employee employee) throws EmployeeException;

    Employee select(UUID index);

    Employee select(String login, String password) throws EmployeeException;

    void delete(UUID token);

    void addSkillEmployee(Skill skill, Employee employee);

    void deleteSkillEmployee(Skill skill, Employee employee);

    Map<String, NavigableMap<Skill, Set<Vacancy>>> getRequirementVacanciesMap();

    Set<Vacancy> getVacancies();

    UUID getTokenOfEmployee(Employee employee) throws EmployeeException;

    void changeSkillEmployee(Skill oldSkill, Skill newSkill, Employee employee);

}

