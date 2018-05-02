import io.github.jython234.matrix.appservice.MatrixAppservice;
import io.github.jython234.matrix.appservice.Util;
import io.github.jython234.matrix.appservice.event.EventType;
import io.github.jython234.matrix.appservice.event.MatrixEvent;
import io.github.jython234.matrix.appservice.event.TypingMatrixEvent;
import io.github.jython234.matrix.appservice.network.RESTController;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
        var event = new TypingMatrixEvent();
        event.userIds = new ArrayList<>();
        event.userIds.add("@fakeuser:fakeserver.net");

        var transactionObj = new JSONObject();
        var transactions = new JSONArray();
        transactions.put(event.encode());
        transactionObj.put("events", transactions);

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
