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
