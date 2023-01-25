package com.oli.HometownPolitician.global.advice;

import com.oli.HometownPolitician.global.error.*;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class GraphqlErrorHandler extends DataFetcherExceptionResolverAdapter {

    private final Map<String, ErrorType> errorTypeMap = new ConcurrentHashMap<>();

    private final Map<ErrorType, Optional<String>> splitPatternMap = new ConcurrentHashMap<>();

    private final Map<ErrorType, Optional<String>> messageMap = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        initializeErrorTypeMap();
        initializeSplitPatternMap();
        initializeMessageMap();
    }

    private void initializeErrorTypeMap() {
        errorTypeMap.put(AccessDeniedException.class.getName(), ErrorType.UNAUTHORIZED);
        errorTypeMap.put(AuthenticationCredentialsNotFoundException.class.getName(), ErrorType.UNAUTHORIZED);
        errorTypeMap.put(ConstraintViolationException.class.getName(), ErrorType.BAD_REQUEST);
        errorTypeMap.put(BadInputError.class.getName(), ErrorType.BAD_REQUEST);
        errorTypeMap.put(FailedError.class.getName(), ErrorType.FORBIDDEN);
        errorTypeMap.put(ExpiredError.class.getName(), ErrorType.FORBIDDEN);
        errorTypeMap.put(UnmatchedError.class.getName(), ErrorType.FORBIDDEN);
        errorTypeMap.put(DuplicatedError.class.getName(), ErrorType.FORBIDDEN);
        errorTypeMap.put(NotFoundError.class.getName(), ErrorType.NOT_FOUND);
        errorTypeMap.put(NullPointerException.class.getName(), ErrorType.INTERNAL_ERROR);
    }

    private void initializeSplitPatternMap() {
        splitPatternMap.put(ErrorType.UNAUTHORIZED, Optional.ofNullable(null));
        splitPatternMap.put(ErrorType.BAD_REQUEST, Optional.ofNullable("\\s*,\\s*"));
        splitPatternMap.put(ErrorType.FORBIDDEN, Optional.ofNullable(null));
        splitPatternMap.put(ErrorType.NOT_FOUND, Optional.ofNullable(null));
        splitPatternMap.put(ErrorType.INTERNAL_ERROR, Optional.ofNullable(null));
    }

    private void initializeMessageMap() {
        messageMap.put(ErrorType.UNAUTHORIZED, Optional.ofNullable("access denied due to not enough authority or expired token"));
        messageMap.put(ErrorType.BAD_REQUEST, Optional.ofNullable(null));
        messageMap.put(ErrorType.FORBIDDEN, Optional.ofNullable(null));
        messageMap.put(ErrorType.NOT_FOUND, Optional.ofNullable(null));
        messageMap.put(ErrorType.INTERNAL_ERROR, Optional.ofNullable("server operation something wrong"));
    }

    @Override
    protected List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env) {
        ErrorType errorType = Optional.ofNullable(errorTypeMap.get(ex.getClass().getName())).orElse(ErrorType.INTERNAL_ERROR);
        Optional<String> splitPattern = splitPatternMap.get(errorType);
        if (ex instanceof GraphQLError) {
            return Arrays
                    .stream(ex.getMessage().split(splitPattern.orElse(UUID.randomUUID().toString())))
                    .map(e -> GraphqlErrorBuilder
                            .newError()
                            .errorType(errorType)
                            .message(messageMap.get(errorType).orElse(e))
                            .path(env.getExecutionStepInfo().getPath())
                            .location(env.getField().getSourceLocation())
                            .extensions(((GraphQLError) ex).getExtensions())
                            .build())
                    .collect(Collectors.toList());
        } else if (errorType != null) {
            return Arrays
                    .stream(ex.getMessage().split(splitPattern.orElse(UUID.randomUUID().toString())))
                    .map(e -> {
                                if (e.contains(":")) {
                                    String[] split = e.split(":");
                                    e = split[1];
                                }
                                return GraphqlErrorBuilder
                                        .newError()
                                        .errorType(errorType)
                                        .message(messageMap.get(errorType).orElse(e.trim()))
                                        .path(env.getExecutionStepInfo().getPath())
                                        .location(env.getField().getSourceLocation())
                                        .build();
                            }
                    )
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

}