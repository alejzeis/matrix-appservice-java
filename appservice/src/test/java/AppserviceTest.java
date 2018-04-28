import io.github.jython234.matrix.appservice.MatrixAppservice;
import io.github.jython234.matrix.appservice.Util;
import io.github.jython234.matrix.appservice.network.RESTController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MatrixAppservice.class)
@AutoConfigureMockMvc
class AppserviceTest {

    private static File registrationFile;

    @Autowired private RESTController controller;
    @Autowired private MockMvc mockMvc;


    @BeforeAll
    static void init() throws IOException {
        registrationFile = new File("tmp/registration.yml");
        Util.copyResourceTo("registration/testRegistration.yml", registrationFile);
    }

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

    @Test
    void testNoToken() throws Exception {
        this.mockMvc.perform(put("/transactions/23123")).andExpect(status().isBadRequest());
        this.mockMvc.perform(get("/rooms/afakeroom")).andExpect(status().isBadRequest());
        this.mockMvc.perform(get("/users/afakeuser")).andExpect(status().isBadRequest());
    }

    @Test
    void testBadToken() throws Exception {
        this.mockMvc.perform(
                put("/transactions/123123123?access_token=badtoken")
                .contentType("text/plain") // TODO: Provide JSON
                .content("placeholdertext")
        ).andExpect(status().isForbidden());
        this.mockMvc.perform(get("/rooms/afakeroom?access_token=badtoken")).andExpect(status().isForbidden());
        this.mockMvc.perform(get("/users/afakeuser?access_token=badtoken")).andExpect(status().isForbidden());
    }

    @AfterAll
    static void cleanup() {
        assertTrue(registrationFile.delete());
    }
}
