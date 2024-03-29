package com.oli.HometownPolitician.global.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.scalars.ExtendedScalars;
import graphql.schema.*;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.graphql.ExecutionGraphQlRequest;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.Assert;
import org.springframework.util.IdGenerator;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.function.*;
import reactor.core.publisher.Mono;

import javax.servlet.ServletException;
import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;

import static com.oli.HometownPolitician.global.config.GraphqlConfig.GraphqlMultipartHandler.SUPPORTED_RESPONSE_MEDIA_TYPES;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@Configuration
public class GraphqlConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        GraphQLScalarType nullType = defineNullScalarType();
        GraphQLScalarType uploadType = defineUploadScalarType();
        return wiringBuilder ->
                wiringBuilder
                        .scalar(nullType)
                        .scalar(uploadType)
                        .scalar(ExtendedScalars.Date)
                        .scalar(ExtendedScalars.GraphQLByte)
                        .scalar(ExtendedScalars.GraphQLLong)
                        .scalar(ExtendedScalars.GraphQLShort)
                        .scalar(ExtendedScalars.GraphQLChar)
                        .scalar(ExtendedScalars.GraphQLBigDecimal)
                        .scalar(ExtendedScalars.GraphQLBigInteger);
    }

    private GraphQLScalarType defineNullScalarType() {
        return GraphQLScalarType
                .newScalar()
                .name("Null")
                .description("Nothing to Return")
                .coercing(new Coercing<>() {

                    @Override
                    public Object serialize(final Object dataFetcherResult) {
                        return null;
                    }

                    @Override
                    public Object parseValue(Object input) throws CoercingParseValueException {
                        return null;
                    }

                    @Override
                    public Object parseLiteral(Object input) throws CoercingParseLiteralException {
                        return null;
                    }

                })
                .build();
    }

    private GraphQLScalarType defineUploadScalarType() {
        return GraphQLScalarType
                .newScalar()
                .name("Upload")
                .description("Multipart File to Upload")
                .coercing(new Coercing<MultipartFile, MultipartFile>() {

                    @Override
                    public MultipartFile serialize(Object dataFetcherResult) throws CoercingSerializeException {
                        throw new CoercingSerializeException("Upload is an input-only type");
                    }

                    @Override
                    public MultipartFile parseValue(Object input) throws CoercingParseValueException {
                        if (input instanceof MultipartFile) {
                            return (MultipartFile) input;
                        }
                        throw new CoercingParseValueException(
                                String.format("Expected a 'MultipartFile' like object but was '%s'.", input != null ? input.getClass() : null)
                        );
                    }

                    @Override
                    public MultipartFile parseLiteral(Object input) throws CoercingParseLiteralException {
                        throw new CoercingParseLiteralException("Parsing literal of 'MultipartFile' is not supported");
                    }

                })
                .build();
    }

    @Bean
    @Order(1)
    public RouterFunction<ServerResponse> graphQlMultipartRouterFunction(
            GraphQlProperties properties,
            WebGraphQlHandler webGraphQlHandler,
            ObjectMapper objectMapper
    ) {
        String path = properties.getPath();
        RouterFunctions.Builder builder = RouterFunctions.route();
        GraphqlMultipartHandler graphqlMultipartHandler = new GraphqlMultipartHandler(webGraphQlHandler, objectMapper);
        builder = builder.POST(path, RequestPredicates.contentType(MULTIPART_FORM_DATA)
                .and(RequestPredicates.accept(SUPPORTED_RESPONSE_MEDIA_TYPES.toArray(MediaType[]::new))), graphqlMultipartHandler::handleRequest);
        return builder.build();
    }

    public class GraphqlMultipartHandler {

        private final WebGraphQlHandler graphQlHandler;

        private final ObjectMapper objectMapper;

        public GraphqlMultipartHandler(WebGraphQlHandler graphQlHandler, ObjectMapper objectMapper) {
            Assert.notNull(graphQlHandler, "WebGraphQlHandler is required");
            Assert.notNull(objectMapper, "ObjectMapper is required");
            this.graphQlHandler = graphQlHandler;
            this.objectMapper = objectMapper;
        }

        public static final List<MediaType> SUPPORTED_RESPONSE_MEDIA_TYPES =
                Arrays.asList(MediaType.APPLICATION_GRAPHQL, MediaType.APPLICATION_JSON);

        private final IdGenerator idGenerator = new AlternativeJdkIdGenerator();

        public ServerResponse handleRequest(ServerRequest serverRequest) throws ServletException {
            Optional<String> operation = serverRequest.param("operations");
            Optional<String> mapParam = serverRequest.param("map");
            Map<String, Object> inputQuery = readJson(operation, new TypeReference<>() {
            });
            final Map<String, Object> queryVariables;
            if (inputQuery.containsKey("variables")) {
                queryVariables = (Map<String, Object>) inputQuery.get("variables");
            } else {
                queryVariables = new HashMap<>();
            }
            Map<String, Object> extensions = new HashMap<>();
            if (inputQuery.containsKey("extensions")) {
                extensions = (Map<String, Object>) inputQuery.get("extensions");
            }
            Map<String, MultipartFile> fileParams = readMultipartBody(serverRequest);
            Map<String, List<String>> fileMapInput = readJson(mapParam, new TypeReference<>() {
            });
            fileMapInput.forEach((String fileKey, List<String> objectPaths) -> {
                MultipartFile file = fileParams.get(fileKey);
                if (file != null) {
                    objectPaths.forEach((String objectPath) -> {
                        MultipartVariableMapper.mapVariable(
                                objectPath,
                                queryVariables,
                                file
                        );
                    });
                }
            });
            String query = (String) inputQuery.get("query");
            String opName = (String) inputQuery.get("operationName");
            WebGraphQlRequest graphQlRequest = new MultipartGraphQlRequest(
                    query,
                    opName,
                    queryVariables,
                    extensions,
                    serverRequest.uri(), serverRequest.headers().asHttpHeaders(),
                    this.idGenerator.generateId().toString(), LocaleContextHolder.getLocale());
            Mono<ServerResponse> responseMono = this.graphQlHandler.handleRequest(graphQlRequest)
                    .map(response -> {
                        ServerResponse.BodyBuilder builder = ServerResponse.ok();
                        builder.headers(headers -> headers.putAll(response.getResponseHeaders()));
                        builder.contentType(selectResponseMediaType(serverRequest));
                        return builder.body(response.toMap());
                    });
            return ServerResponse.async(responseMono);
        }

        private <T> T readJson(Optional<String> string, TypeReference<T> t) {
            Map<String, Object> map = new HashMap<>();
            if (string.isPresent()) {
                try {
                    return objectMapper.readValue(string.get(), t);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            return (T) map;
        }

        private static Map<String, MultipartFile> readMultipartBody(ServerRequest request) {
            try {
                AbstractMultipartHttpServletRequest abstractMultipartHttpServletRequest = (AbstractMultipartHttpServletRequest) request.servletRequest();
                return abstractMultipartHttpServletRequest.getFileMap();
            } catch (RuntimeException ex) {
                throw new ServerWebInputException("Error while reading request parts", null, ex);
            }
        }

        private static MediaType selectResponseMediaType(ServerRequest serverRequest) {
            for (MediaType accepted : serverRequest.headers().accept()) {
                if (SUPPORTED_RESPONSE_MEDIA_TYPES.contains(accepted)) {
                    return accepted;
                }
            }
            return MediaType.APPLICATION_JSON;
        }

    }

    private class MultipartVariableMapper {

        private static final Pattern PERIOD = Pattern.compile("\\.");

        private static final Mapper<Map<String, Object>> MAP_MAPPER =
                new Mapper<Map<String, Object>>() {
                    @Override
                    public Object set(Map<String, Object> location, String target, MultipartFile value) {
                        return location.put(target, value);
                    }

                    @Override
                    public Object recurse(Map<String, Object> location, String target) {
                        return location.get(target);
                    }
                };

        private static final Mapper<List<Object>> LIST_MAPPER =
                new Mapper<List<Object>>() {

                    @Override
                    public Object set(List<Object> location, String target, MultipartFile value) {
                        return location.set(Integer.parseInt(target), value);
                    }

                    @Override
                    public Object recurse(List<Object> location, String target) {
                        return location.get(Integer.parseInt(target));
                    }

                };

        @SuppressWarnings({"unchecked", "rawtypes"})
        public static void mapVariable(String objectPath, Map<String, Object> variables, MultipartFile part) {
            String[] segments = PERIOD.split(objectPath);
            if (segments.length < 2) {
                throw new RuntimeException("object-path in map must have at least two segments");
            } else if (!"variables".equals(segments[0])) {
                throw new RuntimeException("can only map into variables");
            }
            Object currentLocation = variables;
            for (int i = 1; i < segments.length; i++) {
                String segmentName = segments[i];
                Mapper mapper = determineMapper(currentLocation, objectPath, segmentName);
                if (i == segments.length - 1) {
                    if (null != mapper.set(currentLocation, segmentName, part)) {
                        throw new RuntimeException("expected null value when mapping " + objectPath);
                    }
                } else {
                    currentLocation = mapper.recurse(currentLocation, segmentName);
                    if (null == currentLocation) {
                        throw new RuntimeException(
                                "found null intermediate value when trying to map " + objectPath
                        );
                    }
                }
            }
        }

        private static Mapper<?> determineMapper(
                Object currentLocation, String objectPath, String segmentName
        ) {
            if (currentLocation instanceof Map) {
                return MAP_MAPPER;
            } else if (currentLocation instanceof List) {
                return LIST_MAPPER;
            }
            throw new RuntimeException(
                    "expected a map or list at " + segmentName + " when trying to map " + objectPath);
        }

        interface Mapper<T> {

            Object set(T location, String target, MultipartFile value);

            Object recurse(T location, String target);

        }

    }

    private class MultipartGraphQlRequest extends WebGraphQlRequest implements ExecutionGraphQlRequest {

        private final String document;

        private final String operationName;

        private final Map<String, Object> variables;

        private final Map<String, Object> extensions;

        public MultipartGraphQlRequest(
                String query,
                String operationName,
                Map<String, Object> variables,
                Map<String, Object> extensions,
                URI uri,
                HttpHeaders headers,
                String id,
                @Nullable Locale locale) {
            super(uri, headers, fakeBody(query), id, locale);
            this.document = query;
            this.operationName = operationName;
            this.variables = variables;
            this.extensions = extensions;
        }

        private static Map<String, Object> fakeBody(String query) {
            Map<String, Object> fakeBody = new HashMap<>();
            fakeBody.put("query", query);
            return fakeBody;
        }

        @Override
        public String getDocument() {
            return document;
        }

        @Override
        public String getOperationName() {
            return operationName;
        }

        @Override
        public Map<String, Object> getVariables() {
            return variables;
        }

        @Override
        public Map<String, Object> getExtensions() {
            return extensions;
        }

    }

}
