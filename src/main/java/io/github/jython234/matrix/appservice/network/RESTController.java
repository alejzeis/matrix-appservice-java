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
import io.github.jython234.matrix.appservice.event.MatrixEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * The main Spring Controller that handles
 * all incoming REST requests to the appservice
 * from the homeserver.
 *
 * @author jython234
 */
@RestController
public class RESTController {
    private Gson gson = new Gson();
    private JSONParser parser = new JSONParser();

    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.PUT)
    @SuppressWarnings("unchecked")
    public ResponseEntity transactions(@PathVariable(value = "id") long id,
                                       @RequestParam("access_token") String accessToken, @RequestBody String body, HttpServletRequest request) {

        if(!accessToken.equals(MatrixAppservice.getInstance().getRegistration().getHsToken()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"errcode\":\"appservice.M_FORBIDDEN\"}");

        try {
            JSONObject json = (JSONObject) this.parser.parse(body);
            JSONArray transactions = (JSONArray) json.get("events");

            transactions.forEach((transaction) -> MatrixAppservice.getInstance().threadPoolTaskExecutor.submit(() -> {
                var eventWithType = this.gson.fromJson(((JSONObject) transaction).toJSONString(), MatrixEvent.class);

                MatrixAppservice.getInstance().getEventHandler().onMatrixEvent(EventDecoder.decodeEvent((JSONObject) transaction, eventWithType));
            }));
        } catch (ParseException e) {
            MatrixAppservice.getInstance().logger.warn("Malformed JSON from " + request.getRemoteAddr());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"errcode\":\"appservice.M_BAD_JSON\"}");
        }

        return ResponseEntity.ok("{}");
    }

    @RequestMapping(value = "/rooms/{alias}", method = RequestMethod.GET)
    public ResponseEntity rooms(@PathVariable(value = "alias") String alias, @RequestParam("access_token") String accessToken) {

        if(!accessToken.equals(MatrixAppservice.getInstance().getRegistration().getHsToken()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"errcode\":\"appservice.M_FORBIDDEN\"}");

        try {
            var request = MatrixAppservice.getInstance().getEventHandler().onRoomAliasQueried(alias);
            if(request != null) {
                // We need to create the room
                String id = NetworkUtil.createRoom(request);

                MatrixAppservice.getInstance().logger.info("Created room: " + id);
                MatrixAppservice.getInstance().threadPoolTaskExecutor.submit(() -> MatrixAppservice.getInstance().getEventHandler().onRoomAliasCreated(alias, id));

                return ResponseEntity.ok("{}");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"errcode\":\"appservice.M_NOT_FOUND\"}");
            }
        } catch (Exception e) {
            MatrixAppservice.getInstance().logger.warn("While processing room alias: " + e.getClass().getName() + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"errcode\":\"appservice.M_UNKNOWN\", \"error\": \"" + e.getClass().getName() + ": " + e.getMessage() + "\"}");
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity users(@PathVariable(value = "id") String id, @RequestParam("access_token") String accessToken) {

        if(!accessToken.equals(MatrixAppservice.getInstance().getRegistration().getHsToken()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"errcode\":\"appservice.M_FORBIDDEN\"}");

        try {
            var request = MatrixAppservice.getInstance().getEventHandler().onUserAliasQueried(id);
            if(request != null) {
                String userId = NetworkUtil.createUser(request);

                MatrixAppservice.getInstance().logger.info("Provisioned user (stage 1): " + userId);

                return ResponseEntity.ok("{}");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"errcode\":\"appservice.M_NOT_FOUND\"}");
            }
        } catch (Exception e) {
            MatrixAppservice.getInstance().logger.warn("While processing user alias: " + e.getClass().getName() + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"errcode\":\"appservice.M_UNKNOWN\", \"error\": \"" + e.getClass().getName() + ": " + e.getMessage() + "\"}");
        }
    }
}
