package main.java.com.ico.vrp.controller;

import main.java.com.ico.vrp.model.Customer;
import main.java.com.ico.vrp.model.Location;
import main.java.com.ico.vrp.model.Request;
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
    public Location[] request(@RequestBody Request request) {
        return requestService.processRequest(request);
    }

    /*
    @GetMapping("/test")
    public Location[] test() {
        Location l1 = new Location(135.0012, 100.1253);
        Customer l2 = new Customer(50.0011, 12.1254, new int[]{480, 1080}, 0, 0, false);
        Customer l3 = new Customer(100.0020, 123.1253, new int[]{480, 1080}, 0, 0, false);
        Customer l4 = new Customer(10.0019, 12.1250, new int[]{480, 1080}, 0, 0, false);
        Customer l5 = new Customer(87.0017, 57.1260, new int[]{480, 1080}, 0, 0, false);
        Customer l6 = new Customer(17.0016, 12.1249, new int[]{480, 1080}, 0, 0, false);

        //Request request = new Request(l1, new Customer[]{l2, l3, l4, l5, l6});

        return requestService.processRequest(request);
    }*/

}
