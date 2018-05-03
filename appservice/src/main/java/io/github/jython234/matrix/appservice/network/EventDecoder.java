package io.github.jython234.matrix.appservice.network;

import com.google.gson.Gson;
import io.github.jython234.matrix.appservice.event.MatrixEvent;
import io.github.jython234.matrix.appservice.event.TypingMatrixEvent;

final class EventDecoder {
    private static Gson gson = new Gson();

    static MatrixEvent decodeEvent(String json, MatrixEvent eventWithType) {
        switch (eventWithType.getType()) {
            case TypingMatrixEvent.TYPE:
                return gson.fromJson(json, TypingMatrixEvent.class);
            default:
                return null;
        }
    }
}
