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

        if(msgtype.equals("m.text")) {
            var content = new MessageContent.TextMessageContent();
            content.body = object.get("body").getAsString();

            return content;
        } else if(msgtype.equals("m.notice")) {
            var content = new MessageContent.NoticeMessageContent();
            content.body = object.get("body").getAsString();

            return content;
        } else if(msgtype.equals("m.emote")) {
            var content = new MessageContent.EmoteMessageContent();
            content.body = object.get("body").getAsString();

            return content;
        } else if(msgtype.equals("m.image")) {
            var content = new MessageContent.ImageMessageContent();
            content.body = object.get("body").getAsString();
            content.url = object.get("url").getAsString();

            if(object.has("info")) {
                var infoObj = object.get("info").getAsJsonObject();
                content.info = jsonDeserializationContext.deserialize(infoObj, MessageContent.ImageMessageContent.Info.class);
            }

            return content;
        } else if(msgtype.equals("m.file")) {
            var content = new MessageContent.FileMessageContent();
            content.body = object.get("body").getAsString();
            content.url = object.get("url").getAsString();
            content.filename = object.get("filename").getAsString();

            if(object.has("info")) {
                var infoObj = object.get("info").getAsJsonObject();
                content.info = jsonDeserializationContext.deserialize(infoObj, MessageContent.FileMessageContent.Info.class);
            }

            return content;
        } else if(msgtype.equals("m.video")) {
            var content = new MessageContent.VideoMessageContent();
            content.body = object.get("body").getAsString();
            content.url = object.get("url").getAsString();

            if(object.has("info")) {
                var infoObj = object.get("info").getAsJsonObject();
                content.info = jsonDeserializationContext.deserialize(infoObj, MessageContent.VideoMessageContent.Info.class);

                /*content.info = new MessageContent.VideoMessageContent.Info();
                content.info.height = infoObj.get("h").getAsInt();
                content.info.width = infoObj.get("w").getAsInt();
                content.info.mimetype = infoObj.get("mimetype").getAsString();
                content.info.size = infoObj.get("size").getAsInt();
                content.info.duration = infoObj.get("duration").getAsLong();
                content.info.thumbnailUrl = infoObj.get("thumbnail_url").getAsString();

                if(infoObj.has("thumbnail_info")) {
                    var thumbnailObj = infoObj.get("thumbnail_info").getAsJsonObject();
                    content.info.thumbnailInfo = new MessageContent.ThumbnailInfo();
                    content.info.thumbnailInfo.height = thumbnailObj.get("h").getAsInt();
                    content.info.thumbnailInfo.width = thumbnailObj.get("w").getAsInt();
                    content.info.thumbnailInfo.mimetype = thumbnailObj.get("mimetype").getAsString();
                    content.info.thumbnailInfo.size = thumbnailObj.get("size").getAsInt();
                }*/
            }

            return content;
        } else if(msgtype.equals("m.audio")) {
            var content = new MessageContent.AudioMessageContent();
            content.body = object.get("body").getAsString();
            content.url = object.get("url").getAsString();

            if(object.has("info")) {
                var infoObj = object.get("info").getAsJsonObject();
                content.info = jsonDeserializationContext.deserialize(infoObj, MessageContent.AudioMessageContent.Info.class);
            }

            return content;
        } else if(msgtype.equals("m.location")) {
            var content = new MessageContent.LocationMessageContent();
            content.body = object.get("body").getAsString();
            content.geoUri = object.get("geo_uri").getAsString();

            if(object.has("info")) {
                var infoObj = object.get("info").getAsJsonObject();
                content.info = jsonDeserializationContext.deserialize(infoObj, MessageContent.LocationMessageContent.Info.class);
            }

            return content;
        } else {
            throw new JsonParseException("Invalid/Unknown \"msgtype\" in JSON!");
        }
    }
}
