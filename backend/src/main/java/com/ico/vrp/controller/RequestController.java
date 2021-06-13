package main.java.com.ico.vrp.controller;

import main.java.com.ico.vrp.model.Location;
import main.java.com.ico.vrp.model.Request;
import main.java.com.ico.vrp.model.Response;
import main.java.com.ico.vrp.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public void request(@RequestBody Request request) {
        requestService.processRequest(request);
    }

    @GetMapping("/test")
    public Location[] test() {
        Location l1 = new Location(135.0012, 100.1253);
        Location l2 = new Location(50.0011, 12.1254);
        Location l3 = new Location(100.0020, 123.1253);
        Location l4 = new Location(10.0019, 12.1250);
        Location l5 = new Location(87.0017, 57.1260);
        Location l6 = new Location(17.0016, 12.1249);

        Request request = new Request(l1, new Location[]{l2, l3, l4, l5, l6});

        return requestService.processRequest(request);
    }

}
