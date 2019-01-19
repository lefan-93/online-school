package net.thumbtack.school.hiring.request;

import java.util.UUID;

public class SelectVacancieDtoRequest {

    private UUID token;
    private String jobTitle;

    public SelectVacancieDtoRequest(UUID token, String jobTitle) {
        this.token = token;
        this.jobTitle = jobTitle;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}
