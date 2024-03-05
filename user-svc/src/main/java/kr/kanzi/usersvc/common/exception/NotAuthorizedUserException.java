package kr.kanzi.usersvc.common.exception;

public class NotAuthorizedUserException extends RuntimeException{
    public NotAuthorizedUserException(String message) {
        super(message);
    }
}
