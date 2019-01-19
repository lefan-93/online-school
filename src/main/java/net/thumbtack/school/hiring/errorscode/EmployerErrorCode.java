// REVU errorscode means "a code of some errors"
// I think you want to have another - a set of codes, every for his error
// so errorcodes
package net.thumbtack.school.hiring.errorscode;

public enum EmployerErrorCode {
    EMPLOYER_WRONG_COMPANY_NAME("employer wrong company name"),
    EMPLOYER_WRONG_ADDRESS("employer wrong address"),
    EMPLOYER_ALREADY_EXIST("employer already exist"),
    EMPLOYER_WRONG_FIRSTNAME ("wrong firstname"),
    EMPLOYER_WRONG_SURNAME("wrong surname"),
    EMPLOYER_WRONG_MIDDLENAME("wrong middlename"),
    EMPLOYER_WRONG_EMAIL("wrong email"),
    EMPLOYER_WRONG_PASSWORD("wrong password"),
    EMPLOYER_WRONG_LOGIN ("wrong login"),
    EMPLOYER_NOT_EXIST("employer not exist");;


    private String errorString;

    private EmployerErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
