package com.oli.HometownPolitician.domain.politician.repository;

import com.querydsl.core.types.dsl.BooleanExpression;

import static com.oli.HometownPolitician.domain.politician.entity.QPolitician.politician;

public class PoliticianRepositoryCond {
    public BooleanExpression eqChineseName(String chineseName) {
        if (chineseName == null)
            return null;
        return politician.chineseName.eq(chineseName);
    }
}
