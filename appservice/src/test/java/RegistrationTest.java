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
import io.github.jython234.matrix.appservice.exception.KeyNotFoundException;
import io.github.jython234.matrix.appservice.registration.RegistrationLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationTest {

    private static ResourceLoader loader;

    @BeforeAll
    static void init() {
        loader = new DefaultResourceLoader();
    }

    @Test
    @DisplayName("Load registration YAML test")
    void runLoadTest() throws IOException, KeyNotFoundException {
        try {
            var reg = RegistrationLoader.loadRegistrationFromFile(loader.getResource("registration/testRegistration.yml").getFile());

            assertEquals(reg.getId(), "bigfakeid");
            assertEquals(reg.getUrl(), "http://localhost");
            assertEquals(reg.getAsToken(), "secretappservicetoken");
            assertEquals(reg.getHsToken(), "secrethomeservertoken");
            assertEquals(reg.getSenderLocalpart(), "@myappservice:localhost");

            assertNotNull(reg.getNamespaces());

            assertEquals(reg.getNamespaces().rooms.length, 1);
            assertEquals(reg.getNamespaces().rooms[0], "!aksjdflknwerqwer:localhost");

            assertEquals(reg.getNamespaces().aliases.size(), 1);
            assertFalse(reg.getNamespaces().aliases.get(0).exclusive);
            assertEquals(reg.getNamespaces().aliases.get(0).regex, "#!as_.*");

            assertEquals(reg.getNamespaces().users.size(), 1);
            assertTrue(reg.getNamespaces().users.get(0).exclusive);
            assertEquals(reg.getNamespaces().users.get(0).regex, "@!as_.*");
        } catch (KeyNotFoundException | IOException e) {
            throw e;
        }
    }

    @Test
    @DisplayName("Load registration YAML with missing keys test")
    void runMissingKeysTest() {
        assertThrows(KeyNotFoundException.class, () -> RegistrationLoader.loadRegistrationFromFile(loader.getResource("registration/testRegistration-missing-keys.yml").getFile()));
    }

    @Test
    @DisplayName("Load missing registration YAML test")
    void runMissingFileTest() {
        assertThrows(FileNotFoundException.class, () -> RegistrationLoader.loadRegistrationFromFile("a-non-existent-file.yml"));
    }
}
