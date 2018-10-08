package com.yellp.controllers;

import com.yellp.dao.SupportRequestDao;
import com.yellp.dao.UserEntity;
import com.yellp.dto.request.CustomerQueryDto;
import com.yellp.services.SupportRequestService;
import com.yellp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

import static com.yellp.utils.Constants.API_KEY_USER_ID;

@RestController
@RequestMapping("/api")
public class CustomerRequestController {

    @Autowired
    private SupportRequestService supportRequestService;

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRequestController.class);

    @PostMapping(value="/callme",produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SupportRequestDao> callme(@RequestBody CustomerQueryDto query, HttpServletRequest request,
                                                    HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        Objects.requireNonNull(session,"Session is not set");
        SupportRequestDao savedRequest = supportRequestService.acceptRequest(query,
                (String) session.getAttribute(API_KEY_USER_ID.value()));
        LOGGER.info("Request registered with id {}",savedRequest.getRequestId());
        return ResponseEntity.ok(savedRequest);
    }

    @PostMapping(value = "/signup",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserEntity signup(@RequestBody UserEntity user, HttpServletRequest request,HttpServletResponse response) {
        return userService.registerUser(user);
    }

    @GetMapping(value = "/request",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<SupportRequestDao>> allRequests(@RequestParam(name = "requestid",defaultValue = "") String requestId) {
        List<SupportRequestDao> requests = supportRequestService.findRequest(requestId);
        if(!requests.isEmpty())
            return ResponseEntity.ok(requests);
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/answer",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String>  processRequest(@RequestParam(name = "requestid") String requestId) {
        if(supportRequestService.processRequest(requestId))
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update status.");
    }
}
