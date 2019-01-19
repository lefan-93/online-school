package net.thumbtack.school.hiring.request;

import java.util.UUID;

public class ActiveEmployeeDtoRequest extends SelectEmployeeDtoRequest {

    private boolean active;

    public ActiveEmployeeDtoRequest(UUID token, boolean active) {
        super(token);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
