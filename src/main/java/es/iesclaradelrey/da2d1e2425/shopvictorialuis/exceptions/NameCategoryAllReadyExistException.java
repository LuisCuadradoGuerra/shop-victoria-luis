package es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions;

public class NameCategoryAllReadyExistException extends RuntimeException {
    private String detail;


    public NameCategoryAllReadyExistException(String message) {
        super(message);
    }

    public NameCategoryAllReadyExistException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public NameCategoryAllReadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameCategoryAllReadyExistException(Throwable cause) {
        super(cause);
    }

    public NameCategoryAllReadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
