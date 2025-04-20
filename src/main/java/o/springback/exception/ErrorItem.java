package o.springback.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorItem {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;
    private String message;

    // Getters & Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}