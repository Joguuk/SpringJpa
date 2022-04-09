package com.jjozerg.jkhr.vacation.repository;

import com.jjozerg.jkhr.vacation.entity.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : com.jjozerg.jkhr.vacation.repository
 * fileName : VacationRepository
 * author : joguk
 * date : 2022/02/13
 * description : 휴가 Repository
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/13 joguk 최초 생성
 * -----------------------------------------------------------
 */

public interface VacationRepository extends JpaRepository<VacationRequest, Long>, VacationRepositoryCustom{
}
