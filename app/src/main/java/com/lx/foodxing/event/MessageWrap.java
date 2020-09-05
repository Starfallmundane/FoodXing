package com.lx.foodxing.event;

public class MessageWrap {

    public final String message;

    public static MessageWrap getInstance(String message) {
        return new MessageWrap(message);
    }

    public MessageWrap(String message) {
        this.message = message;
    }
}
