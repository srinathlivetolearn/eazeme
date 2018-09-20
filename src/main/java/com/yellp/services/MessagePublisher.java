package com.yellp.services;

public interface MessagePublisher {
    boolean publishMessage(Object message, String senderId);
}
