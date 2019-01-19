package net.thumbtack.school.hiring.request;

import java.util.UUID;

public class ActiveVacancyDtoRequest extends SelectVacancieDtoRequest {
    private boolean active;

    public ActiveVacancyDtoRequest(UUID token, String jobTitle, boolean active) {
        super(token, jobTitle);
        this.active = active;
    }

}
