package net.thumbtack.school.hiring.server;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;

import java.io.*;

public class Server {
    private EmployeeService employeeService;
    private EmployerService employerService;

    public Server() {
        employeeService = new EmployeeService();
        employerService = new EmployerService();
    }

    public String registerEmployer(String requestJsonString) {
        return employerService.registerEmployer(requestJsonString);
    }

    public String modificationEmployer(String requestJsonString) {
        return employerService.modificatioEmployer(requestJsonString);
    }

    public String loginEmployer(String requestJsonString) {
        return employerService.loginEmployer(requestJsonString);
    }

    public String deleteEmployer(String requestJsonString) {
        return employerService.deleteEmployer(requestJsonString);
    }

    public String addVacancieEmployer(String requestJsonString) {
        return employerService.addVacancyEmployer(requestJsonString);
    }

    public String addRequirementVacancieEmployer(String requestJsonString) {
        return employerService.addRequirementVacancieEmployer(requestJsonString);
    }

    public String deleteRequirementVacancieEmploer(String requestJsonString) {
        return employerService.deleteRequirementVacancieEmploer(requestJsonString);
    }

    public String updateRequirementVacancieEmploer(String requestJsonString) {
        return employerService.updateRequirementVacancieEmploer(requestJsonString);
    }

    public String isActiviteVacancieEmployer(String requestJsonString) {
        return employerService.isActiviteVacancieEmployer(requestJsonString);
    }

    public String getAllVacancieEmployer(String requestJsonString) {
        return employerService.getAllVacancieEmployer(requestJsonString);
    }

    public String getActiviteVacancieEmployer(String requestJsonString) {
        return employerService.getActiviteVacancieEmployer(requestJsonString);
    }

    public String getNotActiviteVacancieEmployer(String requestJsonString) {
        return employerService.getNotActiviteVacancieEmployer(requestJsonString);
    }

    public String deleteVacancieEmployer(String requestJsonString) {
        return employerService.deleteVacancieEmployer(requestJsonString);
    }

    public String getAllEmloyesForAllRequirementVacancie(String requestJsonString) {
        return employerService.getAllEmloyesForAllRequirementVacancie(requestJsonString);
    }

    public String getAllEmloyesForRequiredRequirementVacancie(String requestJsonString) {
        return employerService.getAllEmloyesForRequiredRequirementVacancie(requestJsonString);
    }

    public String getAllEmloyesForAllRequirementNotLevelVacancie(String requestJsonString) {
        return employerService.getAllEmloyesForAllRequirementNotLevelVacancie(requestJsonString);
    }

    public String getAllEmloyesForOneRequirementVacancie(String requestJsonString) {
        return employerService.getAllEmloyesForOneRequirementVacancie(requestJsonString);
    }

    public String registerEmployee(String requestJsonString) {
        return employeeService.registerEmployee(requestJsonString);
    }

    public String modificationEmployee(String requestJsonString) {
        return employeeService.modifyEmployee(requestJsonString);
    }

    public String loginEmployee(String requestJsonString) {
        return employeeService.loginEmployee(requestJsonString);
    }

    public String deleteEmployee(String requestJsonString) {
        return employeeService.deleteEmployee(requestJsonString);
    }

    public String addSkillEmployee(String requestJsonString) {
        return employeeService.addSkillEmployee(requestJsonString);
    }

    public String deleteSkillEmployee(String requestJsonString) {
        return employeeService.deleteSkillEmployee(requestJsonString);
    }

    public String changeSkillEmployee(String requestJsonString) {
        return employeeService.changeSkillEmployee(requestJsonString);
    }

    public String setActiveEmployee(String requestJsonString) {
        return employeeService.isActiveEmployee(requestJsonString);
    }

    public String getAllVacanciesForSkillsEmployeeAllRequirement(String requestJsonString) {
        return employeeService.getAllVacanciesForSkillsEmployeeAllRequirement(requestJsonString);
    }

    public String getAllVacanciesForAllSkillsEmployeeRequredRequirement(String requestJsonString) {
        return employeeService.getAllVacanciesForAllSkillsEmployeeRequredRequirement(requestJsonString);
    }

    public String getAllVacancieForAllSkillsEmployeeAllRequirementNotLevel(String requestJsonString) {
        return employeeService.getAllVacanciesForAllSkillsEmployeeAllRequirementNotLevel(requestJsonString);
    }

    public String getAllVacanciesForAllSkillsEmployeeOneRequirement(String requestJsonString) {
        return employeeService.getAllVacanciesForAllSkillsEmployeeOneRequirement(requestJsonString);
    }

    public void startServer(String savedDataFileName) throws IOException {
        if (!(savedDataFileName == null)) {
            Gson gson = new Gson();
            try (BufferedReader br = new BufferedReader(new FileReader(new File(savedDataFileName)))) {
                DataBase dataBase = gson.fromJson(br, DataBase.class);
                DataBase.setDataBase(dataBase);
            }

        }
        DataBase.getDataBase();
    }

    public void stopServer(String saveDataFileName) throws IOException {
        if (!(saveDataFileName == null)) {
            Gson gson = new Gson();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(saveDataFileName)))) {
                gson.toJson(DataBase.getDataBase(), bw);

            }

        } else {
            DataBase.setDataBase(null);
        }
    }
}
