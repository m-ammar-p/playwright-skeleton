package playwright.skeleton.exception;

public class CompanyException extends RuntimeException {

    public CompanyException(String msg) {
        super(msg);
    }

    public CompanyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
