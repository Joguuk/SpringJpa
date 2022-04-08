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

import static com.jjozerg.jkhr.common.JkHrConstants.VacationStatus.REQUEST;
import static com.jjozerg.jkhr.vacation.dto.VacationListResDto.VacationListDto;
import static com.jjozerg.jkhr.vacation.entity.VacationRequest.DEFAULT_VACATION_COUNT;

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
     * 신청 받은 휴가를 저장하고, 남은 휴가일 수를 반환한다.
     *
     * @author joguk
     * @date 2022/02/13 5:30 오후
     */

    public double requestVacation(VacationReqDto vacationReqDto) throws Exception {
        vacationReqDto.validateVacationRequest();

        Optional<Member> optionalMember = memberRepository.findById(vacationReqDto.getMemberId());
        Member member = optionalMember.orElseThrow(
                () -> new NoSuchElementException(MessageUtils.getMessages("message.login.fail.notfound.error")));   // 회원 데이터 없습니다.

        VacationRequest vacationRequest = saveVacation(vacationReqDto, member);
        Double useVacationCount = vacationRepository.retrieveVacationCount(vacationReqDto.getMemberId(), vacationReqDto.getVacationYear());

        return getRemainingVacationCount(useVacationCount);
    }

    /**
     * 휴가신청 가능여부를 확인한다.
     *
     * @author joguk
     * @date 2022/02/13 7:48 오후
     */
    private boolean isVacationRequestPossible(VacationRequest vacationRequest, VacationReqDto vacationReqDto) throws Exception {
        Double useVacationCount = vacationRepository.retrieveVacationCount(vacationReqDto.getMemberId(), vacationReqDto.getVacationYear());
        vacationRequest.checkVacationCount(useVacationCount);

        boolean isDuplicateVacationDate = vacationRepository.retrieveIsDuplicateVacationDate(vacationReqDto.getMemberId(), vacationReqDto.getVacationYear()
                , vacationReqDto.getVacationStartDttm(), vacationReqDto.getVacationEndDttm());

        vacationRequest.checkVacationDuplicateDate(isDuplicateVacationDate);

        return true;
    }

    /**
     * 휴가를 저장한다.
     *
     * @author joguk
     * @date 2022/02/14 10:01 오후
     */
    private VacationRequest saveVacation(VacationReqDto vacationReqDto, Member member) throws Exception {
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

        return vacationRequest;
    }

    /**
     * 남은 휴가 일수를 반환한다.
     *
     * @author joguk
     * @date 2022/02/14 10:05 오후
     */
    public double getRemainingVacationCount(Double useVacationCount) {
        return DEFAULT_VACATION_COUNT - useVacationCount;
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
                () -> new NoSuchElementException(MessageUtils.getMessages("message.login.fail.notfound.error")));   // 회원 데이터 없습니다.

        // 취소 상태로 변경
        vacationRequest.cancelVacation();

        return vacationRequest.getId();
    }
}
