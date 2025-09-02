package com.AuthServices.CustomException;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message)
    {
        super(message);
    }
    public RoleNotFoundException(String msg , Throwable cause)
    {
        super(msg,cause);
    }

}
