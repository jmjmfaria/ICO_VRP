package main.java.com.ico.vrp.controller;

import main.java.com.ico.vrp.model.FullResponse;
import main.java.com.ico.vrp.model.Location;
import main.java.com.ico.vrp.model.Request;
import main.java.com.ico.vrp.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    private RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService){
        this.requestService = requestService;
    }

    @PostMapping("/request")
    public FullResponse request(@RequestBody Request request) {
        return requestService.processRequest(request);
    }

}
