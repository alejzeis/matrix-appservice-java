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
package io.github.jython234.matrix.appservice.network;

import com.google.gson.Gson;
import io.github.jython234.matrix.appservice.MatrixAppservice;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.Duration;

class NetworkUtil {
    private static Gson gson = new Gson();

    static String createRoom(CreateRoomRequest roomOptions) throws Exception {
        var uri = new URI(MatrixAppservice.getInstance().getServerURL() + "/_matrix/client/r0/createRoom?access_token=" + MatrixAppservice.getInstance().getRegistration().getAsToken());

        var json = gson.toJson(roomOptions);

        var request = HttpRequest.newBuilder().uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublisher.fromString(json)).timeout(Duration.ofSeconds(20)).build();

        var response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandler.asString());
        if(response.statusCode() != HttpStatus.OK.value())
            throw new RuntimeException("Received non-200 status code while attempting to create room.");

        var responseJSON = gson.fromJson(response.body(), CreateRoomResponse.class);

        return responseJSON.roomId;
    }

    static String createUser(CreateUserRequest userOptions) throws Exception {
        var uri = new URI(MatrixAppservice.getInstance().getServerURL() + "/_matrix/client/r0/register?access_token=" + MatrixAppservice.getInstance().getRegistration().getAsToken() + "&kind=user");

        var json = gson.toJson(new CreateUserRequest.CreateUserRequestJSON(userOptions.localpart));

        var request = HttpRequest.newBuilder().uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublisher.fromString(json)).timeout(Duration.ofSeconds(20)).build();

        var response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandler.asString());
        if(response.statusCode() != HttpStatus.OK.value())
            throw new RuntimeException("Received non-200 status code while attempting to provision user.");

        var responseJSON = gson.fromJson(response.body(), CreateUserRequest.CreateUserResponseJSON.class);

        MatrixAppservice.getInstance().threadPoolTaskExecutor.submit(() -> {
           // Finish provisioning user
            try {
                setUserDisplayName(responseJSON.userId, userOptions.displayname);
            } catch (Exception e) {
                MatrixAppservice.getInstance().logger.error("Exception while attempting to set user displayname: " + e.getClass().getName() + " " + e.getMessage());
                e.printStackTrace(System.err);
                return;
            }

            if(userOptions.avatarUrl != null) {
                try {
                    setUserAvatarURL(responseJSON.userId, userOptions.avatarUrl);
                } catch (Exception e) {
                    MatrixAppservice.getInstance().logger.error("Exception while attempting to set user avatar: " + e.getClass().getName() + " " + e.getMessage());
                    e.printStackTrace(System.err);
                    return;
                }
            }

            // Run event handler method
            MatrixAppservice.getInstance().getEventHandler().onUserProvisioned(userOptions.localpart);
        });

        return responseJSON.userId;
    }

    @SuppressWarnings("unchecked")
    private static void setUserDisplayName(String userId, String displayname) throws Exception {
        var uri = new URI(MatrixAppservice.getInstance().getServerURL() + "/_matrix/client/r0/profile/" + userId + "/displayname?access_token=" + MatrixAppservice.getInstance().getRegistration().getAsToken());

        JSONObject root =  new JSONObject();
        root.put("displayname", displayname);

        var request = HttpRequest.newBuilder().uri(uri)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublisher.fromString(root.toJSONString())).timeout(Duration.ofSeconds(20)).build();

        var response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandler.asString());
        if(response.statusCode() != HttpStatus.OK.value()) {
            throw new RuntimeException("Received non-200 status code while attempting to set user displayname: " + response.body());
        }
    }

    @SuppressWarnings("unchecked")
    private static void setUserAvatarURL(String userId, String avatarURL) throws Exception {
        var uri = new URI(MatrixAppservice.getInstance().getServerURL() + "/_matrix/client/r0/profile/" + userId + "/avatar_url?access_token=" + MatrixAppservice.getInstance().getRegistration().getAsToken());

        JSONObject root =  new JSONObject();
        root.put("avatar_url", avatarURL);

        var request = HttpRequest.newBuilder().uri(uri)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublisher.fromString(root.toJSONString())).timeout(Duration.ofSeconds(20)).build();

        var response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandler.asString());
        if(response.statusCode() != HttpStatus.OK.value()) {
            throw new RuntimeException("Received non-200 status code while attempting to set user avatar URL: " + response.body());
        }
    }
}
