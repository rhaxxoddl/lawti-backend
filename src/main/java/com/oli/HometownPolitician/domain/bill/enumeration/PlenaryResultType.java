package com.oli.HometownPolitician.domain.bill.enumeration;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PlenaryResultType {
    PASS("가결"),
    CORRECTION_PASS("수정가결"),
    ORIGINAL_PASS("원안가결"),
    REJECT("부결"),
    ALTERNATIVE_DISCARD("대안반영폐기"),
    DISCARD("폐기"),
    EXPIRED_DISCARD("임기만료폐기"),
    END_OF_SESSION_DISCARD("회기불계속폐기"),
    WITHDRAWAL("철회"),
    PENDING("계류");

    private final String lable;

    PlenaryResultType(String lable) {
        this.lable = lable;
    }

    public String getLable() {
        return lable;
    }
    private static final Map<String, PlenaryResultType> BY_LABEL =
            Stream.of(values())
                    .collect(Collectors.toMap(PlenaryResultType::getLable, e -> e));
    public static PlenaryResultType valueOfLable(String lable) {
        if (lable == null || lable.isEmpty())
            return null;
        PlenaryResultType plenaryResultType = BY_LABEL.get(lable);
        if (plenaryResultType == null)
            throw new EnumConstantNotPresentException(ProposerKind.class, lable);
        return plenaryResultType;
    }
}
