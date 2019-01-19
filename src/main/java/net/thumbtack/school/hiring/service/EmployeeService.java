package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.exception.EmployeeException;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.request.*;
import net.thumbtack.school.hiring.response.EmptySuccessResponse;
import net.thumbtack.school.hiring.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.response.RegisterEmployeeDtoResponse;

import java.util.*;

public class EmployeeService {

    private Gson gson = new Gson();
    private EmployeeDao dao = new EmployeeDaoImpl();

    public String registerEmployee(String requestJsonString) {
        String response;
        RegisterEmployeeDtoRequest registerEmployeeDtoReques = gson.fromJson(requestJsonString, RegisterEmployeeDtoRequest.class);
        try {
            registerEmployeeDtoReques.validate();
            Employee employee = new Employee(registerEmployeeDtoReques.getFirstName(),
                    registerEmployeeDtoReques.getSurName(),
                    registerEmployeeDtoReques.getMiddleName(),
                    registerEmployeeDtoReques.getLogin(),
                    registerEmployeeDtoReques.getPassword(),
                    registerEmployeeDtoReques.getEmail());
            dao.insert(employee);
            response = gson.toJson(new RegisterEmployeeDtoResponse(dao.getTokenOfEmployee(employee)));
        } catch (EmployeeException e) {
            response = gson.toJson(new ErrorDtoResponse(e));
        }
        return response;
    }


    public String modifyEmployee(String requestJsonString) {
        String response;
        ModificationEmployeeDtoRequest modificationEmployeeDtoRequest = gson.fromJson(requestJsonString, ModificationEmployeeDtoRequest.class);
        try {
            modificationEmployeeDtoRequest.validate();
            Employee employee = dao.select(modificationEmployeeDtoRequest.getToken());
            if (!modificationEmployeeDtoRequest.getFirstName().equals(employee.getFirstName())) {
                employee.setFirstName(modificationEmployeeDtoRequest.getFirstName());
            }
            if (!modificationEmployeeDtoRequest.getSurName().equals(employee.getSurName())) {
                employee.setSurName(modificationEmployeeDtoRequest.getSurName());
            }
            if (!modificationEmployeeDtoRequest.getMiddleName().equals(employee.getMiddleName())) {
                employee.setMiddleName(modificationEmployeeDtoRequest.getMiddleName());
            }
            if (!modificationEmployeeDtoRequest.getEmail().equals(employee.getEmail())) {
                employee.setEmail(modificationEmployeeDtoRequest.getEmail());
            }
            if (!modificationEmployeeDtoRequest.getPassword().equals(employee.getPassword())) {
                employee.setPassword(modificationEmployeeDtoRequest.getPassword());
            }
            response = gson.toJson(new EmptySuccessResponse());


        } catch (EmployeeException e) {
            response = gson.toJson(new ErrorDtoResponse(e));
        }
        return response;
    }

