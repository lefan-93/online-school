package net.thumbtack.school.hiring.errorscode;

public enum EmployeeErrorCode {
    EMPLOYEE_ALREADY_EXIST("employee already exists"),
    EMPLOYEE_WRONG_FIRSTNAME ("wrong firstname"),
    EMPLOYEE_WRONG_SURNAME("wrong surname"),
    EMPLOYEE_WRONG_MIDDLENAME("wrong middlename"),
    EMPLOYEE_WRONG_EMAIL("wrong email"),
    EMPLOYEE_WRONG_PASSWORD("wrong password"),
    EMPLOYEE_WRONG_LOGIN ("wrong login"),
    EMPLOYEE_NOT_EXIST("employee not exist");

    private String errorString;

    private EmployeeErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
