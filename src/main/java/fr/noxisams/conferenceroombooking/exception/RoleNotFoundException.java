package fr.noxisams.conferenceroombooking.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String msg) {
        super(msg);
    }

    public RoleNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
