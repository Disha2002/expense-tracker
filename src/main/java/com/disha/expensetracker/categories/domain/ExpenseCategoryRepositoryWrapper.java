package com.disha.expensetracker.categories.domain;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ExpenseCategoryRepositoryWrapper {

    private final ExpenseCategoryRepository repository;

    public ExpenseCategoryRepositoryWrapper(final ExpenseCategoryRepository repository) {
        this.repository = repository;
    }

    public List<ExpenseCategory> fetchByCreatedByAndDate(Long userId, ZonedDateTime time) {
        return this.repository.fetchByCreatedByAndDate(userId, time);
    }

    public List<ExpenseCategory> fetchByCreatedBy(Long userId) {
        return this.repository.findByCreatedBy(userId);
    }

    public ExpenseCategory save(ExpenseCategory expenseCategory) {
        return this.repository.save(expenseCategory);
    }

}
