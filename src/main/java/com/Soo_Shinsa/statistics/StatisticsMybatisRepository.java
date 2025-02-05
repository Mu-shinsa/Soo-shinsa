package com.Soo_Shinsa.statistics;

import com.Soo_Shinsa.statistics.dto.StatisticsForSaleRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface StatisticsMybatisRepository {
    List<String> getDateList(StatisticsForSaleRequestDto dto);
    List<String> getBrandList(StatisticsForSaleRequestDto dto);
    List<BigDecimal> getBodyDataList(@Param("dto") StatisticsForSaleRequestDto dto, @Param("brand") String brand);

}
