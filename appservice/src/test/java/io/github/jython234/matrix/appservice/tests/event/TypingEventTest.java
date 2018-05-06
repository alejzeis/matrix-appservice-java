package io.github.jython234.matrix.appservice.tests.event;

import com.google.gson.Gson;
import io.github.jython234.matrix.appservice.event.TypingMatrixEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TypingEventTest {
    private static Gson gson;

    @BeforeAll
    static void init() {
        gson = new Gson();
    }

    @Test
    void encode() {
        // Construct Event
        var event = new TypingMatrixEvent();
        event.roomId = "!fakeroom:fakeserver.net";
        event.content = new TypingMatrixEvent.Content();
        event.content.userIds = new String[]{"@fakeuser:fakeserver.net"};

        var compiled = gson.toJson(event);
        var expected = "{\"room_id\":\"!fakeroom:fakeserver.net\",\"content\":{\"user_ids\":[\"@fakeuser:fakeserver.net\"]},\"type\":\"m.typing\"}";

        assertEquals(expected, compiled);
    }

    @Test
    void decode() {
        var input = "{\"room_id\":\"!fakeroom:fakeserver.net\",\"content\":{\"user_ids\":[\"@fakeuser:fakeserver.net\"]},\"type\":\"m.typing\"}";

        var event = gson.fromJson(input, TypingMatrixEvent.class);

        assertEquals(TypingMatrixEvent.TYPE, event.getType());
        assertEquals("!fakeroom:fakeserver.net", event.roomId);

        assertNotNull(event.content);
        assertNotNull(event.content.userIds);

        assertEquals(1, event.content.userIds.length);
        assertEquals("@fakeuser:fakeserver.net", event.content.userIds[0]);
    }
}
