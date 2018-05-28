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
import io.github.jython234.matrix.appservice.event.room.RedactionMatrixEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        event.roomId = "!SABnCBIIqUARlcXYsy:fakeserver.net";
        event.id = "$15269544431jHMeV:fakeserver.net";
        event.redactedEvent = "$15269544390zubGQ:fakserver.net";

        event.content = new RedactionMatrixEvent.Content();
        event.content.reason = "No reason.";

        testDecode(gson.toJson(event));
    }

    @Test
    void decodeTest() {
        testDecode("{\n" +
                "      \"origin_server_ts\": 1526954443397,\n" +
                "      \"sender\": \"@redactinguser:fakeserver.net\",\n" +
                "      \"event_id\": \"$15269544431jHMeV:fakeserver.net\",\n" +
                "      \"unsigned\": {\n" +
                "        \"age\": 96\n" +
                "      },\n" +
                "      \"content\": {\"reason\":\"No reason.\"},\n" +
                "      \"redacts\": \"$15269544390zubGQ:fakserver.net\",\n" +
                "      \"type\": \"m.room.redaction\",\n" +
                "      \"room_id\": \"!SABnCBIIqUARlcXYsy:fakeserver.net\"\n" +
                "}");
    }

    private void testDecode(String event) {
        RedactionMatrixEvent eventMatrix = gson.fromJson(event, RedactionMatrixEvent.class);

        assertEquals(RedactionMatrixEvent.TYPE, eventMatrix.getType());
        assertEquals("@redactinguser:fakeserver.net", eventMatrix.sender);
        assertEquals("$15269544431jHMeV:fakeserver.net", eventMatrix.id);
        assertEquals("$15269544390zubGQ:fakserver.net", eventMatrix.redactedEvent);
        assertEquals("!SABnCBIIqUARlcXYsy:fakeserver.net", eventMatrix.roomId);

        assertNotNull(eventMatrix.content);
        assertEquals("No reason.", eventMatrix.content.reason);
    }
}
