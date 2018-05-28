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
package io.github.jython234.matrix.appservice.registration;

import io.github.jython234.matrix.appservice.exception.KeyNotFoundException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegistrationLoader {
    /**
     * Load an appservice registration from the given YAML file's location.
     *
     * @param location The location of the YAML registration file.
     * @return A loaded Registration instance with all the fields filled in.
     * @throws FileNotFoundException When the YAML file is not found.
     * @throws KeyNotFoundException When the YAML file is missing a key that is required.
     */
    public static Registration loadRegistrationFromFile(String location) throws KeyNotFoundException, FileNotFoundException {
        return loadRegistrationFromFile(new File(location));
    }

    /**
     * Load an appservice registration from the given YAML File object.
     *
     * @param file The File object that represents the YAML file.
     * @return a loaded Registration instance with all the fields filled in.
     * @throws FileNotFoundException When the YAML file is not found.
     * @throws KeyNotFoundException When the YAML file is missing a key that is required.
     */
    @SuppressWarnings("unchecked")
    public static Registration loadRegistrationFromFile(File file) throws KeyNotFoundException, FileNotFoundException {
        var yaml = new Yaml();
        var reg = new Registration();
        reg.namespaces = new Registration.Namespaces();

        Map map = yaml.load(new FileInputStream(file));

        reg.id = (String) map.get("id");
        reg.url = (String) map.get("url");
        reg.asToken = (String) map.get("as_token");
        reg.hsToken = (String) map.get("hs_token");
        reg.senderLocalpart = (String) map.get("sender_localpart");
        var namespacesMap = (Map) map.get("namespaces");

        if(reg.id == null || reg.url == null || reg.asToken == null || reg.hsToken == null || reg.senderLocalpart == null
                || namespacesMap == null) {
            throw new KeyNotFoundException("Failed to find all required keys in YAML file!");
        }

        var list = ((List<String>) namespacesMap.get("rooms"));
        reg.namespaces.rooms = list.toArray(new String[0]);
        reg.namespaces.aliases = new ArrayList<>();
        reg.namespaces.users = new ArrayList<>();

        var users = (List<Map>) namespacesMap.get("users");
        var aliases = (List<Map>) namespacesMap.get("aliases");

        users.forEach((userNamespace) -> {
            var userSpace = new Registration.UserNamespace();
            userSpace.exclusive = (boolean) userNamespace.get("exclusive");
            userSpace.regex = (String) userNamespace.get("regex");

            reg.namespaces.users.add(userSpace);
        });

        aliases.forEach((aliasNamespace) -> {
            var aliasSpace = new Registration.AliasNamespace();
            aliasSpace.exclusive = (boolean) aliasNamespace.get("exclusive");
            aliasSpace.regex = (String) aliasNamespace.get("regex");

            reg.namespaces.aliases.add(aliasSpace);
        });

        return reg;
    }
}
