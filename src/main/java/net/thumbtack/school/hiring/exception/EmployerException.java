package net.thumbtack.school.hiring.exception;


import net.thumbtack.school.hiring.errorscode.EmployerErrorCode;

public class EmployerException extends Exception {
    private EmployerErrorCode employerErrorCode;

    public EmployerException(EmployerErrorCode employeeErrorCode) {
        this.employerErrorCode = employeeErrorCode;
    }

    public EmployerException(String message, EmployerErrorCode employeeErrorCode) {
        super(message);
        this.employerErrorCode = employeeErrorCode;
    }

    public EmployerException(String message, Throwable cause, EmployerErrorCode employeeErrorCode) {
        super(message, cause);
        this.employerErrorCode = employeeErrorCode;
    }

    public EmployerException(Throwable cause, EmployerErrorCode employeeErrorCode) {
        super(cause);
        this.employerErrorCode = employeeErrorCode;
    }

    public EmployerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, EmployerErrorCode employeeErrorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.employerErrorCode = employerErrorCode;
    }

    public EmployerErrorCode getEmployerErrorCode() {
        return employerErrorCode;
    }
}
