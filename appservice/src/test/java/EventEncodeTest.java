import com.google.gson.Gson;
import io.github.jython234.matrix.appservice.event.TypingMatrixEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventEncodeTest {
    private static Gson gson;

    @BeforeAll
    static void init() {
        gson = new Gson();
    }

    @Test
    void typingEvent() {
        // Construct Event
        var event = new TypingMatrixEvent();
        event.roomId = "!fakeroom:fakeserver.net";
        event.content = new TypingMatrixEvent.Content();
        event.content.userIds = new String[]{"@fakeuser:fakeserver.net"};

        var compiled = gson.toJson(event);
        var expected = "{\"room_id\":\"!fakeroom:fakeserver.net\",\"content\":{\"user_ids\":[\"@fakeuser:fakeserver.net\"]},\"type\":\"m.typing\"}";

        assertEquals(expected, compiled);
    }
}
