package com.example.r2dbcpg.message;

import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/*
@Data
@Table("messages")
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(onConstructor_ = @PersistenceConstructor)
public class Message {

    @Id
    private UUID id;
    private @NonNull String sender;
    private String recipient;
    private @NonNull String body;
}
*/

@With
@Table("messages")
@Value(staticConstructor = "ofAll")
// @AllArgsConstructor(onConstructor_ = @PersistenceConstructor)
public class Message {

    @Id
    private final UUID id;
    private final String sender;
    private final String recipient;
    private final String body;

    public static Message of(String sender, String body) {
        return Message.ofAll(null, sender, null, body);
    }
}
