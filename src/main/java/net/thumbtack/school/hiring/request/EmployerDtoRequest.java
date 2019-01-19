package net.thumbtack.school.hiring.request;

import net.thumbtack.school.hiring.errorscode.EmployerErrorCode;
import net.thumbtack.school.hiring.exception.EmployerException;

public class EmployerDtoRequest {

    private String companyName;
    private String address;
    private String firstName;
    private String surName;
    private String middleName;
    private String email;
    private String password;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String PASSWORD_PATTERN ="(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}";

    public EmployerDtoRequest(String companyName, String adress, String firstName, String surName, String middleName, String email, String password) {
        this.companyName = companyName;
        this.address = adress;
        this.firstName = firstName;
        this.surName = surName;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void validate() throws EmployerException {
        if(companyName == null || companyName.equals("")){
            throw new EmployerException(EmployerErrorCode.EMPLOYER_WRONG_COMPANY_NAME);
        }
        if(surName == null || address.equals("")) {
            throw new EmployerException(EmployerErrorCode.EMPLOYER_WRONG_ADDRESS);
        }
        if(firstName == null || firstName.equals("")){
            throw new EmployerException(EmployerErrorCode.EMPLOYER_WRONG_FIRSTNAME);
        }
        if(surName.equals("")) {
            throw new EmployerException(EmployerErrorCode.EMPLOYER_WRONG_SURNAME);
        }
        if(middleName == null || middleName.equals("")) {
            throw new EmployerException(EmployerErrorCode.EMPLOYER_WRONG_MIDDLENAME);
        }
        if(email == null || !email.matches(EMAIL_PATTERN)){
            throw new EmployerException(EmployerErrorCode.EMPLOYER_WRONG_EMAIL);
        }
        if(password == null || !password.matches(PASSWORD_PATTERN)){
            throw new EmployerException(EmployerErrorCode.EMPLOYER_WRONG_PASSWORD);
        }

    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
