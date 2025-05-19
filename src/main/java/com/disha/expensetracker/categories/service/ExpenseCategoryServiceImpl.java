package com.disha.expensetracker.categories.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.disha.expensetracker.categories.data.ExpenseCategoryRequest;
import com.disha.expensetracker.categories.data.ExpenseCategoryResponse;
import com.disha.expensetracker.categories.data.ExpenseCategoryResponse.CategoryDetails;
import com.disha.expensetracker.categories.domain.ExpenseCategory;
import com.disha.expensetracker.categories.domain.ExpenseCategoryRepositoryWrapper;
import com.disha.expensetracker.user.domain.User;
import com.disha.expensetracker.user.domain.UserDetailsRepositoryWrapper;

@Service
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

        @Autowired
        private final UserDetailsRepositoryWrapper userRepository;
        private final ExpenseCategoryRepositoryWrapper expenseCategoryRepository;

        public ExpenseCategoryServiceImpl(UserDetailsRepositoryWrapper userRepository,
                        ExpenseCategoryRepositoryWrapper expenseCategoryRepository) {
                this.userRepository = userRepository;
                this.expenseCategoryRepository = expenseCategoryRepository;
        }

        @Override
        public ExpenseCategoryResponse fetchCategories(Authentication authentication, ZonedDateTime selectedDate)
                        throws BadRequestException {
                String username = authentication.getName();
                User user = userRepository.findByUserName(username);
                List<ExpenseCategory> list = this.expenseCategoryRepository.fetchByCreatedByAndDate(user.getId(),
                                selectedDate);
                return convertToResponse(list);
        }

        private ExpenseCategoryResponse convertToResponse(List<ExpenseCategory> list) {

                List<ExpenseCategoryResponse.CategoryDetails> detailsList = list.stream()
                                .map(data -> ExpenseCategoryResponse.CategoryDetails.builder()
                                                .id(data.getId())
                                                .name(data.getName())
                                                .description(data.getDescription())
                                                .build())
                                .collect(Collectors.toList());
                return ExpenseCategoryResponse.builder()
                                .categories(detailsList)
                                .build();
        }

        @Override
        public CategoryDetails saveCategory(Authentication authentication, ExpenseCategoryRequest request)
                        throws BadRequestException {
                String username = authentication.getName();
                User user = userRepository.findByUserName(username);
                validateForCreate(request, user.getId());
                ExpenseCategory category = prepareData(request, user.getId());
                category = this.expenseCategoryRepository.save(category);
                return convertTo(category);
        }

        private void validateForCreate(ExpenseCategoryRequest request, Long userId) throws BadRequestException {
                if (request == null) {
                        throw new IllegalArgumentException("Request cannot be null");
                }
                List<ExpenseCategory> names = this.expenseCategoryRepository.fetchByCreatedBy(userId);
                Boolean nameExists = names.stream()
                                .anyMatch(category -> category.getName().equalsIgnoreCase(request.getName()));
                if (nameExists) {
                        throw new BadRequestException("Category Name Already exists");
                }
                if (request.getName() == null || request.getName().trim().isEmpty()) {
                        throw new IllegalArgumentException("Category name cannot be null or empty");
                }

                if (request.getValidFrom() == null) {
                        throw new IllegalArgumentException("validFrom date cannot be null");
                }
        }

        private ExpenseCategory prepareData(ExpenseCategoryRequest request, Long userId) {
                return ExpenseCategory.builder()
                                .name(request.getName())
                                .description(request.getDescription())
                                .validFrom(request.getValidFrom())
                                .validTill(null)
                                .createdBy(userId)
                                .build();
        }

        private CategoryDetails convertTo(ExpenseCategory data) {
                if (data == null)
                        return null;

                return CategoryDetails.builder()
                                .id(data.getId())
                                .name(data.getName())
                                .description(data.getDescription())
                                .build();
        }

}
