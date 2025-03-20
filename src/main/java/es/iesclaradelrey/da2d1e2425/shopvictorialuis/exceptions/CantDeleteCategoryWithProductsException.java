package es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions;

public class CantDeleteCategoryWithProductsException extends RuntimeException {
    private String detail;


    public CantDeleteCategoryWithProductsException(String message) {
        super(message);
    }

    public CantDeleteCategoryWithProductsException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public CantDeleteCategoryWithProductsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CantDeleteCategoryWithProductsException(Throwable cause) {
        super(cause);
    }

    public CantDeleteCategoryWithProductsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
