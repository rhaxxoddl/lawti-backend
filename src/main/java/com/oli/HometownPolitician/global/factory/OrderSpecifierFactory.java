package com.oli.HometownPolitician.global.factory;


import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderSpecifierFactory {

    static public OrderSpecifier<?> from(TargetSlicePaginationInput pagination, PathBuilder path, String property) {
        if (pagination == null) {
            return null;
        }
        return from(pagination.getIsAscending(), path, property);
    }

    static public OrderSpecifier<?> from(Boolean isAscending, PathBuilder path, String property) {
        return new OrderSpecifier<>(
                isAscending ? Order.ASC : Order.DESC,
                getExpression(path, property)
        );
    }

    static private PathBuilder getExpression(PathBuilder<?> path, String property) {
        switch (property) {
            case "id":
                return path.get(property, Long.class);
            case "createdAt":
                return path.get(property, LocalDateTime.class);
            case "updatedAt":
                return path.get(property, LocalDateTime.class);
            case "proposeDate":
                return path.get(property, LocalDate.class);
            case "followerCount":
                return path.get(property, Integer.class);
        }
        return null;
    }

}
