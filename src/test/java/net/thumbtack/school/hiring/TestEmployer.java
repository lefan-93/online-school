package net.thumbtack.school.hiring;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.errorscode.EmployerErrorCode;
import net.thumbtack.school.hiring.model.*;
import net.thumbtack.school.hiring.request.*;
import net.thumbtack.school.hiring.response.*;
import net.thumbtack.school.hiring.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static net.thumbtack.school.hiring.database.DataBase.getDataBase;
import static org.junit.Assert.assertEquals;

public class TestEmployer {

    private Server server;
    private Gson gson;

    @Before
    public void startServer () throws IOException{
        gson = new Gson();
        server = new Server();
        server.startServer(null);
    }

    @After
    public void stopServer () throws IOException {
        server.stopServer(null);
    }

    @Test
    public void testRegisterEmploer()  {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        LoginUserDtoRequest loginUserDtoRequest = new LoginUserDtoRequest("vania", "Iv123vgf");
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s, RegisterEmployerDtoResponse.class);
        String s0 = server.loginEmployer(gson.toJson(loginUserDtoRequest));
        assertEquals(s, s0);
        String s1 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployerErrorCode.EMPLOYER_ALREADY_EXIST.getErrorString())), s1 );
        registerEmployerDtoRequest.setFirstName("");
        String s2 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployerErrorCode.EMPLOYER_WRONG_FIRSTNAME.getErrorString())),s2);
        registerEmployerDtoRequest.setFirstName("Иван");
        registerEmployerDtoRequest.setSurName("");
        String s3 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployerErrorCode.EMPLOYER_WRONG_SURNAME.getErrorString())),s3 );
        registerEmployerDtoRequest.setSurName("Иванов");
        registerEmployerDtoRequest.setMiddleName("");
        String s4 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployerErrorCode.EMPLOYER_WRONG_MIDDLENAME.getErrorString())),s4);
        registerEmployerDtoRequest.setMiddleName("Иванович");
        registerEmployerDtoRequest.setEmail("ivanmail.ru");
        String s5 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployerErrorCode.EMPLOYER_WRONG_EMAIL.getErrorString())),s5);
        registerEmployerDtoRequest.setEmail("ivanI@mail.ru");
        registerEmployerDtoRequest.setPassword("ivanmail.ru");
        String s6 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployerErrorCode.EMPLOYER_WRONG_PASSWORD.getErrorString())),s6 );
        RegisterEmployerDtoRequest registerEmployerDtoRequest1 = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivanI@mail.ru", "vaniaI", "Iv123vgf");
        String s7 = server.registerEmployee(gson.toJson(registerEmployerDtoRequest1));
        LoginUserDtoRequest loginUserDtoRequest1 = new LoginUserDtoRequest("vaniaI", "Iv123vgf");
        assertEquals(s7, server.loginEmployee(gson.toJson(loginUserDtoRequest1)));
    }

    @Test
    public void modificationEmployer() throws IOException {

        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s, RegisterEmployerDtoResponse.class);
        ModificationEmployerDtoRequest modificationEmployerDtoRequest = new ModificationEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "company","Omsk Mira17",  "Иван", "Петров", "Иванович", "petrov@mail.ru", "Iv12Fgfgn3vgf");
        String s1 = server.modificationEmployer(gson.toJson(modificationEmployerDtoRequest));
        assertEquals(gson.toJson(new EmptySuccessResponse()),s1 );
        assertEquals("Петров", getDataBase().selectEmployer(registerEmployerDtoResponse.getToken()).getSurName());
        modificationEmployerDtoRequest.setSurName("");
        gson.toJson(modificationEmployerDtoRequest);
        String s2 = server.modificationEmployer(gson.toJson(modificationEmployerDtoRequest));
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployerErrorCode.EMPLOYER_WRONG_SURNAME.getErrorString())),s2);

    }

    @Test
    public void testLoginEmployer() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        LoginUserDtoRequest loginUserDtoRequest = new LoginUserDtoRequest("vania", "Iv123vgf");
        String s1 = server.loginEmployer(gson.toJson(loginUserDtoRequest));
        assertEquals(s0, s1);
        loginUserDtoRequest.setLogin("vaniat");
        String s2 = server.loginEmployer(gson.toJson(loginUserDtoRequest));
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployerErrorCode.EMPLOYER_WRONG_LOGIN.getErrorString())),s2);
        loginUserDtoRequest.setLogin("vania");
        loginUserDtoRequest.setPassword("1234");
        String s3 = server.loginEmployer(gson.toJson(loginUserDtoRequest));
        assertEquals(gson.toJson(new ErrorDtoResponse(EmployerErrorCode.EMPLOYER_WRONG_PASSWORD.getErrorString())),s3);
    }

    @Test
    public void testDeleteEmloyer() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        String s1 = server.deleteEmployer(s0);
        assertEquals(gson.toJson(new EmptySuccessResponse()), s1);
        assertEquals(0, getDataBase().getUsersCount());
    }

    @Test
    public void testAddVacancieEmployer(){
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 2, true);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        String s1 = server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        assertEquals(gson.toJson(new EmptySuccessResponse()),s1);
        DataBase dataBase = getDataBase();
        assertEquals("Java", dataBase.selectEmployer(registerEmployerDtoResponse.getToken()).getVacancis().get(0).getRequirements().get(0).getName());
    }

    @Test
    public void testAddRequirementVacancieEmployer(){
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 2, true);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        Requirement requirement2 = new Requirement("C++", 2, true);
        AddRequirementEmployerDtoRequest addRequirementEmployerDtoRequest = new AddRequirementEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист");
        addRequirementEmployerDtoRequest.setRequirement(requirement2);
        server.addRequirementVacancieEmployer(gson.toJson(addRequirementEmployerDtoRequest));
        assertEquals("C++", getDataBase().selectEmployer(registerEmployerDtoResponse.getToken()).getVacancis().get(0).getRequirements().get(1).getName());
        Requirement requirement3 = new Requirement("Java", 3, true);
        UpdateRequirmentVacancyDtoRequest updateRequirmentVacancyDtoRequest = new UpdateRequirmentVacancyDtoRequest(registerEmployerDtoResponse.getToken(),"Программист", requirement1,requirement3);
        server.updateRequirementVacancieEmploer(gson.toJson(updateRequirmentVacancyDtoRequest));
        assertEquals(3, getDataBase().selectEmployer(registerEmployerDtoResponse.getToken()).getVacancis().get(0).getRequirements().get(0).getLevel());
    }

    @Test
    public void testDeleteRequirementVacancieEmployer() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 2, true);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        AddRequirementEmployerDtoRequest addRequirementEmployerDtoRequest = new AddRequirementEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист");
        addRequirementEmployerDtoRequest.setRequirement(requirement1);
        server.deleteRequirementVacancieEmploer(gson.toJson(addRequirementEmployerDtoRequest));
        DataBase dataBase = getDataBase();
        assertEquals(0, dataBase.selectEmployer(registerEmployerDtoResponse.getToken()).getVacancis().get(0).getRequirements().size());

    }

    @Test
    public void testUpdateRequirementVacancieEmployer()  {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 2, true);
        Requirement updateRequirement = new Requirement("Java",3 , true);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        UpdateRequirmentVacancyDtoRequest updateRequirmentVacancyDtoRequest = new UpdateRequirmentVacancyDtoRequest(registerEmployerDtoResponse.getToken(),"Программист", requirement1, updateRequirement);
        server.updateRequirementVacancieEmploer(gson.toJson(updateRequirmentVacancyDtoRequest));
        assertEquals(3, getDataBase().selectEmployer(registerEmployerDtoResponse.getToken()).getVacancis().get(0).getRequirements().get(0).getLevel());

    }

   @Test
    public void testActiveVacancieEmployer() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 2, true);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        ActiveVacancyDtoRequest activeVacancyDtoRequest = new ActiveVacancyDtoRequest(registerEmployerDtoResponse.getToken(), "Программист",false);
        String s = gson.toJson(activeVacancyDtoRequest);
        server.isActiviteVacancieEmployer(gson.toJson(activeVacancyDtoRequest));
        DataBase dataBase = getDataBase();
        assertEquals(false, dataBase.selectEmployer(registerEmployerDtoResponse.getToken()).getVacancis().get(0).isActive());

    }

    @Test
   public void testGetAllVacancieEmployer() {

        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 2, true);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        Vacancy vacancy = new Vacancy(addVacancieEmployerDtoRequest.getJobTitle(),addVacancieEmployerDtoRequest.getSalary());
        vacancy.setRequirement(requirement1);
        Set<Vacancy> vacancies = new HashSet<>();
        vacancies.add(vacancy);
        assertEquals(gson.toJson(vacancies), server.getAllVacancieEmployer(s0));

    }

    @Test
    public void testGetActiviteVacancieEmployer() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 2, true);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        Vacancy vacancy = new Vacancy(addVacancieEmployerDtoRequest.getJobTitle(),addVacancieEmployerDtoRequest.getSalary());
        vacancy.setRequirement(requirement1);
        Set<Vacancy> vacancies = new HashSet<>();
        vacancies.add(vacancy);
        assertEquals(gson.toJson(vacancies), server.getActiviteVacancieEmployer(s0));

    }

    @Test
    public void testGetNotActiviteVacancieEmployer()  {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 2, true);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        Vacancy vacancy = new Vacancy(addVacancieEmployerDtoRequest.getJobTitle(),addVacancieEmployerDtoRequest.getSalary());
        vacancy.setRequirement(requirement1);
        Set<Vacancy> vacancies = new HashSet<>();
        assertEquals(gson.toJson(vacancies), server.getNotActiviteVacancieEmployer(s0));
    }

    @Test
    public void testDeleteVacancieEmployer()  {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 2, true);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        SelectVacancieDtoRequest selectVacancieDtoRequest = new SelectVacancieDtoRequest(registerEmployerDtoResponse.getToken(),"Программист");
        server.deleteVacancieEmployer(gson.toJson(selectVacancieDtoRequest));
        DataBase dataBase = getDataBase();
        assertEquals(0, dataBase.selectEmployer(registerEmployerDtoResponse.getToken()).getVacancis().size());
    }

    @Test
    public void testAllEmloyeesForAllRequirementVacancie()  {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Алексей", "Иванов", "Иванович", "vania", "Iv123vgf", "ivan@mail.ru");
        String s0 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(s0, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Java", 2);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest2 = new RegisterEmployeeDtoRequest("Петр", "Иванов", "Иванович", "petr", "Iv123vgf", "petr@mail.ru");
        String s1 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest2));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse2 = gson.fromJson(s1, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest2 = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse2.getToken(), "Java", 5);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest2));
        SkillEmployeeDtoRequest skillEmployeeDtoRequest3 = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse2.getToken(), "C++", 5);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest3));
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s2 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s2, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 2, true);
        Requirement requirement2 = new Requirement("C++", 2, false);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        addVacancieEmployerDtoRequest.setRequirement(requirement2);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        SelectVacancieDtoRequest selectVacancieDtoRequest = new SelectVacancieDtoRequest(registerEmployerDtoResponse.getToken(),"Программист");
        String s = server.getAllEmloyesForAllRequirementVacancie(gson.toJson(selectVacancieDtoRequest));
        EmployeeForVacancyDtoResponse employeeForVacancyDtoResponse = new EmployeeForVacancyDtoResponse(registerEmployeeDtoRequest2.getFirstName(),registerEmployeeDtoRequest2.getSurName(),registerEmployeeDtoRequest2.getEmail(),(getDataBase().selectEmployee(registerEmployeeDtoResponse2.getToken()).getSkills()));
        Set<EmployeeForVacancyDtoResponse> employeeForVacancyDtoResponses= new HashSet<>();
        employeeForVacancyDtoResponses.add(employeeForVacancyDtoResponse);
        assertEquals(gson.toJson(employeeForVacancyDtoResponses), s);
    }

    @Test
    public void testAllEmployeesForRequiredRequirementVacancie() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 3, true);
        Requirement requirement2 = new Requirement("C++", 2, false);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        addVacancieEmployerDtoRequest.setRequirement(requirement2);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Алексей", "Иванов", "Иванович", "alex", "Iv123vgf", "ivan@mail.ru");
        String s1 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(s1, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Java", 5);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest2 = new RegisterEmployeeDtoRequest("Петр", "Иванов", "Иванович", "petr", "Iv101vgf", "petr@mail.ru");
        String s2 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest2));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse2 = gson.fromJson(s2, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest2 = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse2.getToken(), "Java", 1);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest2));
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest3 = new RegisterEmployeeDtoRequest("Саша", "Иванова", "Ивановна", "Sasha", "Iv458vgf", "Sasha@mail.ru");
        String s3 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest3));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse3 = gson.fromJson(s3, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest3 = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse3.getToken(), "C++", 5);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest3));
        SelectVacancieDtoRequest selectVacancieDtoRequest = new SelectVacancieDtoRequest(registerEmployerDtoResponse.getToken(),"Программист");
        String s = server.getAllEmloyesForRequiredRequirementVacancie(gson.toJson(selectVacancieDtoRequest));
        EmployeeForVacancyDtoResponse employeeForVacancyDtoResponse = new EmployeeForVacancyDtoResponse(registerEmployeeDtoRequest.getFirstName(),registerEmployeeDtoRequest.getSurName(),registerEmployeeDtoRequest.getEmail(),(getDataBase().selectEmployee(registerEmployeeDtoResponse.getToken()).getSkills()));
        Set<EmployeeForVacancyDtoResponse> employeeForVacancyDtoResponses= new HashSet<>();
        employeeForVacancyDtoResponses.add(employeeForVacancyDtoResponse);
        assertEquals(gson.toJson(employeeForVacancyDtoResponses), s);

    }

    @Test
    public void testAllEmployesForAllRequirementNotLevelVacancie() {
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Алексей", "Иванов", "Иванович", "alex", "Iv123vgf", "ivan@mail.ru");
        String s1 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(s1, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Java", 5);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest2 = new RegisterEmployeeDtoRequest("Петр", "Иванов", "Иванович", "petr", "Iv101vgf", "petr@mail.ru");
        String s2 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest2));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse2 = gson.fromJson(s2, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest2 = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse2.getToken(), "Java", 1);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest2));
        SkillEmployeeDtoRequest skillEmployeeDtoRequest3 = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse2.getToken(), "C++", 1);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest3));
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 1, true);
        Requirement requirement2 = new Requirement("C++", 1, false);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        addVacancieEmployerDtoRequest.setRequirement(requirement2);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        SelectVacancieDtoRequest selectVacancieDtoRequest = new SelectVacancieDtoRequest(registerEmployerDtoResponse.getToken(), "Программист");
        Vacancy vacancy = (DataBase.getDataBase().selectEmployer(selectVacancieDtoRequest.getToken()).getVacancie(selectVacancieDtoRequest.getJobTitle()));
        EmployeeForVacancyDtoResponse employeeForVacancyDtoResponse = new EmployeeForVacancyDtoResponse(registerEmployeeDtoRequest2.getFirstName(),registerEmployeeDtoRequest2.getSurName(),registerEmployeeDtoRequest2.getEmail(),(getDataBase().selectEmployee(registerEmployeeDtoResponse2.getToken()).getSkills()));
        Set<EmployeeForVacancyDtoResponse> employeeForVacancyDtoResponses= new HashSet<>();
        employeeForVacancyDtoResponses.add(employeeForVacancyDtoResponse);
        String s = server.getAllEmloyesForAllRequirementNotLevelVacancie(gson.toJson(selectVacancieDtoRequest));
        assertEquals(gson.toJson(employeeForVacancyDtoResponses), s);
    }

    @Test
    public void testAllEmployeesForOneRequirementVacancie() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("company", "Omsk Mira17", "Иван", "Иванов", "Иванович", "ivan@mail.ru", "vania", "Iv123vgf");
        String s0 = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));
        RegisterEmployerDtoResponse registerEmployerDtoResponse = gson.fromJson(s0, RegisterEmployerDtoResponse.class);
        Requirement requirement1 = new Requirement("Java", 5, true);
        Requirement requirement2 = new Requirement("C++", 2, false);
        AddVacancieEmployerDtoRequest addVacancieEmployerDtoRequest = new AddVacancieEmployerDtoRequest(registerEmployerDtoResponse.getToken(), "Программист", 50000);
        addVacancieEmployerDtoRequest.setRequirement(requirement1);
        addVacancieEmployerDtoRequest.setRequirement(requirement2);
        server.addVacancieEmployer(gson.toJson(addVacancieEmployerDtoRequest));
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest = new RegisterEmployeeDtoRequest("Алексей", "Иванов", "Иванович", "alex", "Iv123vgf", "ivan@mail.ru");
        String s1 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse = gson.fromJson(s1, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse.getToken(), "Java", 5);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest));
        RegisterEmployeeDtoRequest registerEmployeeDtoRequest2 = new RegisterEmployeeDtoRequest("Петр", "Иванов", "Иванович", "petr", "Iv101vgf", "petr@mail.ru");
        String s2 = server.registerEmployee(gson.toJson(registerEmployeeDtoRequest2));
        RegisterEmployeeDtoResponse registerEmployeeDtoResponse2 = gson.fromJson(s2, RegisterEmployeeDtoResponse.class);
        SkillEmployeeDtoRequest skillEmployeeDtoRequest2 = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse2.getToken(), "Java", 1);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest2));
        SkillEmployeeDtoRequest skillEmployeeDtoRequest3 = new SkillEmployeeDtoRequest(registerEmployeeDtoResponse2.getToken(), "C++", 1);
        server.addSkillEmployee(gson.toJson(skillEmployeeDtoRequest3));
        SelectVacancieDtoRequest selectVacancieDtoRequest = new SelectVacancieDtoRequest(registerEmployerDtoResponse.getToken(),"Программист");
        String s = server.getAllEmloyesForOneRequirementVacancie(gson.toJson(selectVacancieDtoRequest));
        EmployeeForVacancyDtoResponse employeeForVacancyDtoResponse = new EmployeeForVacancyDtoResponse(registerEmployeeDtoRequest.getFirstName(),registerEmployeeDtoRequest.getSurName(),registerEmployeeDtoRequest.getEmail(),(getDataBase().selectEmployee(registerEmployeeDtoResponse.getToken()).getSkills()));
        Set<EmployeeForVacancyDtoResponse> employeeForVacancyDtoResponses= new HashSet<>();
        employeeForVacancyDtoResponses.add(employeeForVacancyDtoResponse);
        assertEquals(gson.toJson(employeeForVacancyDtoResponses), s);
    }
}
