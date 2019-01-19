package net.thumbtack.school.hiring.request;

import java.util.UUID;

public class DeleteEmployerDtoRequest {
    private UUID token;

    public DeleteEmployerDtoRequest(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }
}
