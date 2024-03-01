package kr.kanzi.usersvc.domain;

public class NotAuthorizedUserException extends RuntimeException{
    public NotAuthorizedUserException(String message) {
        super(message);
    }
}
