package com.yellp.services.impl;

import com.yellp.dao.SupportRequestDao;
import com.yellp.dto.request.CustomerQueryDto;
import com.yellp.repository.SupportRequestRepository;
import com.yellp.services.SupportRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SupportRequestServiceImpl implements SupportRequestService {
    @Autowired
    private SupportRequestRepository supportRequestRepository;

    @Autowired
    private SupportRequestDao supportRequestDao;

    @Override
    public SupportRequestDao acceptRequest(CustomerQueryDto customerQueryDto) {
        supportRequestDao.setRequestId(UUID.randomUUID().toString());
        supportRequestDao.setUserId("user1"); //TODO remove hardcoding.
        supportRequestDao.setCustomerName(customerQueryDto.getName());
        supportRequestDao.setContact(customerQueryDto.getContact());
        supportRequestDao.setDescription(customerQueryDto.getDescription());
        supportRequestDao.setRequestTime(new Timestamp(Instant.now().toEpochMilli()));
        supportRequestDao.settPlusMin(customerQueryDto.gettPlusMinutes());
        supportRequestDao.setStatus(1);

        return supportRequestRepository.save(supportRequestDao);
    }

    @Override
    public List<SupportRequestDao> findRequest(String requestId) {
        List<SupportRequestDao> results;
        if(StringUtils.hasText(requestId)) {
            SupportRequestDao request = supportRequestRepository.findByRequestId(requestId);
            results = new ArrayList<>();
            if(request != null)
                results.add(request);
        }else {
            results = supportRequestRepository.findAll();
        }
        return results;
    }

    @Override
    @Transactional
    public boolean processRequest(String requestid) {
        int rowsAffected = supportRequestRepository.updateRequestStatusById(2,requestid);
        return rowsAffected > 0;
    }
}