    public String loginEmployee(String requestJsonString) {
        LoginUserDtoRequest loginEmployeeDtoRequest = gson.fromJson(requestJsonString, LoginUserDtoRequest.class);
        Employee e;
        String response;
        try {
            e = dao.select(loginEmployeeDtoRequest.getLogin(), loginEmployeeDtoRequest.getPassword());
            response = gson.toJson(new RegisterEmployeeDtoResponse(dao.getTokenOfEmployee(e)));
        } catch (EmployeeException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        return response;
    }

    public String deleteEmployee(String requestJsonString) {
        SelectEmployeeDtoRequest selectEmployeeDtoRequest = gson.fromJson(requestJsonString, SelectEmployeeDtoRequest.class);
        dao.delete(selectEmployeeDtoRequest.getToken());
        return gson.toJson(new EmptySuccessResponse());
    }

    public String addSkillEmployee(String requestJsonString) {
        SkillEmployeeDtoRequest addSkillEmployeeDtoRequest = gson.fromJson(requestJsonString, SkillEmployeeDtoRequest.class);
        dao.select(addSkillEmployeeDtoRequest.getToken()).addSkill(addSkillEmployeeDtoRequest.getSkill(), addSkillEmployeeDtoRequest.getLevel());
        Skill skill = new Skill(addSkillEmployeeDtoRequest.getSkill(), addSkillEmployeeDtoRequest.getLevel());
        dao.addSkillEmployee(skill, dao.select(addSkillEmployeeDtoRequest.getToken()));
        return gson.toJson(new EmptySuccessResponse());
    }

    public String deleteSkillEmployee(String requestJsonString) {
        SkillEmployeeDtoRequest deleteSkillEmployeeDtoRequest = gson.fromJson(requestJsonString, SkillEmployeeDtoRequest.class);
        Skill skill = new Skill(deleteSkillEmployeeDtoRequest.getSkill(), deleteSkillEmployeeDtoRequest.getLevel());
        dao.select(deleteSkillEmployeeDtoRequest.getToken()).deleteSkill(skill);
        dao.deleteSkillEmployee(skill, dao.select(deleteSkillEmployeeDtoRequest.getToken()));
        return gson.toJson(new EmptySuccessResponse());
    }

    public String changeSkillEmployee(String requestJsonString) {
        SkillEmployeeDtoRequest changeSkillEmployee = gson.fromJson(requestJsonString, SkillEmployeeDtoRequest.class);
        Skill oldSkill = null;
        Set<Skill> skills = new HashSet<>(dao.select(changeSkillEmployee.getToken()).getSkills());
        for (Skill s : skills) {
            if (s.getName().equals(changeSkillEmployee.getSkill()))
                oldSkill = s;
        }
        Skill newSkill = new Skill(changeSkillEmployee.getSkill(), changeSkillEmployee.getLevel());
        dao.select(changeSkillEmployee.getToken()).changeSkill(oldSkill, newSkill);
        dao.changeSkillEmployee(oldSkill, newSkill, dao.select(changeSkillEmployee.getToken()));
        return gson.toJson(new EmptySuccessResponse());
    }

    public String isActiveEmployee(String requestJsonString) {
        ActiveEmployeeDtoRequest activeEmployeeDtoRequest = gson.fromJson(requestJsonString, ActiveEmployeeDtoRequest.class);
        dao.select(activeEmployeeDtoRequest.getToken()).setActive(activeEmployeeDtoRequest.isActive());
        return gson.toJson(new EmptySuccessResponse());
    }


    public String getAllVacanciesForSkillsEmployeeAllRequirement(String requestJsonString) {
        SelectEmployeeDtoRequest selectEmployee = gson.fromJson(requestJsonString, SelectEmployeeDtoRequest.class);
        Employee e = dao.select(selectEmployee.getToken());
        Set<Vacancy> vacancies = new TreeSet<>(dao.getVacancies());
        Set<Skill> skills = e.getSkills();
        for (Skill s : skills) {
            Set<Vacancy> noncomplianceWithRequirements = new TreeSet<>();
            Set<Vacancy> requiredLevel = new TreeSet<>();
            Set<Skill> skillsOfVacancies = new TreeSet<>(dao.getRequirementVacanciesMap().get(s.getName()).headMap(s, true).keySet());
            for (Skill s1 : skillsOfVacancies) {
                requiredLevel.addAll(dao.getRequirementVacanciesMap().get(s.getName()).headMap(s, true).get(s1));
            }
            for (Vacancy v : requiredLevel) {
                for (Requirement r : v.getRequirements()) {
                    if (!r.getName().equals(s.getName()))
                        noncomplianceWithRequirements.add(v);
                }
            }
            requiredLevel.removeAll(noncomplianceWithRequirements);
            vacancies.retainAll(requiredLevel);
        }
        return gson.toJson(vacancies);
    }


    public String getAllVacanciesForAllSkillsEmployeeRequredRequirement(String requestJsonString) {
        SelectEmployeeDtoRequest selectEmployee = gson.fromJson(requestJsonString, SelectEmployeeDtoRequest.class);
        Employee e = dao.select(selectEmployee.getToken());
        Set<Vacancy> vacancies = new TreeSet<>(dao.getVacancies());
        Set<Skill> skills = e.getSkills();
        for (Skill s : skills) {
            Set<Vacancy> noncomplianceWithRequirements = new TreeSet<>();
            Set<Vacancy> requiredLevel = new TreeSet<>();
            Set<Skill> skillsOfVacancies = new TreeSet<>(dao.getRequirementVacanciesMap().get(s.getName()).headMap(s, true).keySet());
            for (Skill s1 : skillsOfVacancies) {
                requiredLevel.addAll(dao.getRequirementVacanciesMap().get(s.getName()).headMap(s, true).get(s1));
            }
            for (Vacancy v : requiredLevel) {
                for (Requirement r : v.getRequirements()) {
                    if (!r.getName().equals(s.getName())) {
                        if (r.isRequired())
                            noncomplianceWithRequirements.add(v);
                    }
                }
            }
            requiredLevel.removeAll(noncomplianceWithRequirements);
            vacancies.retainAll(requiredLevel);
        }
        return gson.toJson(vacancies);
    }

    public String getAllVacanciesForAllSkillsEmployeeAllRequirementNotLevel(String requestJsonString) {
        SelectEmployeeDtoRequest selectEmployee = gson.fromJson(requestJsonString, SelectEmployeeDtoRequest.class);
        Employee e = dao.select(selectEmployee.getToken());
        Set<Vacancy> vacancies = new TreeSet<>(dao.getVacancies());
        Set<Skill> skills = e.getSkills();
        for (Skill s : skills) {
            Set<Vacancy> requiredLevel = new TreeSet<>();
            Set<Skill> skillsOfVacancies = new TreeSet<>(dao.getRequirementVacanciesMap().get(s.getName()).keySet());
            for (Skill s1 : skillsOfVacancies) {
                requiredLevel.addAll(dao.getRequirementVacanciesMap().get(s.getName()).get(s1));
            }
            vacancies.retainAll(requiredLevel);
        }
        return gson.toJson(vacancies);
    }


    public String getAllVacanciesForAllSkillsEmployeeOneRequirement(String requestJsonString) {
        SelectEmployeeDtoRequest selectEmployee = gson.fromJson(requestJsonString, SelectEmployeeDtoRequest.class);
        Employee e = dao.select(selectEmployee.getToken());
        Set<Vacancy> vacanciesForOneRequirement = new TreeSet<>();
        Map<Integer, Set<Vacancy>> vacancies = new TreeMap<>((s1, s2) -> s2 - s1);
        Set<Skill> skills = e.getSkills();
        for (Skill s : skills) {
            Set<Vacancy> requiredLevel = new TreeSet<>();
            Set<Skill> skillsOfVacancies = new TreeSet<>(dao.getRequirementVacanciesMap().get(s.getName()).headMap(s, true).keySet());
            for (Skill s1 : skillsOfVacancies) {
                vacanciesForOneRequirement.addAll(dao.getRequirementVacanciesMap().get(s.getName()).headMap(s, true).get(s1));
            }
        }
        for (Vacancy v : vacanciesForOneRequirement) {
            List<Requirement> requirements = v.getRequirements();
            Integer count = 0;
            for (Requirement r : requirements) {
                for (Skill s : skills) {
                    if (r.getName().equals(s.getName()) && r.getLevel() == s.getLevel())
                        count++;
                }

            }
            if (vacancies.containsKey(count)) {
                vacancies.get(count).add(v);
            } else {
                vacancies.put(count, new HashSet<>());
                vacancies.get(count).add(v);
            }
        }
        return gson.toJson(vacancies);

    }

}
