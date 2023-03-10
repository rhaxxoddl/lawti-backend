package com.oli.HometownPolitician.domain.bill.enumeration;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BillStageType {
    RECEIPT("접수"),
    COMMITTEE_RECEIPT("소관위접수"),
    COMMITTEE_REVIEW("소관위심사"),
    SYSTEMATIC_REVIEW("체계자구심사"), // 법사위 심사
    MAIN_SESSION_AGENDA("본회의부의안건"),
    PROMULGATION("공포"),
    MAIN_SESSION_REJECTION("본회의불부의"),
    ALTERNATIVE_DISCARD("대안반영폐기"),
    DISCARD("폐기"),
    WITHDRAWAL("철수");

    private final String lable;

    BillStageType(String value) {
        this.lable = value;
    }

    public String getLable() {
        return lable;
    }
    private static final Map<String, BillStageType> BY_LABEL =
            Stream.of(values())
                    .collect(Collectors.toMap(BillStageType::getLable, e -> e));
    public static BillStageType valueOfLable(String lable) {
        return BY_LABEL.get(lable);
    }
}
