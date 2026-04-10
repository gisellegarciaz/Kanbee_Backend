package com.kanbee.api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsDTO {

    private String userName;
    private String listName;
    private Long timeInSeconds;
    private Long timeInMinutes;
}