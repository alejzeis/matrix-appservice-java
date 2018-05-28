/*
 * Copyright © 2018, jython234
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package io.github.jython234.matrix.appservice.tests.event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.jython234.matrix.appservice.event.room.message.MessageContent;
import io.github.jython234.matrix.appservice.event.room.message.MessageContentDeserializer;
import io.github.jython234.matrix.appservice.event.room.message.MessageContentSerializer;
import io.github.jython234.matrix.appservice.event.room.message.MessageMatrixEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageEventTest {
    private static Gson gson;

    @BeforeAll
    static void init() {
        gson = new GsonBuilder()
                .registerTypeAdapter(MessageContent.class, new MessageContentDeserializer())
                .registerTypeAdapter(MessageContent.class, new MessageContentSerializer())
                .create();
    }

    @Test
    void decodeTest() {
        testDecode("{\n" +
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
                "    \"event_id\": \"" + Constants.id + "\",\n" +
                "    \"origin_server_ts\": 1432735824653,\n" +
                "    \"room_id\": \"" + Constants.roomId + "\",\n" +
                "    \"sender\": \"" + Constants.sender + "\",\n" +
                "    \"type\": \"m.room.message\"\n" +
                "}");
    }

    @Test
    void encodeTest() {
        var event = new MessageMatrixEvent();
        Constants.setRoomEventDefaults(event);

        var content = new MessageContent.VideoMessageContent();
        content.body = "A Video";
        content.url = "mxc://localhost/d230dASDFFxqpbQYZym562";

        content.info = new MessageContent.VideoMessageContent.Info();
        content.info.width = 480;
        content.info.height = 240;
        content.info.duration = 12341234;
        content.info.mimetype = "video/mp4";
        content.info.size = 234234;
        content.info.thumbnailUrl = "mxc://localhost/vvhOpdygXyonDWhikuxZjjhx";
        content.info.thumbnailInfo = new MessageContent.ThumbnailInfo();
        content.info.thumbnailInfo.mimetype = "image/jpeg";
        content.info.thumbnailInfo.height = 300;
        content.info.thumbnailInfo.width = 240;
        content.info.thumbnailInfo.size = 123124;

        event.content = content;

        testDecode(gson.toJson(event));
    }

    private void testDecode(String event) {
        MessageMatrixEvent eventMatrix = gson.fromJson(event, MessageMatrixEvent.class);

        assertEquals(MessageMatrixEvent.TYPE, eventMatrix.getType());
        Constants.checkRoomEventDefaults(eventMatrix);

        assertNotNull(eventMatrix.content);
        assertTrue(eventMatrix.content instanceof MessageContent.VideoMessageContent);
        assertEquals(MessageContent.VideoMessageContent.TYPE, eventMatrix.content.msgtype);

        var content = (MessageContent.VideoMessageContent) eventMatrix.content;

        assertEquals("A Video", content.body);
        assertEquals("mxc://localhost/d230dASDFFxqpbQYZym562", content.url);

        assertNotNull(content.info);
        assertEquals(12341234, content.info.duration);
        assertEquals(240, content.info.height);
        assertEquals(480, content.info.width);
        assertEquals("video/mp4", content.info.mimetype);
        assertEquals(234234, content.info.size);
        assertEquals("mxc://localhost/vvhOpdygXyonDWhikuxZjjhx", content.info.thumbnailUrl);

        assertNotNull(content.info.thumbnailInfo);
        assertEquals(300, content.info.thumbnailInfo.height);
        assertEquals(240, content.info.thumbnailInfo.width);
        assertEquals("image/jpeg", content.info.thumbnailInfo.mimetype);
        assertEquals(123124, content.info.thumbnailInfo.size);
    }
}
