package com.Soo_Shinsa.statistics;

import com.Soo_Shinsa.constant.StatisticsEnum;
import com.Soo_Shinsa.statistics.dto.StatisticsBodyResponseDto;
import com.Soo_Shinsa.statistics.dto.StatisticsForSaleRequestDto;
import com.Soo_Shinsa.statistics.dto.StatisticsHeaderResponseDto;
import com.Soo_Shinsa.statistics.dto.StatisticsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsMybatisRepository repository;


    @Override
    public StatisticsResponseDto getStatisticsOfSales(StatisticsForSaleRequestDto requestDto) {
        StatisticsResponseDto dto = new StatisticsResponseDto();

        List<String> dateList = repository.getDateList(requestDto);
        List<String> brandList = repository.getBrandList(requestDto);
        StatisticsHeaderResponseDto statisticsHeaderResponseDto = new StatisticsHeaderResponseDto(dateList, brandList);

        for (String brand : brandList) {
            List<String> dataList = repository.getBodyDataList(requestDto, brand)
                    .stream()
                    .map(data -> {
                        if (requestDto.getStartPrice() != null && requestDto.getEndPrice() != null) {
                            return data.compareTo(requestDto.getStartPrice()) > 0 && data.compareTo(requestDto.getEndPrice()) < 0 ? data : BigDecimal.ZERO;
                        } else if (requestDto.getStartPrice() != null) {
                            return data.compareTo(requestDto.getStartPrice()) > 0 ? data : BigDecimal.ZERO;
                        } else if (requestDto.getEndPrice() != null ) {
                            return data.compareTo(requestDto.getEndPrice()) < 0 ? data : BigDecimal.ZERO;
                        }else {
                            return data;
                        }
                    })
                    .map(BigDecimal::toString)
                    .toList();
            dto.addBodyData(dataList);
        }

        //총합 추가
        brandList.add(StatisticsEnum.TOTAL.getName());
        dto.addHeaderData(statisticsHeaderResponseDto);
        dto.addBodyData(repository.getBodyDataList(requestDto, null)
                .stream()
                .map(BigDecimal::toString)
                .toList());

        return dto;

    }
}
