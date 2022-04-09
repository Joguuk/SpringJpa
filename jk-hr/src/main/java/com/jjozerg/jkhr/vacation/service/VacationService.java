package com.jjozerg.jkhr.vacation.service;

import com.jjozerg.jkhr.common.MessageUtils;
import com.jjozerg.jkhr.config.exception.BusinessException;
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

import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationStatus.REQUEST;
import static com.jjozerg.jkhr.vacation.dto.VacationListResDto.VacationListDto;

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
        Double usedVacationCount = vacationRepository.retrieveVacationCount(memberId, vacationYear);
        List<VacationListDto> vacationListDtoList = vacationRepository.retrieveVacationList(memberId, vacationYear);

        return new VacationListResDto(usedVacationCount, vacationListDtoList);
    }

    /**
     * 신청 받은 휴가를 저장하고, 남은 휴가일수를 반환한다.
     *
     * @author joguk
     * @date 2022/02/13 5:30 오후
     */
    public double requestVacation(VacationReqDto vacationReqDto) throws Exception {
        Double usedVacationCount = vacationRepository.retrieveVacationCount(vacationReqDto.getMemberId(), vacationReqDto.getVacationYear());
        vacationReqDto.validateVacationRequest(usedVacationCount);

        Member member = getMember(vacationReqDto);
        VacationRequest vacationRequest = saveVacation(vacationReqDto, member);

        return vacationRequest.getRemainingVacationCount(usedVacationCount);
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

        if (checkVacationDuplicateDate(vacationRequest, vacationReqDto)) {
            vacationRepository.save(vacationRequest);
        }

        return vacationRequest;
    }

    /**
     * 신청 일자에 휴가가 중복 신청된 경우, 메세지와 함께 BusinessException을 발생시킨다.
     *
     * @author joguk
     * @date 2022/02/13 7:48 오후
     */
    private boolean checkVacationDuplicateDate(VacationRequest vacationRequest, VacationReqDto vacationReqDto) throws Exception {
        boolean isDuplicateVacationDate = vacationRepository.retrieveIsDuplicateVacationDate(vacationReqDto.getMemberId(), vacationReqDto.getVacationYear()
                , vacationReqDto.getVacationStartDttm(), vacationReqDto.getVacationEndDttm());

        if (isDuplicateVacationDate) {
            throw new BusinessException(MessageUtils.getMessages("message.vacation.request.fail.duplicate"));    // 신청하신 휴가 일자에 이미 휴가가 신청되어 있습니다.
        }

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
                () -> new NoSuchElementException(MessageUtils.getMessages("message.login.fail.notfound.error")));   // 회원 데이터 없습니다.

        // 취소 상태로 변경
        vacationRequest.cancelVacation();

        return vacationRequest.getId();
    }

    /**
     * 회원 정보를 조회하여 반환한다.
     *
     * @author joguk
     * @date 2022/02/15 10:57 오후
     */
    private Member getMember(VacationReqDto vacationReqDto) {
        Optional<Member> optionalMember = memberRepository.findById(vacationReqDto.getMemberId());
        Member member = optionalMember.orElseThrow(
                () -> new NoSuchElementException(MessageUtils.getMessages("message.login.fail.notfound.error")));   // 회원 데이터 없습니다.
        return member;
    }
}
