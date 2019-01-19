package net.thumbtack.school.hiring.request;

import java.util.UUID;

public class ModificationEmployerDtoRequest extends EmployerDtoRequest {
    private UUID token;

    public ModificationEmployerDtoRequest(UUID token, String companyName, String adress, String firstName, String surName, String middleName, String email, String password) {
        super(companyName, adress, firstName, surName, middleName, email, password);
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }
}
