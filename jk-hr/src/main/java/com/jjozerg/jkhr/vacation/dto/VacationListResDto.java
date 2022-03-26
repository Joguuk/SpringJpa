package com.jjozerg.jkhr.vacation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

import static com.jjozerg.jkhr.common.jkhrConstants.VacationKind;
import static com.jjozerg.jkhr.common.jkhrConstants.VacationStatus;
import static com.jjozerg.jkhr.vacation.entity.VacationRequest.DEFAULT_VACATION_COUNT;

/**
 * packageName : com.jjozerg.jkhr.vacation.dto
 * fileName : VacationListResDto
 * author : joguk
 * date : 2022/02/13
 * description : 휴가목록응답 Dto
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/13 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Getter @ToString
@NoArgsConstructor
public class VacationListResDto {
    private Double useVacationCount;
    private Double remainingVacationCount;
    private List<VacationListDto> vacationListDtos;

    public VacationListResDto(Double useVacationCount, List<VacationListDto> vacationListDtos) {
        this.useVacationCount = useVacationCount;
        this.remainingVacationCount = DEFAULT_VACATION_COUNT - useVacationCount;
        this.vacationListDtos = vacationListDtos;
    }

    @Getter @ToString
    public static class VacationListDto {
        private Double vacationCount;
        private Integer vacationYear;
        private VacationStatus vacationStatus;
        private VacationKind vacationKind;
        private LocalDateTime vacationStartDttm;
        private LocalDateTime vacationEndDttm;
        private String note;
    }
}
