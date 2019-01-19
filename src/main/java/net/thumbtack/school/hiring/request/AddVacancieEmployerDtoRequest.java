package net.thumbtack.school.hiring.request;

import net.thumbtack.school.hiring.model.Vacancy;

import java.util.UUID;

public class AddVacancieEmployerDtoRequest extends VacancyDtoRequest {
    private UUID token;

    public AddVacancieEmployerDtoRequest(UUID token, String jobTitle, int salary) {
        super(jobTitle, salary);
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

}
