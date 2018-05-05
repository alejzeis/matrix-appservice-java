package io.github.jython234.matrix.appservice.tests;/*
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
import io.github.jython234.matrix.appservice.MatrixAppservice;
import io.github.jython234.matrix.appservice.Util;
import io.github.jython234.matrix.appservice.event.EventHandler;
import io.github.jython234.matrix.appservice.event.MatrixEvent;
import io.github.jython234.matrix.appservice.network.CreateRoomRequest;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                        .contentType("application/json")
                        .content("{\"events\":[]}")
        ).andExpect(status().isForbidden());
        this.mockMvc.perform(get("/rooms/afakeroom?access_token=badtoken")).andExpect(status().isForbidden());
        this.mockMvc.perform(get("/users/afakeuser?access_token=badtoken")).andExpect(status().isForbidden());
    }

    @Test
    void testCorrectToken() throws Exception {
        // Dummy EventHandler
        MatrixAppservice.getInstance().setEventHandler(new EventHandler() {
            @Override
            public void onMatrixEvent(MatrixEvent event) {

            }

            @Override
            public CreateRoomRequest onRoomAliasQueried(String alias) {
                assertEquals("afakeroom", alias);
                return null;
            }

            @Override
            public void onRoomAliasCreated(String alias) {

            }
        });

        this.mockMvc.perform(
                put("/transactions/123123123?access_token=" + MatrixAppservice.getInstance().getRegistration().getHsToken())
                        .contentType("application/json")
                        .content("{\"events\":[]}")
        ).andExpect(status().isOk());
        this.mockMvc.perform(get("/rooms/afakeroom?access_token=" + MatrixAppservice.getInstance().getRegistration().getHsToken())).andExpect(status().isNotFound()).andExpect(content().string("{\"errcode\":\"appservice.M_NOT_FOUND\"}"));
        this.mockMvc.perform(get("/users/afakeuser?access_token=" + MatrixAppservice.getInstance().getRegistration().getHsToken())).andExpect(status().isNotImplemented());
    }

    @AfterAll
    static void cleanup() {
        assertTrue(registrationFile.delete());
    }
}
