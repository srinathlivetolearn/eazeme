package com.yellp.services;

import com.yellp.dto.request.CustomerQueryDto;
import com.yellp.dao.SupportRequestDao;

import java.util.List;

public interface SupportRequestService {
    SupportRequestDao acceptRequest(CustomerQueryDto supportRequest, String userId);

    List<SupportRequestDao> findRequest(String requestId);

    boolean processRequest(String requestid);
}
