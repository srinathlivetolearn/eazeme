package com.yellp.repository;

import com.yellp.dao.SupportRequestDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRequestRepository extends JpaRepository<SupportRequestDao,Long> {
    SupportRequestDao findByRequestId(String requestId);
    @Modifying
    @Query("update SupportRequestDao sr set sr.status = ?1 where sr.requestId = ?2")
    int updateRequestStatusById(Integer status,String requestId);
}
