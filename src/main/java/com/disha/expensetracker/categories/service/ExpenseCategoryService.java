package com.disha.expensetracker.categories.service;

import java.time.ZonedDateTime;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

import com.disha.expensetracker.categories.data.ExpenseCategoryRequest;
import com.disha.expensetracker.categories.data.ExpenseCategoryResponse;
import com.disha.expensetracker.categories.data.ExpenseCategoryResponse.CategoryDetails;

public interface ExpenseCategoryService {

        ExpenseCategoryResponse fetchCategories(Authentication authentication, ZonedDateTime selectedDate)
                        throws BadRequestException;

        CategoryDetails saveCategory(Authentication authentication, ExpenseCategoryRequest request)
                        throws BadRequestException;

}
