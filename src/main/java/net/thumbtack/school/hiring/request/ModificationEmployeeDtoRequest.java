package net.thumbtack.school.hiring.request;

import net.thumbtack.school.hiring.exception.EmployeeException;

import java.util.UUID;

public class ModificationEmployeeDtoRequest extends EmployeeDtoRequest {
    private UUID token;

    public ModificationEmployeeDtoRequest(UUID token, String firstName, String surName, String middleName,String password, String email ) throws EmployeeException {
        super(firstName, surName, middleName,  password,  email);
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

}
