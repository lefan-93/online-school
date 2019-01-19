package net.thumbtack.school.hiring.database;

import net.thumbtack.school.hiring.errorscode.EmployeeErrorCode;
import net.thumbtack.school.hiring.errorscode.EmployerErrorCode;
import net.thumbtack.school.hiring.exception.EmployeeException;
import net.thumbtack.school.hiring.exception.EmployerException;
import net.thumbtack.school.hiring.model.*;

import java.util.*;

public class DataBase {
    private static DataBase dataBase;
    private Map<UUID, User> usersIndexMap;
    private Map<String, User> usersLoginMap;
    private Map<String, NavigableMap<Skill, Set<Employee>>> skillsEmployeesMap;
    private Map<String, NavigableMap<Skill, Set<Vacancy>>> requirementVacanciesMap;
    private Set<Vacancy> vacancies;

    private DataBase() {
        usersIndexMap = new HashMap<>();
        usersLoginMap = new HashMap<>();
        skillsEmployeesMap = new TreeMap<>();
        requirementVacanciesMap = new TreeMap<>();
        vacancies = new HashSet<>();
    }

    public static DataBase getDataBase() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public static void setDataBase(DataBase dataBase) {
        DataBase.dataBase = dataBase;
    }

    public void insert(Employee employee) throws EmployeeException {
        if (usersLoginMap.containsKey(employee.getLogin())) {
            throw new EmployeeException(EmployeeErrorCode.EMPLOYEE_ALREADY_EXIST);
        }
        usersIndexMap.put(UUID.randomUUID(), employee);
        usersLoginMap.put(employee.getLogin(), employee);
    }

    public Employee selectEmployee(UUID token) {
        return (Employee) usersIndexMap.get(token);
    }

    public Employee selectEmployee(String login, String password) throws EmployeeException {
        Employee e = (Employee) usersLoginMap.get(login);
        if (e == null) {
            throw new EmployeeException(EmployeeErrorCode.EMPLOYEE_WRONG_LOGIN);
        } else if (!e.getPassword().equals(password)) {
            throw new EmployeeException(EmployeeErrorCode.EMPLOYEE_WRONG_PASSWORD);
        }
        return e;
    }

    public void deleteEmployee(UUID token) {
        Employee employee = (Employee) usersIndexMap.get(token);
        Set<Skill> skills = employee.getSkills();
        for (Skill skill : skills) {
            skillsEmployeesMap.get(skill.getName()).get(skill).remove((Employee) usersIndexMap.get(token));
        }
        usersLoginMap.remove(usersIndexMap.get(token).getLogin());
        usersIndexMap.remove(token);

    }

    public void addSkillEmployee(Skill skill, Employee employee) {
        try {
            skillsEmployeesMap.get(skill.getName()).get(skill).add(employee);
        } catch (NullPointerException e) {
            try {
                Set<Employee> employees = new HashSet<>();
                employees.add(employee);
                skillsEmployeesMap.get(skill.getName()).put(skill, employees);
            } catch (NullPointerException ex) {
                Set<Employee> employees = new HashSet<>();
                employees.add(employee);
                NavigableMap<Skill, Set<Employee>> employeeMap = new TreeMap<>();
                employeeMap.put(skill, employees);
                skillsEmployeesMap.put(skill.getName(), employeeMap);
            }
        }
    }

    public void deleteSkillEmployee(Skill skill, Employee employee) {
        skillsEmployeesMap.get(skill.getName()).get(skill).remove(employee);
    }

    public void changeSkillEmployee(Skill oldSkill, Skill newSkill, Employee employee) {
        deleteSkillEmployee(oldSkill, employee);
        addSkillEmployee(newSkill, employee);
    }

    public int getUsersCount() {
        return usersLoginMap.values().size();
    }

    public void insert(Employer employer) throws EmployerException {
        if (usersLoginMap.containsValue(employer)) {
            throw new EmployerException(EmployerErrorCode.EMPLOYER_ALREADY_EXIST);
        }
        usersIndexMap.put(UUID.randomUUID(), employer);
        usersLoginMap.put(employer.getLogin(), employer);

    }

    public Employer selectEmployer(UUID token) {
        if (usersIndexMap.containsKey(token)) {
            return (Employer) usersIndexMap.get(token);
        }
        return null;
    }

    public Employer selectEmployer(String login, String password) throws EmployerException {
        Employer e = (Employer) usersLoginMap.get(login);
        if (e == null) {
            throw new EmployerException(EmployerErrorCode.EMPLOYER_WRONG_LOGIN);
        } else if (!e.getPassword().equals(password)) {
            throw new EmployerException(EmployerErrorCode.EMPLOYER_WRONG_PASSWORD);
        }
        return e;
    }

    public void addRequirementVacancy(Requirement requirement, Vacancy vacancy) {
        try {
            requirementVacanciesMap.get(requirement.getName()).get(requirement).add(vacancy);
        } catch (NullPointerException e) {
            try {
                Set<Vacancy> vacancies = new HashSet<>();
                vacancies.add(vacancy);
                requirementVacanciesMap.get(requirement.getName()).put(requirement, vacancies);
            } catch (NullPointerException ex) {
                Set<Vacancy> vacancies = new HashSet<>();
                vacancies.add(vacancy);
                NavigableMap<Skill, Set<Vacancy>> vacanciesMap = new TreeMap<>();
                vacanciesMap.put(requirement, vacancies);
                requirementVacanciesMap.put(requirement.getName(), vacanciesMap);
            }
        }
    }

    public void deleteRequirement(Requirement requirement, Vacancy vacancy) {
        Skill skill = new Skill(requirement.getName(), requirement.getLevel());
        requirementVacanciesMap.get(skill.getName()).get(skill).remove(vacancy);
    }

    public void changeRequirementVacancy(Requirement oldRequirement, Requirement newRequirement, Vacancy vacancy) {
        deleteRequirement(oldRequirement, vacancy);
        addRequirementVacancy(newRequirement, vacancy);
    }

    public void deleteEmployer(UUID token) {
        usersLoginMap.remove(usersIndexMap.get(token).getLogin());
        usersIndexMap.remove(token);

    }

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    public Map<String, NavigableMap<Skill, Set<Employee>>> getSkillsEmployeesMap() {
        return skillsEmployeesMap;
    }

    public Map<String, NavigableMap<Skill, Set<Vacancy>>> getRequirementVacanciesMap() {
        return requirementVacanciesMap;
    }

    public UUID getTokenOfEmployee(Employee employee) throws EmployeeException {

        return usersIndexMap.entrySet()
                .stream()
                .filter(e -> e.getValue().equals(employee))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new EmployeeException(EmployeeErrorCode.EMPLOYEE_NOT_EXIST));
    }

    public UUID getTokenOfEmployer(Employer employer) throws EmployerException {

        return usersIndexMap.entrySet()
                .stream()
                .filter(e -> e.getValue().equals(employer))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new EmployerException(EmployerErrorCode.EMPLOYER_NOT_EXIST));
    }
}

