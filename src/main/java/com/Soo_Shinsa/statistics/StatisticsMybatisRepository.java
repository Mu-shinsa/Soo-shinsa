package com.Soo_Shinsa.statistics;

import com.Soo_Shinsa.statistics.dto.StatisticsRequestDto;
import com.Soo_Shinsa.statistics.dto.StatisticsForSaleRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface StatisticsMybatisRepository {
    List<String> getDateList(StatisticsRequestDto dto);
    List<String> getBrandList(StatisticsRequestDto dto);
    List<BigDecimal> getBodyDataListForSales(@Param("dto") StatisticsForSaleRequestDto dto, @Param("brand") String brand);

}
