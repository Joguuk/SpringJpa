package com.jjozerg.jkhr.vacation.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.jjozerg.jkhr.common.JkHrConstants.*;

/**
 * packageName : com.jjozerg.jkhr.vacation.dto
 * fileName : VacationReqDto
 * author : joguk
 * date : 2022/02/13
 * description : 휴가신청Dto
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/13 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Getter
@ToString
public class VacationReqDto {
    @NotNull
    private Long memberId;
    @NotNull
    private Double vacationCount;
    @NotNull
    private Integer vacationYear;
    @NotNull
    private VacationKind vacationKind;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime vacationStartDttm;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime vacationEndDttm;

    private String note;
}
