package com.jjozerg.jkhr.common;

import lombok.Getter;

/**
 * packageName : com.jjozerg.jkhr.common
 * fileName : JkHrConstants
 * author : joguk
 * date : 2022/02/12
 * description : JkHrConstants 상수 클래스
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/12 joguk 최초 생성
 * -----------------------------------------------------------
 */
public class JkHrConstants {
    private JkHrConstants() {}

    // 로그인 세션 key String
    public static final String LOGIN_MEMBER = "loginMember";

    /**
     * 회원상태 enum class
     *
     * @author joguk
     * @date 2022/02/13 4:25 오후
     */
    @Getter
    public enum MemberStatus {
        ACTIVATION("1", "활성"),
        SUSPENSION("2", "정지"),
        DORMANT("3", "휴면계정"),
        WITHDRAWAL("4", "탈퇴");

        private String memberStatusCode;
        private String memberStatusName;

        MemberStatus(String memberStatusCode, String memberStatusName) {
            this.memberStatusCode = memberStatusCode;
            this.memberStatusName = memberStatusName;
        }
    }

    /**
     * 휴가 유형 enum class
     *
     * @author joguk
     * @date 2022/02/13 4:25 오후
     */
    @Getter
    public enum VacationKind {
        ALL("1", "연차", 1),
        HALF("2", "반차", 0.5),
        QUARTER("3", "1/4 휴가", 0.25);

        private String vacationKindCode;
        private String vacationKindName;
        private double vacationHolidayQt;

        VacationKind(String vacationKindCode, String vacationKindName, double vacationHolidayQt) {
            this.vacationKindCode = vacationKindCode;
            this.vacationKindName = vacationKindName;
            this.vacationHolidayQt = vacationHolidayQt;
        }

        /**
         * 연차 여부를 반환한다.
         *
         * @author joguk
         * @date 2022/02/14 11:12 오후
         */
        public boolean isAll() {
            return this.equals(ALL);
        }

        /**
         * 반차 여부를 반환한다.
         *
         * @author joguk
         * @date 2022/02/14 11:44 오후
         */
        public boolean isHalf() {
            return this.equals(HALF);
        }

        /**
         * 반반차 여부를 반환한다.
         *
         * @author joguk
         * @date 2022/02/14 11:44 오후
         */
        public boolean isQuarter() {
            return this.equals(QUARTER);
        }
    }

    /**
     * 휴가상태 enum class
     *
     * @author joguk
     * @date 2022/02/13 5:37 오후
     */
    @Getter
    public enum VacationStatus {
        REQUEST("1", "신청"),
        CANCEL("2", "취소");

        private String vacationStatusCode;
        private String vacationStatusName;

        VacationStatus(String vacationStatusCode, String vacationStatusName) {
            this.vacationStatusCode = vacationStatusCode;
            this.vacationStatusName = vacationStatusName;
        }

        public boolean isCancel() {
            return this.equals(CANCEL);
        }
    }
}
