package com.oli.HometownPolitician.domain.bill.enumeration;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ProposerKind {
    lawmaker("의원"),
    chairman("위원장"),
    government("정부");

    private final String lable;

    ProposerKind(String lable) {
        this.lable = lable;
    }

    public String getLable() {
        return lable;
    }
    private static final Map<String, ProposerKind> BY_LABEL =
            Stream.of(values())
                    .collect(Collectors.toMap(ProposerKind::getLable, e -> e));
    public static ProposerKind valueOfLable(String lable) {
        ProposerKind proposerKind = BY_LABEL.get(lable);
        if (proposerKind == null)
            throw new EnumConstantNotPresentException(ProposerKind.class, lable);
        return proposerKind;
    }
}
