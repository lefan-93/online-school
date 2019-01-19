package net.thumbtack.school.hiring.response;

import java.util.UUID;

public class RegisterEmployeeDtoResponse {

    private UUID token;

    public RegisterEmployeeDtoResponse(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }
}
