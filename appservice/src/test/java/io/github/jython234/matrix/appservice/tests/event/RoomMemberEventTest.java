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
import io.github.jython234.matrix.appservice.event.RoomMemberMatrixEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoomMemberEventTest {
    private static Gson gson;

    @BeforeAll
    static void init() {
        gson = new Gson();
    }

    @Test
    void testEvent() {
        String event = "{\n" +
                "  \"origin_server_ts\": 1526072940657,\n" +
                "  \"sender\": \"@fakeuser:fakeserver.net\",\n" +
                "  \"event_id\": \"$152607294010wXXPo:fakeserver.net\",\n" +
                "  \"unsigned\": {\n" +
                "    \"age\": 59\n" +
                "  },\n" +
                "  \"state_key\": \"@inviteduser:fakeserver.net\",\n" +
                "  \"content\": {\n" +
                "    \"membership\": \"invite\"\n" +
                "  },\n" +
                "  \"membership\": \"invite\",\n" +
                "  \"type\": \"m.room.member\",\n" +
                "  \"room_id\": \"!SABnCBIIqUARlcXYsy:localmatrix\"\n" +
                "}";

        // Test decode

        RoomMemberMatrixEvent eventMatrix = gson.fromJson(event, RoomMemberMatrixEvent.class);

        assertEquals(RoomMemberMatrixEvent.TYPE, eventMatrix.getType());
        assertEquals("!SABnCBIIqUARlcXYsy:localmatrix", eventMatrix.roomId);
        assertEquals("@fakeuser:fakeserver.net", eventMatrix.sender);
        assertEquals("$152607294010wXXPo:fakeserver.net", eventMatrix.id);
        assertEquals("@inviteduser:fakeserver.net", eventMatrix.stateKey);

        assertNotNull(eventMatrix.content);
        assertEquals(RoomMemberMatrixEvent.Content.Membership.INVITE, eventMatrix.content.membership);

        // Test encode

        eventMatrix.content.avatarURL = "mxc://fakeURL/qwerty";
        eventMatrix.content.displayname = "Invited User";
        eventMatrix.content.isDirect = true;


        assertEquals("{\"state_key\":\"@inviteduser:fakeserver.net\",\"content\":{\"avatarURL\":\"mxc://fakeURL/qwerty\",\"displayname\":\"Invited User\",\"membership\":\"invite\",\"isDirect\":true},\"room_id\":\"!SABnCBIIqUARlcXYsy:localmatrix\",\"sender\":\"@fakeuser:fakeserver.net\",\"event_id\":\"$152607294010wXXPo:fakeserver.net\",\"type\":\"m.room.member\"}",
                gson.toJson(eventMatrix));
    }
}