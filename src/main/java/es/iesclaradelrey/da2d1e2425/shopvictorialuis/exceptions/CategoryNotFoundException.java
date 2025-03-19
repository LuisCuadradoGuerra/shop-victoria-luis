package es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    private String detail;


    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public CategoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
