package io.github.jython234.matrix.appservice.network;

import io.github.jython234.matrix.appservice.MatrixAppservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The main Spring Controller that handles
 * all incoming REST requests to the appservice
 * from the homeserver.
 *
 * @author jython234
 */
@RestController
public class RESTController {

    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.PUT)
    public ResponseEntity transactions(@PathVariable(value = "id") long id,
                                       @RequestParam("access_token") String accessToken, @RequestBody String body) {

        if(!accessToken.equals(MatrixAppservice.getInstance().getRegistration().getHsToken()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid access token");

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("");
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
