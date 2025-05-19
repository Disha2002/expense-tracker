package com.disha.expensetracker.categories.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCategoryResponse {
    private List<CategoryDetails> categories;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDetails {
        private Long id;
        private String name;
        private String description;
    }
}
