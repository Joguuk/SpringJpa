package com.jjozerg.jkhr.vacation.service;

import com.jjozerg.jkhr.common.MessageUtils;
import com.jjozerg.jkhr.member.entity.Member;
import com.jjozerg.jkhr.member.repository.MemberRepository;
import com.jjozerg.jkhr.vacation.dto.VacationListResDto;
import com.jjozerg.jkhr.vacation.dto.VacationReqDto;
import com.jjozerg.jkhr.vacation.entity.VacationRequest;
import com.jjozerg.jkhr.vacation.repository.VacationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.jjozerg.jkhr.common.jkhrConstants.VacationStatus.*;
import static com.jjozerg.jkhr.common.jkhrConstants.VacationStatus.REQUEST;
import static com.jjozerg.jkhr.vacation.dto.VacationListResDto.*;

/**
 * packageName : com.jjozerg.jkhr.vacation.service
 * fileName : VacationRequestService
 * author : joguk
 * date : 2022/02/13
 * description : 휴가 Service
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/13 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Slf4j
@Service
@Transactional
public class VacationService {
    private VacationRepository vacationRepository;
    private MemberRepository memberRepository;

    public VacationService(VacationRepository vacationRepository, MemberRepository memberRepository) {
        this.vacationRepository = vacationRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 전달받은 회원ID와 년도에 해당하는 휴가 내역과 잔여/사용 휴가 일수를 반환한다.
     *
     * @author joguk
     * @date 2022/02/13 5:30 오후
     */
    public VacationListResDto retrieveVacations(Long memberId, Integer vacationYear) {
        Double useVacationCount = vacationRepository.retrieveVacationCount(memberId, vacationYear);
        List<VacationListDto> vacationListDtos = vacationRepository.retrieveVacationList(memberId, vacationYear);

        return new VacationListResDto(useVacationCount, vacationListDtos);
    }

    /**
     * 휴가를 저장한다.
     *
     * @author joguk
     * @date 2022/02/13 5:30 오후
     */
    public Long saveVacation(VacationReqDto vacationReqDto) throws Exception {
        Optional<Member> optionalMember = memberRepository.findById(vacationReqDto.getMemberId());
        Member member = optionalMember.orElseThrow(() -> new NoSuchElementException(MessageUtils.getMessages("message.member.notfound.error")));

        VacationRequest vacationRequest = VacationRequest.builder()
                .member(member)
                .vacationStatus(REQUEST)
                .vacationYear(vacationReqDto.getVacationYear())
                .vacationCount(vacationReqDto.getVacationCount())
                .vacationKind(vacationReqDto.getVacationKind())
                .vacationStartDttm(vacationReqDto.getVacationStartDttm())
                .vacationEndDttm(vacationReqDto.getVacationEndDttm())
                .note(vacationReqDto.getNote())
                .build();

        if (isVacationRequestPossible(vacationRequest, vacationReqDto)) {
            vacationRepository.save(vacationRequest);
        }

        return vacationRequest.getId();
    }

    /**
     * 휴가신청가능여부를 확인한다.
     *
     * @author joguk
     * @date 2022/02/13 7:48 오후
     */
    private boolean isVacationRequestPossible(VacationRequest vacationRequest, VacationReqDto vacationReqDto) throws Exception {
        Double useVacationCount = vacationRepository.retrieveVacationCount(vacationReqDto.getMemberId(), vacationReqDto.getVacationYear());
        vacationRequest.checkVacationCount(useVacationCount);

        return true;
    }

    /**
     * 휴가를 취소 상태로 변경한다.
     *
     * @author joguk
     * @date 2022/02/13 8:03 오후
     */
    public Long cancelVacation(Long vacationReqId) {
        Optional<VacationRequest> optionalVacationRequest = vacationRepository.findById(vacationReqId);
        VacationRequest vacationRequest = optionalVacationRequest.orElseThrow(
                () -> new NoSuchElementException(MessageUtils.getMessages("message.member.notfound.error")));

        // 취소 상태로 변경
        vacationRequest.cancelVacation();

        return vacationRequest.getId();
    }
}
