package net.thumbtack.school.hiring.request;

public class RegisterEmployeeDtoRequest extends EmployeeDtoRequest {

    private String login;

    public RegisterEmployeeDtoRequest(String firstName, String surName, String middleName, String login, String password, String email) {
        super(firstName, surName, middleName, password, email);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }


}
