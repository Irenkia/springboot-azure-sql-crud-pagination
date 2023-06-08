package com.example.springbootazuresqlcrudpagination.exception;

public class ServerError extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ServerError(String message) {
        super(message);
    }
}
