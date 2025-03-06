package es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions;

public class ProductNotFoundException extends RuntimeException {
    private String detail;


    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProductNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
