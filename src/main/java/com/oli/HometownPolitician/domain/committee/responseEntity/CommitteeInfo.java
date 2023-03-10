package com.oli.HometownPolitician.domain.committee.responseEntity;

import lombok.Data;

@Data
public class CommitteeInfo {
    private String cmt_div_cd; // 위원회구분코드
    private String cmt_div_nm; // 위원회구분
    private String hr_dept_cd; // 위원회코드
    private String committee_name; // 위원회먕
    private String hg_nm; // 위원장
    private String hg_nm_list; // 간사
    private int limit_cnt; // 위원정수
    private int curr_cnt; // 현원
    private int poly99_cnt; // 비교섭단체위원수
    private int poly_cnt; // 교섭단체위원수
}
