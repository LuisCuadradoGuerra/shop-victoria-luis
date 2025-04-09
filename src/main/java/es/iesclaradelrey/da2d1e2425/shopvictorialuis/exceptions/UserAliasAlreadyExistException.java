package es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions;

public class UserAliasAlreadyExistException extends RuntimeException {
    public UserAliasAlreadyExistException(String appUserAlias) {
        super(String.format("UserAlias: %s already exists", appUserAlias));
    }

}
