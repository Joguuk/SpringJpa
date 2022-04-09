package com.jjozerg.jkhr.vacation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationCount.DEFAULT;
import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationKind;
import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationStatus;

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
        this.remainingVacationCount = DEFAULT.getVacationCount() - useVacationCount;
        this.vacationListDtos = vacationListDtos;
    }

    @Getter @ToString
    public static class VacationListDto {
        private Long vacationReqId;
        private Double vacationCount;
        private Integer vacationYear;
        private VacationStatus vacationStatus;
        private VacationKind vacationKind;
        private LocalDateTime vacationStartDttm;
        private LocalDateTime vacationEndDttm;
        private String note;
    }
}
