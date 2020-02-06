package com.example.websocket.message;

import lombok.Value;
import lombok.With;

import java.util.UUID;

@With
@Value
public class Message {
    private final UUID id;
    private final String sender;
    private final String recipient;
    private final String body;
}
