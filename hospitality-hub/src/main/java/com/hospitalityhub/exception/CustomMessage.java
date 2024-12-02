package com.hospitalityhub.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public   class CustomMessage {
    private final String message;
    @Override
    public String toString() {
        return "{\n" +
                "\t\"message\": \"" + message + "\"\n" +
                "}";
    }
}
