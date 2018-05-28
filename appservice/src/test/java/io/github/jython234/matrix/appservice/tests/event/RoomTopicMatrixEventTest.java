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
import io.github.jython234.matrix.appservice.event.room.RoomTopicMatrixEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoomTopicMatrixEventTest {
    private static Gson gson;

    @BeforeAll
    static void init() {
        gson = new Gson();
    }

    @Test
    void encodeTest() {
        RoomTopicMatrixEvent event = new RoomTopicMatrixEvent();
        Constants.setRoomEventDefaults(event);

        event.content = new RoomTopicMatrixEvent.Content();
        event.content.topic = "A topic.";

        testDecode(gson.toJson(event));
    }

    @Test
    void decodeTest() {
        testDecode("{\n" +
                "    \"age\": 231345,\n" +
                "    \"content\": {\n" +
                "        \"topic\": \"A topic.\"\n" +
                "    },\n" +
                "    \"event_id\": \"" + Constants.id + "\",\n" +
                "    \"origin_server_ts\": 1527470226586,\n" +
                "    \"room_id\": \"" + Constants.roomId + "\",\n" +
                "    \"sender\": \"" + Constants.sender + "\",\n" +
                "    \"state_key\": \"\",\n" +
                "    \"type\": \"m.room.topic\"\n" +
                "}");
    }

    private void testDecode(String event) {
        RoomTopicMatrixEvent matrixEvent = gson.fromJson(event, RoomTopicMatrixEvent.class);
        Constants.checkRoomEventDefaults(matrixEvent);

        assertNotNull(matrixEvent.content);
        assertEquals("A topic.", matrixEvent.content.topic);
    }
}
