package io.github.jython234.matrix.appservice.tests.event;

import com.google.gson.Gson;
import io.github.jython234.matrix.appservice.event.RedactionMatrixEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class RedactionMatrixEventTest {
    private static Gson gson;

    @BeforeAll
    static void init() {
        gson = new Gson();
    }

    @Test
    void encodeTest() {
        RedactionMatrixEvent event = new RedactionMatrixEvent();
        event.sender = "@redactinguser:fakeserver.net";
        event.roomId = "!fakeroom:fakeserver.net";
        event.eventId = "$ekq45QxM5qpEkfj9:fakeserver.net";
        event.redactedEvent = "$d24g6R7wRf652AxN:fakeserver.net";

        event.content = new RedactionMatrixEvent.Content();
        event.content.reason = "No reason.";


        assertEquals("{\"room_id\":\"!fakeroom:fakeserver.net\",\"sender\":\"@redactinguser:fakeserver.net\",\"event_id\":\"$ekq45QxM5qpEkfj9:fakeserver.net\",\"redacts\":\"$d24g6R7wRf652AxN:fakeserver.net\",\"content\":{\"reason\":\"No reason.\"},\"type\":\"m.room.redaction\"}",
                gson.toJson(event));
    }

    @Test
    void decodeTest() {
        String event = "{\n" +
                "      \"origin_server_ts\": 1526954443397,\n" +
                "      \"sender\": \"@redactinguser:fakeserver.net\",\n" +
                "      \"event_id\": \"$15269544431jHMeV:fakeserver.net\",\n" +
                "      \"unsigned\": {\n" +
                "        \"age\": 96\n" +
                "      },\n" +
                "      \"content\": {},\n" +
                "      \"redacts\": \"$15269544390zubGQ:fakserver.net\",\n" +
                "      \"type\": \"m.room.redaction\",\n" +
                "      \"room_id\": \"!SABnCBIIqUARlcXYsy:fakeserver.net\"\n" +
                "}";

        RedactionMatrixEvent eventMatrix = gson.fromJson(event, RedactionMatrixEvent.class);

        assertEquals(RedactionMatrixEvent.TYPE, eventMatrix.getType());
        assertEquals("@redactinguser:fakeserver.net", eventMatrix.sender);
        assertEquals("$15269544431jHMeV:fakeserver.net", eventMatrix.eventId);
        assertEquals("$15269544390zubGQ:fakserver.net", eventMatrix.redactedEvent);
        assertEquals("!SABnCBIIqUARlcXYsy:fakeserver.net", eventMatrix.roomId);

        assertNotNull(eventMatrix.content);
        assertNull(eventMatrix.content.reason);
    }
}
