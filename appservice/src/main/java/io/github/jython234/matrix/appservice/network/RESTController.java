package io.github.jython234.matrix.appservice.network;

import com.google.gson.Gson;
import io.github.jython234.matrix.appservice.MatrixAppservice;
import io.github.jython234.matrix.appservice.event.MatrixEvent;
import io.github.jython234.matrix.appservice.event.TypingMatrixEvent;
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
    private JSONParser parser = new JSONParser();

    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.PUT)
    @SuppressWarnings("unchecked")
    public ResponseEntity transactions(@PathVariable(value = "id") long id,
                                       @RequestParam("access_token") String accessToken, @RequestBody String body, HttpServletRequest request) {

        if(!accessToken.equals(MatrixAppservice.getInstance().getRegistration().getHsToken()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid access token");

        try {
            var gson = new Gson();
            JSONObject json = (JSONObject) this.parser.parse(body);
            JSONArray transactions = (JSONArray) json.get("events");

            transactions.forEach((transaction) -> {
                var eventWithType = gson.fromJson((String) transaction, MatrixEvent.class);

                // TODO: multi-thread event handling
                MatrixAppservice.getInstance().getEventHandler().onMatrixEvent(EventDecoder.decodeEvent((String) transaction, eventWithType));
            });
        } catch (ParseException e) {
            MatrixAppservice.getInstance().logger.warn("Malformed JSON from " + request.getRemoteAddr());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"errcode\":\"appservice.M_BAD_JSON\"}");
        }

        return ResponseEntity.ok("{}");
    }

    @RequestMapping(value = "/rooms/{alias}", method = RequestMethod.GET)
    public ResponseEntity rooms(@PathVariable(value = "alias") String alias, @RequestParam("access_token") String accessToken) {

        if(!accessToken.equals(MatrixAppservice.getInstance().getRegistration().getHsToken()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid access token");

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("");
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity users(@PathVariable(value = "id") String id, @RequestParam("access_token") String accessToken) {

        if(!accessToken.equals(MatrixAppservice.getInstance().getRegistration().getHsToken()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid access token");

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("");
    }
}
