package o.springback.exception;
import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private List<ErrorItem> errors = new ArrayList<>();

    public void addError(ErrorItem error) {
        errors.add(error);
    }

    public List<ErrorItem> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorItem> errors) {
        this.errors = errors;
    }
}