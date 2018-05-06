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
package io.github.jython234.matrix.appservice.event.message;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class MessageContentDeserializer implements JsonDeserializer<MessageContent> {

    @Override
    public MessageContent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        var object = jsonElement.getAsJsonObject();
        var msgtype = object.get("msgtype").getAsString();

        switch (msgtype) {
            case "m.text": {
                var content = new MessageContent.TextMessageContent();
                content.body = object.get("body").getAsString();

                return content;
            }
            case "m.notice": {
                var content = new MessageContent.NoticeMessageContent();
                content.body = object.get("body").getAsString();

                return content;
            }
            case "m.emote": {
                var content = new MessageContent.EmoteMessageContent();
                content.body = object.get("body").getAsString();

                return content;
            }
            case "m.image": {
                var content = new MessageContent.ImageMessageContent();
                content.body = object.get("body").getAsString();
                content.url = object.get("url").getAsString();

                if (object.has("info")) {
                    var infoObj = object.get("info").getAsJsonObject();
                    content.info = jsonDeserializationContext.deserialize(infoObj, MessageContent.ImageMessageContent.Info.class);
                }

                return content;
            }
            case "m.file": {
                var content = new MessageContent.FileMessageContent();
                content.body = object.get("body").getAsString();
                content.url = object.get("url").getAsString();
                content.filename = object.get("filename").getAsString();

                if (object.has("info")) {
                    var infoObj = object.get("info").getAsJsonObject();
                    content.info = jsonDeserializationContext.deserialize(infoObj, MessageContent.FileMessageContent.Info.class);
                }

                return content;
            }
            case "m.video": {
                var content = new MessageContent.VideoMessageContent();
                content.body = object.get("body").getAsString();
                content.url = object.get("url").getAsString();

                if (object.has("info")) {
                    var infoObj = object.get("info").getAsJsonObject();
                    content.info = jsonDeserializationContext.deserialize(infoObj, MessageContent.VideoMessageContent.Info.class);
                }

                return content;
            }
            case "m.audio": {
                var content = new MessageContent.AudioMessageContent();
                content.body = object.get("body").getAsString();
                content.url = object.get("url").getAsString();

                if (object.has("info")) {
                    var infoObj = object.get("info").getAsJsonObject();
                    content.info = jsonDeserializationContext.deserialize(infoObj, MessageContent.AudioMessageContent.Info.class);
                }

                return content;
            }
            case "m.location": {
                var content = new MessageContent.LocationMessageContent();
                content.body = object.get("body").getAsString();
                content.geoUri = object.get("geo_uri").getAsString();

                if (object.has("info")) {
                    var infoObj = object.get("info").getAsJsonObject();
                    content.info = jsonDeserializationContext.deserialize(infoObj, MessageContent.LocationMessageContent.Info.class);
                }

                return content;
            }
            default:
                throw new JsonParseException("Invalid/Unknown \"msgtype\" in JSON!");
        }
    }
}
