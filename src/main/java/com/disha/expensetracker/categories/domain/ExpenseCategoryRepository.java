package com.disha.expensetracker.categories.domain;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

        @Query("""
                            SELECT c FROM ExpenseCategory c
                            WHERE c.createdBy = :userId
                              AND (:selectedTime < c.validTill OR c.validTill IS NULL)
                        """)
        List<ExpenseCategory> fetchByCreatedByAndDate(@Param("userId") Long userId,
                        @Param("selectedTime") ZonedDateTime selectedTime);

        List<ExpenseCategory> findByCreatedBy(@Param("createdBy") Long createdBy);

}
