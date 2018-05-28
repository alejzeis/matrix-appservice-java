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
import io.github.jython234.matrix.appservice.event.room.RoomMemberMatrixEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoomMemberEventTest {
    private static Gson gson;

    @BeforeAll
    static void init() {
        gson = new Gson();
    }

    @Test
    void encodeTest() {
        RoomMemberMatrixEvent event = new RoomMemberMatrixEvent();

        Constants.setRoomEventDefaults(event);
        event.stateKey = "@inviteduser:fakeserver.net";

        event.content = new RoomMemberMatrixEvent.Content();
        event.content.membership = RoomMemberMatrixEvent.Content.Membership.INVITE;
        event.content.avatarURL = "mxc://fakeURL/qwerty";
        event.content.displayname = "Invited User";
        event.content.isDirect = true;

        testDecode(gson.toJson(event));
    }

    @Test
    void decodeTest() {
        testDecode("{\n" +
                "  \"origin_server_ts\": 1526072940657,\n" +
                "  \"sender\": \"" + Constants.sender + "\",\n" +
                "  \"event_id\": \"" + Constants.id + "\",\n" +
                "  \"unsigned\": {\n" +
                "    \"age\": 59\n" +
                "  },\n" +
                "  \"state_key\": \"@inviteduser:fakeserver.net\",\n" +
                "  \"content\": {\n" +
                "    \"membership\": \"invite\",\n" +
                "    \"avatarURL\": \"mxc://fakeURL/qwerty\",\n" +
                "    \"displayname\": \"Invited User\",\n" +
                "    \"is_direct\": true\n" +
                "  },\n" +
                "  \"membership\": \"invite\",\n" +
                "  \"type\": \"m.room.member\",\n" +
                "  \"room_id\": \"" + Constants.roomId + "\"\n" +
                "}");
    }

    private void testDecode(String event) {
        RoomMemberMatrixEvent eventMatrix = gson.fromJson(event, RoomMemberMatrixEvent.class);

        assertEquals(RoomMemberMatrixEvent.TYPE, eventMatrix.getType());

        Constants.checkRoomEventDefaults(eventMatrix);
        assertEquals("@inviteduser:fakeserver.net", eventMatrix.stateKey);

        assertNotNull(eventMatrix.content);
        assertEquals(RoomMemberMatrixEvent.Content.Membership.INVITE, eventMatrix.content.membership);
        assertEquals("mxc://fakeURL/qwerty", eventMatrix.content.avatarURL);
        assertEquals("Invited User", eventMatrix.content.displayname);
        assertTrue(eventMatrix.content.isDirect);
    }
}
