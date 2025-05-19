package com.disha.expensetracker.categories.data;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCategoryRequest {

    private String name;
    private String description;
    private ZonedDateTime validFrom;
    private ZonedDateTime validTill;

}
