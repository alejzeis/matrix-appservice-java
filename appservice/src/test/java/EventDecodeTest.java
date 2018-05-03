import com.google.gson.Gson;
import io.github.jython234.matrix.appservice.event.TypingMatrixEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventDecodeTest {
    private static Gson gson;

    @BeforeAll
    static void init() {
        gson = new Gson();
    }

    @Test
    void typingEvent() {
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
