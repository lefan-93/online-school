package net.thumbtack.school.hiring.request;


import net.thumbtack.school.hiring.errorscode.EmployeeErrorCode;
import net.thumbtack.school.hiring.exception.EmployeeException;

public class EmployeeDtoRequest {

    private String firstName;
    private String surName;
    private String middleName = null;
    private String password;
    private String email;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String PASSWORD_PATTERN = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}";

    public EmployeeDtoRequest(String firstName, String surName, String middleName, String password, String email) {
        this.firstName = firstName;
        this.surName = surName;
        this.middleName = middleName;
        this.password = password;
        this.email = email;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void validate() throws EmployeeException {
        if (firstName == null || firstName.equals(""))
            throw new EmployeeException(EmployeeErrorCode.EMPLOYEE_WRONG_FIRSTNAME);
        if (surName == null || surName.equals(""))
            throw new EmployeeException(EmployeeErrorCode.EMPLOYEE_WRONG_SURNAME);
        if (middleName == null || middleName.equals(""))
            throw new EmployeeException(EmployeeErrorCode.EMPLOYEE_WRONG_MIDDLENAME);
        if (email != null && !email.matches(EMAIL_PATTERN))
            throw new EmployeeException(EmployeeErrorCode.EMPLOYEE_WRONG_EMAIL);
        if (password != null && !password.matches(PASSWORD_PATTERN))
            throw new EmployeeException(EmployeeErrorCode.EMPLOYEE_WRONG_PASSWORD);

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
