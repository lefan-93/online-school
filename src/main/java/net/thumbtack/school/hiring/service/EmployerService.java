package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.exception.EmployerException;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.request.*;
import net.thumbtack.school.hiring.response.EmployeeForVacancyDtoResponse;
import net.thumbtack.school.hiring.response.EmptySuccessResponse;
import net.thumbtack.school.hiring.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.response.RegisterEmployerDtoResponse;

import java.util.*;

public class EmployerService {
    private Gson gson = new Gson();
    private EmployerDao dao = new EmployerDaoImpl();

    public String registerEmployer(String requestJsonString) {
        String response;
        RegisterEmployerDtoRequest registerEmployerDtoRequest = gson.fromJson(requestJsonString, RegisterEmployerDtoRequest.class);
        try {
            registerEmployerDtoRequest.validate();
            Employer employer = new Employer(registerEmployerDtoRequest.getCompanyName(),
                    registerEmployerDtoRequest.getAddress(),
                    registerEmployerDtoRequest.getFirstName(),
                    registerEmployerDtoRequest.getSurName(),
                    registerEmployerDtoRequest.getMiddleName(),
                    registerEmployerDtoRequest.getLogin(),
                    registerEmployerDtoRequest.getPassword(),
                    registerEmployerDtoRequest.getEmail());

            dao.insert(employer);
            response = gson.toJson(new RegisterEmployerDtoResponse(dao.getTokenOfEmployer(employer)));

        } catch (EmployerException e) {
            response = gson.toJson(new ErrorDtoResponse(e));
        }
        return response;
    }

    public String modificatioEmployer(String requestJsonString) {
        String response;
        ModificationEmployerDtoRequest modificationEmployerDtoRequest = gson.fromJson(requestJsonString, ModificationEmployerDtoRequest.class);
        try {
            modificationEmployerDtoRequest.validate();
            Employer employer = dao.select(modificationEmployerDtoRequest.getToken());
            if (!modificationEmployerDtoRequest.getCompanyName().equals(employer.getCompanyName())) {
                employer.setCompanyName(modificationEmployerDtoRequest.getCompanyName());
            }
            if (!modificationEmployerDtoRequest.getAddress().equals(employer.getAddress())) {
                employer.setAddress(modificationEmployerDtoRequest.getAddress());
            }
            if (!modificationEmployerDtoRequest.getFirstName().equals(employer.getFirstName())) {
                employer.setFirstName(modificationEmployerDtoRequest.getFirstName());
            }
            if (!modificationEmployerDtoRequest.getSurName().equals(employer.getSurName())) {
                employer.setSurName(modificationEmployerDtoRequest.getSurName());
            }
            if (!modificationEmployerDtoRequest.getMiddleName().equals(employer.getMiddleName())) {
                employer.setMiddleName(modificationEmployerDtoRequest.getMiddleName());
            }
            if (!modificationEmployerDtoRequest.getEmail().equals(employer.getEmail())) {
                employer.setEmail(modificationEmployerDtoRequest.getEmail());
            }
            if (!modificationEmployerDtoRequest.getPassword().equals(employer.getPassword())) {
                employer.setPassword(modificationEmployerDtoRequest.getPassword());
            }
            response = gson.toJson(new EmptySuccessResponse());


        } catch (EmployerException e) {
            response = gson.toJson(new ErrorDtoResponse(e));
        }
        return response;

    }

