package io.github.jython234.matrix.appservice.event.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class MessageContentSerializer implements JsonSerializer<MessageContent> {

    @Override
    public JsonElement serialize(MessageContent messageContent, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(messageContent);
    }
}
