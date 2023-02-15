package com.oli.HometownPolitician.domain.bill.enumeration;

public enum PlenaryResultType {
    PASS("가결"),
    REJECT("부결"),
    ALTERNATIVE_DISCARD("대안반영폐기"),
    DISCARD("폐기"),
    EXPIRED_DISCARD("임기만료폐기"),
    WITHDRAWAL("철회"),
    PENDING("계류");

    private final String value;

    PlenaryResultType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
