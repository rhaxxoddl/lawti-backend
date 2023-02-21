package com.oli.HometownPolitician.domain.proposer.enumeration;

public enum ProposerRole {
    REPRESENTATIVE("대표"),
    PUBLIC("공동");


    private final String value;

    ProposerRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }}
