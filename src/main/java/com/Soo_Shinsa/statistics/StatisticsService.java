package com.Soo_Shinsa.statistics;

import com.Soo_Shinsa.statistics.dto.StatisticsForSaleRequestDto;
import com.Soo_Shinsa.statistics.dto.StatisticsResponseDto;
import jakarta.validation.Valid;

public interface StatisticsService {
    StatisticsResponseDto getStatisticsOfSales(@Valid StatisticsForSaleRequestDto requestDto);
}
