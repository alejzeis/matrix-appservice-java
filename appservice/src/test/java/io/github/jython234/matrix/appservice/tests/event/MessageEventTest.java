package io.github.jython234.matrix.appservice.tests.event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.jython234.matrix.appservice.event.message.MessageContent;
import io.github.jython234.matrix.appservice.event.message.MessageContentDeserializer;
import io.github.jython234.matrix.appservice.event.message.MessageMatrixEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageEventTest {
    private static Gson gson;

    @BeforeAll
    static void init() {
        gson = new GsonBuilder().registerTypeAdapter(MessageContent.class, new MessageContentDeserializer()).create();
    }

    @Test
    void decodeTest() {
        String event = "{\n" +
                "    \"age\": 50,\n" +
                "    \"content\": {\n" +
                "        \"body\": \"A Video\",\n" +
                "        \"info\": {\n" +
                "            \"duration\": 12341234,\n" +
                "            \"h\": 240,\n" +
                "            \"mimetype\": \"video/mp4\",\n" +
                "            \"size\": 234234,\n" +
                "            \"thumbnail_info\": {\n" +
                "                \"h\": 300,\n" +
                "                \"mimetype\": \"image/jpeg\",\n" +
                "                \"size\": 123124,\n" +
                "                \"w\": 240\n" +
                "            },\n" +
                "            \"thumbnail_url\": \"mxc://localhost/vvhOpdygXyonDWhikuxZjjhx\",\n" +
                "            \"w\": 480\n" +
                "        },\n" +
                "        \"msgtype\": \"m.video\",\n" +
                "        \"url\": \"mxc://localhost/d230dASDFFxqpbQYZym562\"\n" +
                "    },\n" +
                "    \"event_id\": \"$143273582443PhrSn:fakeserver.net\",\n" +
                "    \"origin_server_ts\": 1432735824653,\n" +
                "    \"room_id\": \"!bdcFmnylWSoSUllTqx:fakeserver.net\",\n" +
                "    \"sender\": \"@fakeuser:fakeserver.net\",\n" +
                "    \"type\": \"m.room.message\"\n" +
                "}";

        MessageMatrixEvent eventMatrix = gson.fromJson(event, MessageMatrixEvent.class);

        assertEquals("!bdcFmnylWSoSUllTqx:fakeserver.net", eventMatrix.roomId);
        assertEquals("@fakeuser:fakeserver.net", eventMatrix.sender);
        assertEquals("$143273582443PhrSn:fakeserver.net", eventMatrix.eventId);

        assertNotNull(eventMatrix.content);
        assertTrue(eventMatrix.content instanceof MessageContent.VideoMessageContent);
        assertEquals("A Video", eventMatrix.content.body);

    }
}
