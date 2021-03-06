package com.jjozerg.jkhr.vacation.repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.jjozerg.jkhr.vacation.dto.VacationListResDto.VacationListDto;

/**
 * packageName : com.jjozerg.jkhr.vacation.repository
 * fileName : VacationRepositoryCustom
 * author : joguk
 * date : 2022/02/13
 * description : 휴가Custom Repository
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/13 joguk 최초 생성
 * -----------------------------------------------------------
 */
public interface VacationRepositoryCustom {
    Double retrieveVacationCount(Long memberId, int vactionYear);

    List<VacationListDto> retrieveVacationList(Long memberId, int vactionYear);

    boolean retrieveIsDuplicateVacationDate(Long memberId, int vactionYear, LocalDateTime vacationStartDttm, LocalDateTime vacationEndDttm);
}
