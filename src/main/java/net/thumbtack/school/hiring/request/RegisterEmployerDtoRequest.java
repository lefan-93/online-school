package net.thumbtack.school.hiring.request;

public class RegisterEmployerDtoRequest extends EmployerDtoRequest{
    private String login;

    public String getLogin() {
        return login;
    }

    public RegisterEmployerDtoRequest(String companyName, String address, String firstName, String surName, String middleName, String email, String login, String password ) {
        super(companyName, address, firstName, surName, middleName, email, password);
        this.login = login;


    }


}
