package com.hodvidar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rest/owner")
public class OwnerController {

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "")
    public String getUserInfo() {
        return "Hodvidar";
    }

}
