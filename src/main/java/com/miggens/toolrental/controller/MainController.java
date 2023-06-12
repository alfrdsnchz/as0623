package com.miggens.toolrental.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miggens.toolrental.pojo.UsageResponse;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping("")
    public UsageResponse main() {
        String endpointStr = "checkout/<dd-mm-yyyy>/item/<tool-code: ex LADW,\\w{4}>/days/<num-days: 1 or greater>/discount/<discount-value:0-100>";

        return new UsageResponse(String.format("Only usable enpoint is POST: %s", endpointStr)); 
    }

}
