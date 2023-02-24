package com.oli.HometownPolitician.domain.bill.enumeration;

public enum LawtiBillStageType {
    RECEIPT("접수"),
    COMMITTEE("소관위"),
    JUDICIAL_COMMITTEE("법사위"),
    MAIN_SESSION("본회의"),
    PROMULGATION("공포"),
    ALTERNATIVE_DISCARD("대안반영폐기"),
    DISCARD("폐기"); // 본회의불부의, 철수 포함

    private final String value;

    LawtiBillStageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
