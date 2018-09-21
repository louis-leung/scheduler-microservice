package com.louisleung.springboot.schedulermicroservice.controllers;

import com.louisleung.springboot.schedulermicroservice.models.Report;
import com.louisleung.springboot.schedulermicroservice.services.HomeResourceAssembler;
import com.louisleung.springboot.schedulermicroservice.services.ReportResourceAssembler;
import com.louisleung.springboot.schedulermicroservice.services.Scheduler;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
public class HomeController {

    public static final String HOME_BASE_URI = "/api";

    private ReportResourceAssembler reportResourceAssembler;
    private HomeResourceAssembler homeResourceAssembler;

    @Autowired
    public HomeController(ReportResourceAssembler reportResourceAssembler, HomeResourceAssembler homeResourceAssembler) {
        this.reportResourceAssembler = reportResourceAssembler;
        this.homeResourceAssembler = homeResourceAssembler;
    }

    @ApiOperation(value = "Returns resource containing links to other resources")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK")
            }
    )
    @GetMapping(path="/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Resource<String> homePage() {
        return this.homeResourceAssembler.toResource("Home Page");
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK")
            }
    )
    @ApiOperation(value = "Returns a report")
    @GetMapping(path="/report", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Resource<Report> getReport() {
        return this.reportResourceAssembler.toResource(Scheduler.generateReport());
    }
}

