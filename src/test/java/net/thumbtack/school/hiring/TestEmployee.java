package net.thumbtack.school.hiring;


import com.google.gson.Gson;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.errorscode.EmployeeErrorCode;
import net.thumbtack.school.hiring.exception.EmployeeException;
import net.thumbtack.school.hiring.model.Requirement;
import net.thumbtack.school.hiring.model.Vacancy;
import net.thumbtack.school.hiring.request.*;
import net.thumbtack.school.hiring.response.*;
import net.thumbtack.school.hiring.server.Server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestEmployee {

    private Server server;
    private Gson gson;


    @Before
    public void startServer() throws IOException {
        gson = new Gson();
        server = new Server();
        server.startServer(null);
    }

    @After
    public void stopServer() throws IOException {
        server.stopServer(null);
    }

    @Test
    public void testRegisterEmployee() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Иван", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String s = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        LoginUserDtoRequest loginUserDtoRequest = new LoginUserDtoRequest("vania", "Iv123vgf");
        assertEquals(server.loginEmployee(gson.toJson(loginUserDtoRequest)), s);
        registerEmployeeDtoRequest.setFirstName("");
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployeeErrorCode.EMPLOYEE_WRONG_FIRSTNAME.getErrorString())), server.registerEmployee(gson.toJson(registerEmployeeDtoRequest)));
        registerEmployeeDtoRequest.setFirstName("Иван");
        registerEmployeeDtoRequest.setSurName("");
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployeeErrorCode.EMPLOYEE_WRONG_SURNAME.getErrorString())), server.registerEmployee(gson.toJson(registerEmployeeDtoRequest)));
        registerEmployeeDtoRequest.setSurName("Иванов");
        registerEmployeeDtoRequest.setMiddleName("");
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployeeErrorCode.EMPLOYEE_WRONG_MIDDLENAME.getErrorString())), server.registerEmployee(gson.toJson(registerEmployeeDtoRequest)));
        registerEmployeeDtoRequest.setMiddleName("Иванович");
        registerEmployeeDtoRequest.setEmail("ivanmail.ru");
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployeeErrorCode.EMPLOYEE_WRONG_EMAIL.getErrorString())), server.registerEmployee(gson.toJson(registerEmployeeDtoRequest)));
        registerEmployeeDtoRequest.setEmail("ivan@mail.ru");
        registerEmployeeDtoRequest.setPassword("fdgdhdh");
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployeeErrorCode.EMPLOYEE_WRONG_PASSWORD.getErrorString())), server.registerEmployee(gson.toJson(registerEmployeeDtoRequest)));
        registerEmployeeDtoRequest.setPassword("Iv123vgf");
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest2 = new RegisterEmployeeDtoRequest("Иван", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployeeErrorCode.EMPLOYEE_ALREADY_EXIST.getErrorString())), server.registerEmployee(gson.toJson(registerEmployeeDtoRequest2)));

    }

    @Test
    public void testLoginEmployee() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Иван", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        LoginUserDtoRequest loginUserDtoRequest = new LoginUserDtoRequest("vania", "Iv123vgf");
        assertEquals(server.registerEmployee(gson.toJson(registerEmployeeDtoRequest)), server.loginEmployee(gson.toJson(loginUserDtoRequest)));
        loginUserDtoRequest.setLogin("vaniat");
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployeeErrorCode.EMPLOYEE_WRONG_LOGIN.getErrorString())), server.loginEmployee(gson.toJson(loginUserDtoRequest)));
        loginUserDtoRequest.setLogin("vania");
        loginUserDtoRequest.setPassword("1235");
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployeeErrorCode.EMPLOYEE_WRONG_PASSWORD.getErrorString())), server.loginEmployee(gson.toJson(loginUserDtoRequest)));
    }

    @Test
    public void testDeleteEmloyee() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Иван", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String s0 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        server.deleteEmployee(s0);
        assertEquals(0, DataBase.getDataBase().getUsersCount() );
    }


    @Test
    public void testModificationEmployee() throws EmployeeException {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Иван", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String s0 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(s0, RegisterEmployeeDtoResponse.class);
        ModificationEmployeeDtoRequest modificationEmployeeDtoRequest = new ModificationEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Иван", "Петров", "Иванович", "Iv123vgf", "ivan@mail.ru");
        String s1 = server.modificationEmployee(gson.toJson(modificationEmployeeDtoRequest));
        assertEquals(gson.toJson(new EmptySuccessResponse()), s1);
        assertEquals("Петров", DataBase.getDataBase().selectEmployee(registerEmployeeDtoResponse.getToken()).getSurName());
        ModificationEmployeeDtoRequest modificationEmployeeDtoRequest2 = new ModificationEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Иван", "", "Иванович", "Iv123vgf", "ivan@mail.ru");
        String s2 = server.modificationEmployee(gson.toJson(modificationEmployeeDtoRequest2));
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployeeErrorCode.EMPLOYEE_WRONG_SURNAME.getErrorString())), s2);
    }

    @Test
    public void testActiveEmployee() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Иван", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String s0 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(s0, RegisterEmployeeDtoResponse.class);
        ActiveEmployeeDtoRequest activeEmployeeDtoRequest = new ActiveEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), false);
        server.setActiveEmployee(gson.toJson(activeEmployeeDtoRequest));
        assertEquals(false, DataBase.getDataBase().selectEmployee(registerEmployeeDtoResponse.getToken()).isActive());
    }

    @Test
    public void testAddSkillEmployee() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Иван", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String s0 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(s0, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Java", 3);
        String s = server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        assertEquals(gson.toJson(new EmptySuccessResponse()), s);
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        assertEquals(3, DataBase.getDataBase().selectEmployee(registerEmployerDtoResponse.getToken()).getLavelForSkill("Java"));
    }

    @Test
    public void testDeleteSkillEmployee() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Иван", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String s0 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(s0, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Java", 3);
        String s = server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        assertEquals(gson.toJson(new EmptySuccessResponse()), s);
        String s1 = server.deleteSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        assertEquals(gson.toJson(new EmptySuccessResponse()), s1);
        assertEquals(0, DataBase.getDataBase().selectEmployee(registerEmployeeDtoResponse.getToken()).getLavelForSkill("Java"));
    }


    @Test
    public void testChangeSkillEmployee() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Иван", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String s0 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(s0, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Java", 3);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        skillEmployeeDtoRequest.setLevel(4);
        String s1 = server.changeSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        assertEquals(gson.toJson(new EmptySuccessResponse()), s1);
        assertEquals(4, DataBase.getDataBase().selectEmployee(registerEmployeeDtoResponse.getToken()).getLavelForSkill("Java"));

    }

    @Test
    public void testgetAllVacancieForSkillsEmployeeAllRequirement() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Иван", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String s0 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(s0, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Java", 3);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s1 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s1, RegisterEmployerDtoResponse.class);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest1 = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest2 = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Тестировщик", 50000);
        Requirement requirement1 = new Requirement("Java", 5, true);
        Requirement requirement2 = new Requirement("C++", 2, false);
        Requirement requirement3 = new Requirement("Java", 2, true);
        addVacancieEmployerDtoRequest1.setRequirement(requirement1);
        addVacancieEmployerDtoRequest1.setRequirement(requirement2);
        addVacancieEmployerDtoRequest2.setRequirement(requirement3);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest1));
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest2));
        String result = server.getAllVacanciesForSkillsEmployeeAllRequirement(s0);
        Vacancy vacancy = new Vacancy("Тестировщик", 50000);
        vacancy.setRequirement(requirement3);
        Set<Vacancy> vacancies = new TreeSet<>();
        vacancies.add(vacancy);
        assertEquals(gson.toJson(vacancies), result);
    }

    @Test
    public void testgetAllVacancieForAllSkillsEmployeeRequredRequirement() throws IOException {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Алексей", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String s0 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(s0, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Java", 3);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s1 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s1, RegisterEmployerDtoResponse.class);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest1 = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest2 = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Тестировщик", 50000);
        Requirement requirement1 = new Requirement("Java", 2, true);
        Requirement requirement2 = new Requirement("C++", 2, false);
        Requirement requirement3 = new Requirement("Java", 3, true);
        addVacancieEmployerDtoRequest1.setRequirement(requirement1);
        addVacancieEmployerDtoRequest1.setRequirement(requirement2);
        addVacancieEmployerDtoRequest2.setRequirement(requirement3);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest1));
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest2));
        String s = server.getAllVacanciesForAllSkillsEmployeeRequredRequirement(s0);
        Vacancy vacancy1 = new Vacancy(addVacancieEmployerDtoRequest2.getJobTitle(), addVacancieEmployerDtoRequest2.getSalary());
        Vacancy vacancy2 = new Vacancy(addVacancieEmployerDtoRequest1.getJobTitle(), addVacancieEmployerDtoRequest1.getSalary());
        vacancy1.setRequirements(addVacancieEmployerDtoRequest2.getRequirements());
        vacancy2.setRequirements(addVacancieEmployerDtoRequest1.getRequirements());
        Set<Vacancy> vacancies = new TreeSet<>();
        vacancies.add(vacancy2);
        vacancies.add(vacancy1);
        assertEquals(gson.toJson(vacancies), s);
    }

    @Test
    public void testgetAllVacancieForAllSkillsEmployeeAllRequirementNotLevel() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Алексей", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String string0 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(string0, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Java", 3);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String string1 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(string1, RegisterEmployerDtoResponse.class);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest1 = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest2 = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Тестировщик", 50000);
        Requirement requirement1 = new Requirement("Java", 2, true);
        Requirement requirement2 = new Requirement("C++", 2, false);
        Requirement requirement3 = new Requirement("Java", 5, true);
        addVacancieEmployerDtoRequest1.setRequirement(requirement1);
        addVacancieEmployerDtoRequest1.setRequirement(requirement2);
        addVacancieEmployerDtoRequest2.setRequirement(requirement3);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest1));
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest2));
        String result = server.getAllVacancieForAllSkillsEmployeeAllRequirementNotLevel(string0);
        Vacancy vacancy1 = new Vacancy(addVacancieEmployerDtoRequest1.getJobTitle(), addVacancieEmployerDtoRequest2.getSalary());
        Vacancy vacancy2 = new Vacancy(addVacancieEmployerDtoRequest2.getJobTitle(), addVacancieEmployerDtoRequest1.getSalary());
        vacancy1.setRequirements(addVacancieEmployerDtoRequest1.getRequirements());
        vacancy2.setRequirements(addVacancieEmployerDtoRequest2.getRequirements());
        List<Vacancy> vacancies = new ArrayList<>();
        vacancies.add(vacancy1);
        vacancies.add(vacancy2);
        assertEquals(gson.toJson(vacancies), result);
    }

    @Test
    public void testgetAllVacancieForAllSkillsEmployeeOneRequirement() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Алексей", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String string0 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(string0, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Java", 2);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        SkillEmployeeDtoRequest skillEmployeeDtoRequest2 = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "C++", 2);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest2));
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String string1 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(string1, RegisterEmployerDtoResponse.class);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest1 = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest2 = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Тестировщик", 50000);
        Requirement requirement1 = new Requirement("Java", 2, true);
        Requirement requirement2 = new Requirement("C++", 2, false);
        Requirement requirement3 = new Requirement("Java", 2, true);
        addVacancieEmployerDtoRequest1.setRequirement(requirement1);
        addVacancieEmployerDtoRequest1.setRequirement(requirement2);
        addVacancieEmployerDtoRequest2.setRequirement(requirement3);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest1));
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest2));
        String result = server.getAllVacanciesForAllSkillsEmployeeOneRequirement(string0);
        Vacancy vacancy1 = new Vacancy(addVacancieEmployerDtoRequest1.getJobTitle(), addVacancieEmployerDtoRequest2.getSalary());
        Vacancy vacancy2 = new Vacancy(addVacancieEmployerDtoRequest2.getJobTitle(), addVacancieEmployerDtoRequest1.getSalary());
        vacancy1.setRequirements(addVacancieEmployerDtoRequest1.getRequirements());
        vacancy2.setRequirements(addVacancieEmployerDtoRequest2.getRequirements());
        Map<Integer, Set<Vacancy>> vacanciesMap = new TreeMap<>((s1, s2) -> s2 - s1);
        Set<Vacancy> vacancies1 = new TreeSet<>();
        vacancies1.add(vacancy1);
        Set<Vacancy> vacancies2 = new TreeSet<>();
        vacancies2.add(vacancy2);
        vacanciesMap.put(2, vacancies1);
        vacanciesMap.put(1, vacancies2);
        assertEquals(gson.toJson(vacanciesMap), result);
    }

}
