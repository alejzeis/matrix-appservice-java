package io.github.jython234.matrix.appservice.event;

public enum EventType {
    ROOM_ALIASES("m.room.aliases"),
    ROOM_CANONICAL_ALIAS("m.room.canonical_alias"),
    ROOM_CREATE("m.room.create"),
    ROOM_JOIN_RULES("m.room.join_rules"),
    ROOM_MEMBER("m.room.member"),
    ROOM_POWER_LEVELS("m.room.power_levels"),
    ROOM_REDACTION("m.room.redaction"),
    ROOM_MESSAGE("m.room.message"),
    ROOM_MESSAGE_FEEDBACK("m.room.message.feedback"),
    ROOM_NAME("m.room.name"),
    ROOM_TOPIC("m.room.topic"),
    ROOM_AVATAR("m.room.avatar"),
    ROOM_PINNED_EVENTS("m.room.pinned_events"),
    ROOM_HISTORY_VISIBILITY("m.room.history_visibility"),
    ROOM_THIRD_PARTY_INVITE("m.room.third_party_invite"),
    ROOM_GUEST_ACCESS("m.room.guest_access"),
    CALL_INVITE("m.call.invite"),
    CALL_CANDIDATES("m.call.candidates"),
    CALL_ANSWER("m.call.answer"),
    CALL_HANGUP("m.call.hangup"),
    TYPING("m.typing"),
    RECEIPT("m.receipt"),
    PRESENCE("m.presence"),
    TAG("m.tag"),
    DIRECT("m.direct");

    private String type;

    EventType(String type) {
        this.type = type;
    }

    /**
     * Return the EventType as a string, used in the Matrix protocol
     * JSON.
     * @return The EventType as a string.
     */
    public String asString() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.asString();
    }
}
