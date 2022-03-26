package com.jjozerg.jkhr.vacation.controller;

import com.jjozerg.jkhr.vacation.dto.VacationListResDto;
import com.jjozerg.jkhr.vacation.dto.VacationReqDto;
import com.jjozerg.jkhr.vacation.service.VacationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@Slf4j
@RequestMapping("/vacations/v1")
public class VacationController {
    private VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    /**
     * 휴가를 저장한다.
     *
     * @author joguk
     * @date 2022/02/13 4:28 오후
     */
    @PostMapping
    public Long saveVacation(
            @RequestBody @Valid VacationReqDto vacationReqDto) throws Exception {
        return vacationService.saveVacation(vacationReqDto);
    }

    @PutMapping("/{vacationReqId}/cancel")
    public Long cancelVacation(@PathVariable    Long vacationReqId) {
        return vacationService.cancelVacation(vacationReqId);
    }

    /**
     * 전달받은 회원ID와 년도에 해당하는 휴가 내역과 잔여/사용 휴가 일수를 반환한다.
     *
     * @author joguk
     * @date 2022/02/13 4:28 오후
     */
    @GetMapping
    public VacationListResDto retrieveVacations(
            @RequestParam Long memberId,
            @RequestParam Integer vacationYear) {
        return vacationService.retrieveVacations(memberId, vacationYear);
    }
}