    public String loginEmployer(String requestJsonString) {
        LoginUserDtoRequest loginEmployerDtoRequest = gson.fromJson(requestJsonString, LoginUserDtoRequest.class);
        Employer e;
        String response;
        try {
            e = dao.select(loginEmployerDtoRequest.getLogin(), loginEmployerDtoRequest.getPassword());
            response = gson.toJson(new RegisterEmployerDtoResponse(dao.getTokenOfEmployer(e)));
        } catch (EmployerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        return response;
    }

    public String addVacancyEmployer(String requestJsonString) {
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = gson.fromJson(requestJsonString, AddVacancieEmployerDtoRequest.class);
        Employer employer = dao.select(addVacancieEmployerDtoRequest.getToken());
        Vacancy vacancy = new Vacancy(addVacancieEmployerDtoRequest.getJobTitle(), addVacancieEmployerDtoRequest.getSalary());
        for (Requirement requirement : addVacancieEmployerDtoRequest.getRequirements()) {
            dao.addRequirementVacancy(requirement, vacancy);
        }
        vacancy.setRequirements(addVacancieEmployerDtoRequest.getRequirements());
        employer.addVacancy(vacancy);
        dao.addVacancy(vacancy);
        return gson.toJson(new EmptySuccessResponse());


    }

    public String addRequirementVacancieEmployer(String requestJsonString) {
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = gson.fromJson(requestJsonString, AddVacancieEmployerDtoRequest.class);
        Employer employer = dao.select(addVacancieEmployerDtoRequest.getToken());
        Vacancy vacancy = new Vacancy(addVacancieEmployerDtoRequest.getJobTitle(), addVacancieEmployerDtoRequest.getSalary());
        for (Requirement requirement : addVacancieEmployerDtoRequest.getRequirements()) {
            dao.addRequirementVacancy(requirement, vacancy);
        }
        employer.getVacancie(vacancy.getJobTitle()).setRequirements(addVacancieEmployerDtoRequest.getRequirements());
        return gson.toJson(new EmptySuccessResponse());
    }

    public String deleteRequirementVacancieEmploer(String requestJsonString) {
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = gson.fromJson(requestJsonString, AddVacancieEmployerDtoRequest.class);
        Employer employer = dao.select(addVacancieEmployerDtoRequest.getToken());
        Vacancy vacancy = new Vacancy(addVacancieEmployerDtoRequest.getJobTitle(), addVacancieEmployerDtoRequest.getSalary());
        for (Requirement requirement : addVacancieEmployerDtoRequest.getRequirements()) {
            dao.addRequirementVacancy(requirement, vacancy);
        }
        employer.getVacancie(addVacancieEmployerDtoRequest.getJobTitle()).deleteRequirements(addVacancieEmployerDtoRequest.getRequirements());
        return gson.toJson(new EmptySuccessResponse());
    }

    public String updateRequirementVacancieEmploer(String requestJsonString) {
        JsonObject jsonObject = gson.fromJson(requestJsonString, JsonObject.class);
        JsonObject requirementJsonObject = jsonObject.getAsJsonObject("requirement");
        Requirement requirement = gson.fromJson(requirementJsonObject, Requirement.class);
        JsonObject updateRequirementJsonObject = jsonObject.getAsJsonObject("updateRequirement");
        Requirement updateRequirement = gson.fromJson(updateRequirementJsonObject, Requirement.class);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = gson.fromJson(requestJsonString, AddVacancieEmployerDtoRequest.class);
        Vacancy vacancy = new Vacancy(addVacancieEmployerDtoRequest.getJobTitle(), addVacancieEmployerDtoRequest.getSalary());
        dao.changeRequirementVacancy(requirement, updateRequirement, vacancy);
        Employer employer = dao.select(addVacancieEmployerDtoRequest.getToken());
        employer.getVacancie(vacancy.getJobTitle()).changeRequirement(requirement, updateRequirement);
        return gson.toJson(new EmptySuccessResponse());

    }

    public String getAllEmloyesForAllRequirementVacancie(String requestJsonString) {
        SelectVacancieDtoRequest selectVacancieDtoRequest = gson.fromJson(requestJsonString, SelectVacancieDtoRequest.class);
        List<Requirement> requirements = dao.select(selectVacancieDtoRequest.getToken()).getVacancie(selectVacancieDtoRequest.getJobTitle()).getRequirements();
        Set<EmployeeForVacancyDtoResponse> employees = new HashSet<>();
        Set<Employee> employeesForSkill = new HashSet<>();
        for (Requirement r : requirements) {
            Set<Employee> employeesForSkillFromMap = new HashSet<>();
            for (Skill skill : dao.getSkillsEmployeesMap().get(r.getName()).tailMap(r).keySet()) {
                employeesForSkillFromMap.addAll(dao.getSkillsEmployeesMap().get(r.getName()).tailMap(r).get(skill));
            }
            if (employeesForSkill.size() == 0) {
                employeesForSkill.addAll(employeesForSkillFromMap);
            }
            employeesForSkill.retainAll(employeesForSkillFromMap);
        }
        for (Employee e : employeesForSkill) {
            EmployeeForVacancyDtoResponse employeeForVacancyDtoResponse = new EmployeeForVacancyDtoResponse(e.getFirstName(), e.getSurName(), e.getEmail(), e.getSkills());
            employees.add(employeeForVacancyDtoResponse);
        }

        return gson.toJson(employees);

    }

    public String getAllEmloyesForRequiredRequirementVacancie(String requestJsonString) {
        SelectVacancieDtoRequest selectVacancieDtoRequest = gson.fromJson(requestJsonString, SelectVacancieDtoRequest.class);
        List<Requirement> requirements = dao.select(selectVacancieDtoRequest.getToken()).getVacancie(selectVacancieDtoRequest.getJobTitle()).getRequirements();
        Set<EmployeeForVacancyDtoResponse> employees = new HashSet<>();
        Set<Employee> employeesForSkill = new HashSet<>();
        for (Requirement r : requirements) {
            if (r.isRequired()) {
                Set<Employee> employeesForSkillFromMap = new HashSet<>();
                for (Skill skill : dao.getSkillsEmployeesMap().get(r.getName()).tailMap(r).keySet()) {
                    employeesForSkillFromMap.addAll(dao.getSkillsEmployeesMap().get(skill.getName()).tailMap(r).get(skill));
                }
                if (employeesForSkill.size() == 0) {
                    employeesForSkill.addAll(employeesForSkillFromMap);
                }
                employeesForSkill.retainAll(employeesForSkillFromMap);
            }
        }
        for (Employee e : employeesForSkill) {
            EmployeeForVacancyDtoResponse employeeForVacancyDtoResponse = new EmployeeForVacancyDtoResponse(e.getFirstName(), e.getSurName(), e.getEmail(), e.getSkills());
            employees.add(employeeForVacancyDtoResponse);
        }

        return gson.toJson(employees);
    }

    public String getAllEmloyesForAllRequirementNotLevelVacancie(String requestJsonString) {
        SelectVacancieDtoRequest selectVacancieDtoRequest = gson.fromJson(requestJsonString, SelectVacancieDtoRequest.class);
        List<Requirement> requirements = dao.select(selectVacancieDtoRequest.getToken()).getVacancie(selectVacancieDtoRequest.getJobTitle()).getRequirements();
        Set<EmployeeForVacancyDtoResponse> employees = new HashSet<>();
        Set<Employee> employeesForSkill = new HashSet<>();
        for (Requirement r : requirements) {
            Set<Employee> employeesForSkillFromMap = new HashSet<>();
            for (Skill skill : dao.getSkillsEmployeesMap().get(r.getName()).keySet()) {
                employeesForSkillFromMap.addAll(dao.getSkillsEmployeesMap().get(r.getName()).get(skill));
            }
            if (employeesForSkill.size() == 0) {
                employeesForSkill.addAll(employeesForSkillFromMap);
            }
            employeesForSkill.retainAll(employeesForSkillFromMap);
        }
        for (Employee e : employeesForSkill) {
            EmployeeForVacancyDtoResponse employeeForVacancyDtoResponse = new EmployeeForVacancyDtoResponse(e.getFirstName(), e.getSurName(), e.getEmail(), e.getSkills());
            employees.add(employeeForVacancyDtoResponse);
        }

        return gson.toJson(employees);

    }

    public String getAllEmloyesForOneRequirementVacancie(String requestJsonString) {
        SelectVacancieDtoRequest selectVacancieDtoRequest = gson.fromJson(requestJsonString, SelectVacancieDtoRequest.class);
        List<Requirement> requirements = dao.select(selectVacancieDtoRequest.getToken()).getVacancie(selectVacancieDtoRequest.getJobTitle()).getRequirements();
        Set<EmployeeForVacancyDtoResponse> employees = new HashSet<>();
        Set<Employee> employeesForSkill = new HashSet<>();
        for (Requirement r : requirements) {
            for (Skill skill : dao.getSkillsEmployeesMap().get(r.getName()).tailMap(r).keySet()) {
                employeesForSkill.addAll(dao.getSkillsEmployeesMap().get(r.getName()).tailMap(r).get(skill));
            }
        }
        for (Employee e : employeesForSkill) {
            EmployeeForVacancyDtoResponse employeeForVacancyDtoResponse = new EmployeeForVacancyDtoResponse(e.getFirstName(), e.getSurName(), e.getEmail(), e.getSkills());
            employees.add(employeeForVacancyDtoResponse);
        }


        return gson.toJson(employees);
    }


    public String deleteEmployer(String requestJsonString) {
        DeleteEmployerDtoRequest deleteEmployerDtoRequest = gson.fromJson(requestJsonString, DeleteEmployerDtoRequest.class);
        dao.delete(deleteEmployerDtoRequest.getToken());
        return gson.toJson(new EmptySuccessResponse());
    }

    public String deleteVacancieEmployer(String requestJsonString) {
        SelectEmployerDtoRequest selectEmployerDtoRequest = gson.fromJson(requestJsonString, SelectEmployerDtoRequest.class);
        JsonObject jsonObject = gson.fromJson(requestJsonString, JsonObject.class);
        JsonPrimitive activiteJsonElement = jsonObject.getAsJsonPrimitive("jobTitle");
        String vacancie = activiteJsonElement.getAsString();
        Vacancy vacancy = dao.select(selectEmployerDtoRequest.getToken()).getVacancie(vacancie);
        List<Requirement> requirements = vacancy.getRequirements();
        for (Requirement r : requirements) {
            dao.deleteRequirement(r, vacancy);
        }
        dao.select(selectEmployerDtoRequest.getToken()).deleteVacancie(vacancie);
        return gson.toJson(new EmptySuccessResponse());
    }


    public String isActiviteVacancieEmployer(String requestJsonString) {

        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = gson.fromJson(requestJsonString, AddVacancieEmployerDtoRequest.class);
        Employer employer = dao.select(addVacancieEmployerDtoRequest.getToken());
        employer.getVacancie(addVacancieEmployerDtoRequest.getJobTitle()).setActive(addVacancieEmployerDtoRequest.isActive());

        return gson.toJson(new EmptySuccessResponse());

    }

    public String getAllVacancieEmployer(String requestJsonString) {
        SelectEmployerDtoRequest selectEmployerDtoRequest = gson.fromJson(requestJsonString, SelectEmployerDtoRequest.class);
        List<Vacancy> vacancies = dao.select(selectEmployerDtoRequest.getToken()).getVacancis();
        return gson.toJson(vacancies);
    }

    public String getActiviteVacancieEmployer(String requestJsonString) {
        SelectEmployerDtoRequest selectEmployerDtoRequest = gson.fromJson(requestJsonString, SelectEmployerDtoRequest.class);
        List<Vacancy> vacancies = new ArrayList<>();

        for (Vacancy vacancy : dao.select(selectEmployerDtoRequest.getToken()).getVacancis()) {
            if (vacancy.isActive()) {
                vacancies.add(vacancy);
            }
        }
        return gson.toJson(vacancies);
    }

    public String getNotActiviteVacancieEmployer(String requestJsonString) {
        SelectEmployerDtoRequest selectEmployerDtoRequest = gson.fromJson(requestJsonString, SelectEmployerDtoRequest.class);
        List<Vacancy> vacancies = new ArrayList<>();

        for (Vacancy vacancy : dao.select(selectEmployerDtoRequest.getToken()).getVacancis()) {
            if (!vacancy.isActive()) {
                vacancies.add(vacancy);
            }
        }
        return gson.toJson(vacancies);
    }
}
