package io.github.jython234.matrix.appservice.network;

import com.google.gson.annotations.SerializedName;

public final class CreateRoomRequest {
    public String preset = "public_chat";
    public String visibility = "public";

    @SerializedName("room_alias_name")
    public String roomAliasName;

    public String name;
    public String topic;
}
