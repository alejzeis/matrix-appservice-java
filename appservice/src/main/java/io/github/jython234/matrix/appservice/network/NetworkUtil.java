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

import java.net.URI;
import java.time.Duration;

class NetworkUtil {
    private static Gson gson = new Gson();

    public static String createRoom(CreateRoomRequest roomOptions) throws Exception {
        var uri = new URI(MatrixAppservice.getInstance().getServerURL() + "/_matrix/client/r0/createRoom?access_token=" + MatrixAppservice.getInstance().getRegistration().getAsToken());

        var json = gson.toJson(roomOptions);

        var request = HttpRequest.newBuilder().uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublisher.fromString(json)).timeout(Duration.ofSeconds(20)).build();

        var response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandler.asString());
        var responseJSON = gson.fromJson(response.body(), CreateRoomResponse.class);

        return responseJSON.roomId;
    }
}
