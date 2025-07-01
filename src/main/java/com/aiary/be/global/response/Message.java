package com.aiary.be.global.response;

public record Message(
    String message
) {
    public static Message from(String message) {
        return new Message(message);
    }
}
