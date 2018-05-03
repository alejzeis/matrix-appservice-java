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
import com.google.gson.Gson;
import io.github.jython234.matrix.appservice.MatrixAppservice;
import io.github.jython234.matrix.appservice.Util;
import io.github.jython234.matrix.appservice.event.EventHandler;
import io.github.jython234.matrix.appservice.event.MatrixEvent;
import io.github.jython234.matrix.appservice.event.TypingMatrixEvent;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MatrixAppservice.class)
@AutoConfigureMockMvc
public class EventProcessTest {
    private static File registrationFile;

    @Autowired private MockMvc mockMvc;


    @BeforeAll
    static void init() throws IOException {
        registrationFile = new File("tmp/registration.yml");
        Util.copyResourceTo("registration/testRegistration.yml", registrationFile);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testTypingEvent() throws Exception {
        // Construct Event
        var event = new TypingMatrixEvent();
        event.roomId = "!fakeroom:fakeserver.net";
        event.content = new TypingMatrixEvent.Content();
        event.content.userIds = new String[]{"@fakeuser:fakeserver.net"};

        MatrixAppservice.getInstance().setEventHandler(new EventHandler() {
            @Override
            public void onMatrixEvent(MatrixEvent firedEvent) {
                LoggerFactory.getLogger("EventProcessTest").info("Got Event: " + firedEvent);

                assertTrue(firedEvent instanceof TypingMatrixEvent);
                assertEquals(TypingMatrixEvent.TYPE, firedEvent.getType());

                var typingEvent = (TypingMatrixEvent) firedEvent;
                assertEquals(event.roomId, typingEvent.roomId);

                assertNotNull(typingEvent.content);
                assertNotNull(typingEvent.content.userIds);

                assertEquals(event.content.userIds.length, typingEvent.content.userIds.length);
                assertEquals(event.content.userIds[0], typingEvent.content.userIds[0]);
            }

            @Override
            public boolean onRoomAliasQueried(String alias) {
                return false;
            }

            @Override
            public void onRoomAliasCreated(String alias) {

            }
        });

        // Serialize everything into JSON to be sent
        var gson = new Gson();
        var transactionObj = new JSONObject();
        var transactions = new JSONArray();
        transactions.put(gson.toJson(event));
        transactionObj.put("events", transactions);

        // Perform the REST request
        this.mockMvc.perform(
                put("/transactions/1?access_token=" + MatrixAppservice.getInstance().getRegistration().getHsToken())
                        .contentType("application/json")
                        .content(transactionObj.toJSONString())
        ).andExpect(status().isOk());
    }

    @AfterAll
    static void cleanup() {
        assertTrue(registrationFile.delete());
    }
}
