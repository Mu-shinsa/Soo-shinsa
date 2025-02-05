package com.Soo_Shinsa.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class StatisticsHeaderResponseDto {
    List<String> rowList;
    List<String> columnList;
}
