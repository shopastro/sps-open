package com.shopastro.sps.open.sample.gateway.graphql;

import graphql.language.StringValue;
import graphql.scalars.ExtendedScalars;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.server.webflux.GraphiQlHandler;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * @author ye.ly@shopastro-inc.com
 */

@Configuration
public class GraphqlConfiguration {
    @Bean
    @Order(0)
    public RouterFunction<ServerResponse> graphiQlRouterFunction() {
        RouterFunctions.Builder builder = RouterFunctions.route();
        ClassPathResource graphiQlPage = new ClassPathResource("graphiql/index.html");
        GraphiQlHandler graphiQLHandler = new GraphiQlHandler("/graphql", "", graphiQlPage);
        builder = builder.GET("/graphiql", graphiQLHandler::handleRequest);
        return builder.build();
    }
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.DateTime);
    }
//
//    @Bean
//    public GraphQLScalarType graphqlDateTime() {
//        return GraphQLScalarType.newScalar()
//                .name("DateTime")
//                .coercing(new Coercing<LocalDate, String>() {
//                    @Override
//                    public String serialize(final Object dataFetcherResult) {
//                        if (dataFetcherResult instanceof LocalDate) {
//                            return dataFetcherResult.toString();
//                        } else {
//                            throw new CoercingSerializeException("Expected a LocalDate object.");
//                        }
//                    }
//
//                    @Override
//                    public LocalDate parseValue(final Object input) {
//                        try {
//                            if (input instanceof String) {
//                                return LocalDate.parse((String) input);
//                            } else {
//                                throw new CoercingParseValueException("Expected a String");
//                            }
//                        } catch (DateTimeParseException e) {
//                            throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e
//                            );
//                        }
//                    }
//
//                    @Override
//                    public LocalDate parseLiteral(final Object input) throws CoercingParseLiteralException {
//                        if (input instanceof StringValue) {
//                            try {
//                                return LocalDate.parse(((StringValue) input).getValue());
//                            } catch (DateTimeParseException e) {
//                                throw new CoercingParseLiteralException(e);
//                            }
//                        } else {
//                            throw new CoercingParseLiteralException("Expected a StringValue.");
//                        }
//                    }
//                }).build();
//    }
}
