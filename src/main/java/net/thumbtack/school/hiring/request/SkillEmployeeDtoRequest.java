package net.thumbtack.school.hiring.request;

import java.util.UUID;

public class SkillEmployeeDtoRequest {

    private UUID token;
    private String skill;
    private int level;


    public SkillEmployeeDtoRequest(UUID token, String skill, int level){
        this.token = token;
        this.skill = skill;
        this.level = level;

    }

    public UUID getToken() {
        return token;
    }

    public String getSkill() {
        return skill;
    }

    public int getLevel() {
        return level;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
