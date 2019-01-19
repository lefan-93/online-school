package net.thumbtack.school.hiring.request;

public class LoginUserDtoRequest {
    private String login;
    private String password;

    public LoginUserDtoRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
