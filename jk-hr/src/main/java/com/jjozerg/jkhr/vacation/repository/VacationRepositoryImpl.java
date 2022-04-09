package com.jjozerg.jkhr.vacation.repository;

import com.jjozerg.jkhr.common.CroquiscomHrConstants;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.jjozerg.jkhr.common.CroquiscomHrConstants.*;
import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationStatus.*;
import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationStatus.CANCEL;
import static com.jjozerg.jkhr.vacation.dto.VacationListResDto.VacationListDto;
import static com.jjozerg.jkhr.vacation.entity.QVacationRequest.vacationRequest;

/**
 * packageName : com.jjozerg.jkhr.vacation.repository
 * fileName : VacationRepositoryImpl
 * author : joguk
 * date : 2022/02/13
 * description : 휴가 Custom Repository Implements
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/13 joguk 최초 생성
 * -----------------------------------------------------------
 */
public class VacationRepositoryImpl implements VacationRepositoryCustom{
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public VacationRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 전달받은 회원ID와 년도에 해당하는 휴가 일수를 조회하여 반환한다.
     *
     * @author joguk
     * @date 2022/02/13 5:30 오후
     */
    public Double retrieveVacationCount(Long memberId, int vactionYear) {
        Double vactionCount = queryFactory
                .select(vacationRequest.vacationCount.sum().coalesce(0.0))
                .from(vacationRequest)
                .where(
                        isEqualMember(memberId),
                        isEqualVacationYear(vactionYear),
                        vacationRequest.vacationStatus.eq(REQUEST)
                )
                .fetchOne();

        return vactionCount;
    }

    /**
     * 신청한 일자에 휴가일자가 중복되는지 확인한다.
     *
     * @author joguk
     * @date 2022/02/14 10:19 오후
     */
    public boolean retrieveIsDuplicateVacationDate(Long memberId, int vactionYear, LocalDateTime vacationStartDttm, LocalDateTime vacationEndDttm) {
        Boolean isDuplicateVactionDate = queryFactory
                .select(vacationRequest.id.count().lt(0))
                .from(vacationRequest)
                .where(
                        isEqualMember(memberId),
                        isEqualVacationYear(vactionYear),
                        isBetweenVacationDttm(vacationStartDttm, vacationEndDttm),
                        vacationRequest.vacationStatus.ne(CANCEL)
                )
                .fetchOne();

        return isDuplicateVactionDate;
    }

    /**
     * 휴가 신청일과 종료일에 중복되는지 반환한다.
     *
     * @author joguk
     * @date 2022/02/14 10:29 오후
     */
    private BooleanExpression isBetweenVacationDttm(LocalDateTime vacationStartDttm, LocalDateTime vacationEndDttm) {
        return (vacationRequest.vacationStartDttm.loe(vacationStartDttm).and(vacationRequest.vacationEndDttm.goe(vacationStartDttm)))
                .or((vacationRequest.vacationStartDttm.loe(vacationEndDttm).and(vacationRequest.vacationEndDttm.goe(vacationEndDttm))));
    }

    /**
     * 전달받은 회원ID와 년도의 휴가 목록을 조회하여 반환한다.
     *
     * @author joguk
     * @date 2022/02/13 5:30 오후
     */
    public List<VacationListDto> retrieveVacationList(Long memberId, int vactionYear) {
        List<VacationListDto> vacations = queryFactory
                .select(Projections.fields(VacationListDto.class,
                        vacationRequest.id.as("vacationReqId"),
                        vacationRequest.vacationCount,
                        vacationRequest.vacationYear,
                        vacationRequest.vacationStatus,
                        vacationRequest.vacationKind,
                        vacationRequest.vacationStartDttm,
                        vacationRequest.vacationEndDttm,
                        vacationRequest.note))
                .from(vacationRequest)
                .where(
                        isEqualMember(memberId),
                        isEqualVacationYear(vactionYear)
                )
                .fetch();

        return vacations;
    }

    /**
     * 휴가년도 일치여부를 반환한다.
     *
     * @author joguk
     * @date 2022/02/13 5:31 오후
     */
    private BooleanExpression isEqualVacationYear(int vactionYear) {
        return vacationRequest.vacationYear.eq(vactionYear);
    }

    /**
     * 회원ID 일치여부를 반환한다.
     *
     * @author joguk
     * @date 2022/02/13 5:31 오후
     */
    private BooleanExpression isEqualMember(Long memberId) {
        return vacationRequest.member().memberId.eq(memberId);
    }
}
