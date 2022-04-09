package com.jjozerg.jkhr.vacation.controller;

import com.jjozerg.jkhr.common.MessageUtils;
import com.jjozerg.jkhr.vacation.dto.VacationListResDto;
import com.jjozerg.jkhr.vacation.dto.VacationReqDto;
import com.jjozerg.jkhr.vacation.service.VacationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * packageName : com.jjozerg.jkhr.vacation.controller
 * fileName : VacationRequestController
 * author : joguk
 * date : 2022/02/13
 * description : 휴가 Controller
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/13 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Tag(name = "휴가신청Controller")
@RestController
@RequestMapping("/vacations/v1")
@Validated
public class VacationController {
    private VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    /**
     * 신청 받은 휴가를 저장하고, 남은 휴가일 수를 반환한다.
     *  1) 반차, 연차인 경우 시작일 종료일이 불필요
     *  2) 연차일 경우 시작일 종료일 검증
     *
     * @author joguk
     * @date 2022/02/13 4:28 오후
     */
    @Operation(summary = "휴가 신청", description = "휴가를 신청한다.")
    @PostMapping
    public String requestVacation(
            @RequestBody @Valid VacationReqDto vacationReqDto) throws Exception {
        Double remainingVacationCount = vacationService.requestVacation(vacationReqDto);

        return MessageUtils.getMessages("message.vacation.request.success", new Object[]{Double.toString(remainingVacationCount)});
    }

    /**
     * 휴가 취소 요청을 처리한다.
     *
     * @author joguk
     * @date 2022/02/14 10:42 오후
     */
    @Operation(summary = "휴가 취소", description = "휴가신청ID에 해당하는 휴가를 취소한다.")
    @PutMapping("/{vacationReqId}/cancel")
    public String cancelVacation(@PathVariable Long vacationReqId) {
        vacationService.cancelVacation(vacationReqId);
        return MessageUtils.getMessages("message.vacation.cancel.success");
    }

    /**
     * 전달받은 회원ID와 년도에 해당하는 휴가 내역과 잔여/사용 휴가 일수를 반환한다.
     *
     * @author joguk
     * @date 2022/02/13 4:28 오후
     */
    @Operation(summary = "휴가목록 조회", description = "회원ID와 년도를 통해 휴가 신청 내역과 잔여 휴가 일수를 조회한다.")
    @GetMapping("/{memberId}/{vacationYear}")
    public VacationListResDto retrieveVacations(
            @PathVariable Long memberId,
            @PathVariable @Min(2010) @Max(9999) Integer vacationYear) {
        return vacationService.retrieveVacations(memberId, vacationYear);
    }
}
