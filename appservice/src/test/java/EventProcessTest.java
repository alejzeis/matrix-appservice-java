import com.google.gson.Gson;
import io.github.jython234.matrix.appservice.MatrixAppservice;
import io.github.jython234.matrix.appservice.Util;
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

        // Setup our event handler to handle the event when we get it
        MatrixAppservice.getInstance().setEventHandler(firedEvent -> {
            LoggerFactory.getLogger("EventProcessTest").info("Got Event: " + firedEvent);

            assertTrue(firedEvent instanceof TypingMatrixEvent);
            assertEquals(TypingMatrixEvent.TYPE, firedEvent.getType());

            var typingEvent = (TypingMatrixEvent) firedEvent;
            assertEquals(event.roomId, typingEvent.roomId);

            assertNotNull(typingEvent.content);
            assertNotNull(typingEvent.content.userIds);

            assertEquals(event.content.userIds.length, typingEvent.content.userIds.length);
            assertEquals(event.content.userIds[0], typingEvent.content.userIds[0]);
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
