package com.jjozerg.jkhr.vacation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jjozerg.jkhr.common.CroquiscomHrConstants;
import com.jjozerg.jkhr.common.MessageUtils;
import com.jjozerg.jkhr.config.exception.BusinessException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationCount.*;
import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationKind;
import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationKind.HALF;
import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationKind.QUARTER;

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
    @JsonIgnore public static final int FOUR_HOUR_SECONDS = 14400;      // 4시간(14,400초)
    @JsonIgnore public static final int TWO_HOUR_SECONDS = 7200;      // 2시간(7,200초)

    @NotNull
    @Schema(description = "회원번호", defaultValue = "0")
    private Long memberId;

    @NotNull
    @Schema(description = "휴가년도", defaultValue = "2022")
    private Integer vacationYear;

    @NotNull
    @Schema(description = "휴가유형", defaultValue = "ALL", allowableValues = {"ALL", "HALF", "QUARTER"})
    private VacationKind vacationKind;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "휴가시작일자", defaultValue = "2022-01-01T00:00:00.0000")
    private LocalDateTime vacationStartDttm;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "휴가종료일자", defaultValue = "2022-01-01T00:00:00.0000")
    private LocalDateTime vacationEndDttm;
    @Schema(description = "비고")
    private String note;

    @JsonIgnore
    private Double vacationCount;

    /**
     * 종료일자가 입력되었는지 반환한다.
     *
     * @author joguk
     * @date 2022/02/14 11:17 오후
     */
    public boolean validVacationDttm() {
        return vacationEndDttm != null;
    }

    /**
     * 휴가 신청 데이터를 검증한다.
     *  1) 연차인데 종료일자가 없는 경우 fail
     *  2) 반차,반반차인 경우 시간 검증
     *  3) 연차인 경우, 시작일 종료일 검증 후 세팅
     *
     * @author joguk
     * @date 2022/02/14 11:21 오후
     * @param usedVacationCount
     */
    public void validateVacationRequest(Double usedVacationCount) {
        if (vacationKind.isAll()) {
            validateAllVacation(usedVacationCount);
            return;
        }

        validateHalfAndQuarterVacation(usedVacationCount);
    }

    /**
     * 연차 신청 데이터를 검증한다.
     *  1) 종료일자가 없는 경우 fail
     *  2) 시작일자와 종료일자로 연차 수 count
     *  3) 연차인 경우 시작시간과 종료시간을 0900~1800으로 변경한다.
     *
     * @author joguk
     * @date 2022/02/14 11:25 오후
     * @param usedVacationCount
     */
    private void validateAllVacation(Double usedVacationCount) {
        if (!validVacationDttm()) {
            throw new BusinessException(MessageUtils.getMessages("message.vacation.request.fail.end.date"));    // 연차인 경우, 휴가 종료일을 입력해주시기 바랍니다.
        }

        Long between = ChronoUnit.DAYS.between(vacationStartDttm, vacationEndDttm);
        this.vacationCount = between.doubleValue();
        vacationStartDttm = LocalDateTime.of(vacationStartDttm.getYear(), vacationStartDttm.getMonth(), vacationStartDttm.getDayOfMonth(), 9, 0, 0);
        vacationEndDttm = LocalDateTime.of(vacationEndDttm.getYear(), vacationEndDttm.getMonth(), vacationEndDttm.getDayOfMonth(), 18, 0, 0);

        validateVacationCount(usedVacationCount);
    }

    /**
     * 신청 가능 휴가 일수를 검증한다.
     *
     * @author joguk
     * @date 2022/02/15 11:45 오후
     */
    private void validateVacationCount(Double usedVacationCount) {
        if (vacationCount + usedVacationCount > DEFAULT.getVacationCount()) {
            throw new BusinessException(MessageUtils.getMessages("message.vacation.request.fail.max.count"));    // 신청 가능한 휴가 가능일수를 초과했습니다.
        }
    }

    /**
     * 반차,반반차 신청 데이터를 검증한다.
     *
     * @author joguk
     * @date 2022/02/14 11:34 오후
     * @param usedVacationCount
     */
    private void validateHalfAndQuarterVacation(Double usedVacationCount) {
        Duration duration = Duration.between(vacationStartDttm, vacationEndDttm);
        long seconds = duration.getSeconds();

        if (vacationKind.isHalf()) {
            checkVacationTime(seconds, FOUR_HOUR_SECONDS, "message.vacation.request.fail.half.time", HALF);
        }

        if (vacationKind.isQuarter()) {
            checkVacationTime(seconds, TWO_HOUR_SECONDS, "message.vacation.request.fail.quarter.time", QUARTER);
        }

        validateVacationCount(usedVacationCount);
    }

    /**
     * 반차, 반반차의 시간을 검증한다.
     *
     * @author joguk
     * @date 2022/02/15 11:19 오후
     */
    private void checkVacationTime(long seconds, int hourSeconds, String messageCode, VacationKind vacationKind) {
        if (seconds != hourSeconds) {
            throw new BusinessException(MessageUtils.getMessages(messageCode));
        }
        vacationCount = vacationKind.getVacationHolidayQt();
        return;
    }

}
