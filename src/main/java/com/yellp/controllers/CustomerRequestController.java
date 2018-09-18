package com.yellp.controllers;

import com.yellp.dao.SupportRequestDao;
import com.yellp.dto.request.CustomerQueryDto;
import com.yellp.services.SupportRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class CustomerRequestController {

    @Autowired
    private SupportRequestService supportRequestService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRequestController.class);

    @PostMapping(value="/callme",produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SupportRequestDao> callme(@RequestBody CustomerQueryDto query, HttpServletRequest request,
                                                    HttpServletResponse response) {
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        LOGGER.info(String.format("Origin: %s", origin));
        SupportRequestDao savedRequest = supportRequestService.acceptRequest(query);
        return ResponseEntity.ok(savedRequest);
    }

    @RequestMapping(value = "/request",produces = MediaType.APPLICATION_JSON_VALUE,method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<List<SupportRequestDao>> allRequests(@RequestParam(name = "requestid",defaultValue = "") String requestId) {
        List<SupportRequestDao> requests = supportRequestService.findRequest(requestId);
        if(!requests.isEmpty())
            return ResponseEntity.ok(requests);
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/answer",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String>  processRequest(@RequestParam(name = "requestid") String requestid) {
        if(supportRequestService.processRequest(requestid))
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update status.");
    }
}
