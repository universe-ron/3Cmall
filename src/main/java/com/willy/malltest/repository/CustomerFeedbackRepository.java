package com.willy.malltest.repository;

import com.willy.malltest.model.CustomerFeedback;
import com.willy.malltest.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerFeedbackRepository extends JpaRepository<CustomerFeedback, Integer> {

    @Query(value = "SELECT * FROM customer_feedback WHERE order_id = :orderID AND user_id = :userId", nativeQuery = true)
    CustomerFeedback findCustomerFeedbackByByordersIdAnduserId(@Param("orderID") Integer orderID, @Param("userId") Long userId);

    @Query(value = "SELECT * FROM customer_feedback WHERE user_id = :userId", nativeQuery = true)
    List<CustomerFeedback> findCustomerFeedbackByduserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "UPDATE customer_feedback SET type = :type, description = :description, customer_feedback_status = :customerFeedbackStatus WHERE feedback_id = :feedbackID", nativeQuery = true)
    void updateCustomerFeedback(@Param("type") String type, @Param("description") String description, @Param("customerFeedbackStatus") String customerFeedbackStatus, @Param("feedbackID") Integer feedbackID);

//    User findByfeedbackId(Integer feedbackID);

    CustomerFeedback findByFeedbackID(Integer feedbackID);

    @Query(value = "SELECT * FROM customer_feedback", nativeQuery = true)
    List<CustomerFeedback> findAllFeedbacks();
}
