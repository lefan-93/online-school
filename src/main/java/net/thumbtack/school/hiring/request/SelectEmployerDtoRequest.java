package net.thumbtack.school.hiring.request;

import java.util.UUID;

public class SelectEmployerDtoRequest {

    private UUID token;

    public SelectEmployerDtoRequest(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }
}
