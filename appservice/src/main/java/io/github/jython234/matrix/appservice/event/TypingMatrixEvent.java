package io.github.jython234.matrix.appservice.event;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.List;

public class TypingMatrixEvent extends MatrixEvent {
    /**
     * List of Matrix User Ids that are typing.
     */
    public List<String> userIds;

    public TypingMatrixEvent() {
        this.type = EventType.TYPING;
    }

    @Override
    public JSONObject encode() {
        var object = super.encode();
        var content = new JSONObject();

        JSONArray array = new JSONArray();
        array.addAll(this.userIds);
        content.put("user_ids", array);

        object.put("content", content);
        return object;
    }

    @Override
    public void decode(JSONObject object) {
        super.decode(object);

        JSONArray userIds = (JSONArray) ((JSONObject) object.get("content")).get("user_ids");
        this.userIds = userIds;
    }
}
