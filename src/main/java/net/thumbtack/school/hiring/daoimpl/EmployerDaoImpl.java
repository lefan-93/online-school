package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.exception.EmployeeException;
import net.thumbtack.school.hiring.exception.EmployerException;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.response.EmployeeForVacancyDtoResponse;
import net.thumbtack.school.hiring.database.DataBase;

import java.util.*;

public class EmployerDaoImpl implements EmployerDao {

    DataBase dataBase = DataBase.getDataBase();

    @Override
    public void insert(Employer employer) throws EmployerException {
         dataBase.insert(employer);
    }

    @Override
    public Employer select(UUID token) {
        return dataBase.selectEmployer(token);
    }

    @Override
    public Employer select(String login, String password) throws EmployerException {
        return dataBase.selectEmployer(login, password);
    }

    @Override
    public void delete(UUID token) {
        dataBase.deleteEmployer(token);

    }

    @Override
    public Map<String, NavigableMap<Skill, Set<Employee>>> getSkillsEmployeesMap() {
        return dataBase.getSkillsEmployeesMap();
    }

    @Override
    public void deleteRequirement(Requirement requirement, Vacancy vacancy) {
        dataBase.deleteRequirement(requirement, vacancy);
    }

    @Override
    public UUID getTokenOfEmployer(Employer employer) throws EmployerException {
        return dataBase.getTokenOfEmployer(employer);
    }

    @Override
    public void addVacancy(Vacancy vacancy) {
        dataBase.addVacancy(vacancy);
    }

    @Override
    public void addRequirementVacancy(Requirement requirement, Vacancy vacancy) {
        dataBase.addRequirementVacancy(requirement, vacancy);
    }

    @Override
    public void changeRequirementVacancy(Requirement oldRequirement, Requirement newRequirement, Vacancy vacancy) {
        dataBase.changeRequirementVacancy(oldRequirement, newRequirement, vacancy);
    }
}
