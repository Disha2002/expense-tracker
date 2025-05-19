package com.disha.expensetracker.categories.api;

import java.time.ZonedDateTime;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disha.expensetracker.categories.data.ExpenseCategoryRequest;
import com.disha.expensetracker.categories.data.ExpenseCategoryResponse;
import com.disha.expensetracker.categories.data.ExpenseCategoryResponse.CategoryDetails;
import com.disha.expensetracker.categories.service.ExpenseCategoryService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/categories")
public class ExpenseCategoryApiResource {

    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryApiResource(final ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @GetMapping("all")
    public ExpenseCategoryResponse getcategories(@PathParam("selectedDate") ZonedDateTime selectedDate,
            Authentication authentication) throws BadRequestException {
        return this.expenseCategoryService.fetchCategories(authentication, selectedDate);
    }

    @PostMapping()
    public CategoryDetails postMethodName(@RequestBody ExpenseCategoryRequest entity, Authentication authentication)
            throws BadRequestException {
        return this.expenseCategoryService.saveCategory(authentication, entity);
    }

}
