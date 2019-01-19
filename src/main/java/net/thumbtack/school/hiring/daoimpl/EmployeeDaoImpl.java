package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.exception.EmployeeException;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.UUID;

public class EmployeeDaoImpl implements EmployeeDao {
    DataBase dataBase = DataBase.getDataBase();

    @Override
    public void insert(Employee employee) throws EmployeeException {
         dataBase.insert(employee);
    }

    @Override
    public Employee select(UUID index) {
        return dataBase.selectEmployee(index);
    }

    @Override
    public Employee select(String login, String password) throws EmployeeException {
        return dataBase.selectEmployee(login, password);
    }

    @Override
    public void delete(UUID token) {
        dataBase.deleteEmployee(token);
    }

    @Override
    public void addSkillEmployee(Skill skill, Employee employee) {
        dataBase.addSkillEmployee(skill, employee);
    }

    @Override
    public void deleteSkillEmployee(Skill skill, Employee employee) {
        dataBase.deleteSkillEmployee(skill, employee);
    }

    @Override
    public void changeSkillEmployee(Skill oldSkill, Skill newSkill, Employee employee) {
        dataBase.changeSkillEmployee(oldSkill, newSkill, employee);
    }

    @Override
    public Map<String, NavigableMap<Skill, Set<Vacancy>>> getRequirementVacanciesMap() {
        return dataBase.getRequirementVacanciesMap();
    }

    @Override
    public Set<Vacancy> getVacancies() {
        return dataBase.getVacancies();
    }

    @Override
    public UUID getTokenOfEmployee(Employee employee) throws EmployeeException {
        return dataBase.getTokenOfEmployee(employee);
    }

}

