package net.thumbtack.school.hiring.response;

import net.thumbtack.school.hiring.exception.EmployeeException;
import net.thumbtack.school.hiring.exception.EmployerException;

public class ErrorDtoResponse {
    private String error;

    public ErrorDtoResponse(EmployeeException e) {
        error = e.getEmployeeErrorCode().getErrorString();
    }

    public ErrorDtoResponse(EmployerException e) {error = e.getEmployerErrorCode().getErrorString();
    }

    public  ErrorDtoResponse (String error){this.error=error;}
}
