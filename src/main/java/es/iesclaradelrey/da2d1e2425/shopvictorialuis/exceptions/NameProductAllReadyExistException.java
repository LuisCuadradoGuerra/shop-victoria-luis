package es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions;

public class NameProductAllReadyExistException extends RuntimeException {
    private String detail;


    public NameProductAllReadyExistException(String message) {
        super(message);
    }

    public NameProductAllReadyExistException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public NameProductAllReadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameProductAllReadyExistException(Throwable cause) {
        super(cause);
    }

    public NameProductAllReadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
