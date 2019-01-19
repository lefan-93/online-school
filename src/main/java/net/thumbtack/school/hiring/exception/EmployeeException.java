package net.thumbtack.school.hiring.exception;

import net.thumbtack.school.hiring.errorscode.EmployeeErrorCode;

public class EmployeeException extends Exception {
    private EmployeeErrorCode employeeErrorCode;

    public EmployeeException(EmployeeErrorCode employeeErrorCode) {
        this.employeeErrorCode = employeeErrorCode;
    }

    public EmployeeException(String message, EmployeeErrorCode employeeErrorCode) {
        super(message);
        this.employeeErrorCode = employeeErrorCode;
    }

    public EmployeeException(String message, Throwable cause, EmployeeErrorCode employeeErrorCode) {
        super(message, cause);
        this.employeeErrorCode = employeeErrorCode;
    }

    public EmployeeException(Throwable cause, EmployeeErrorCode employeeErrorCode) {
        super(cause);
        this.employeeErrorCode = employeeErrorCode;
    }

    public EmployeeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, EmployeeErrorCode employeeErrorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.employeeErrorCode = employeeErrorCode;
    }

    public EmployeeErrorCode getEmployeeErrorCode() {
        return employeeErrorCode;
    }
}
