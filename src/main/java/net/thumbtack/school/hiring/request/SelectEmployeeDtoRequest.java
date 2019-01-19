package net.thumbtack.school.hiring.request;

import java.util.UUID;

public class SelectEmployeeDtoRequest {

    private UUID token;

    SelectEmployeeDtoRequest(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
