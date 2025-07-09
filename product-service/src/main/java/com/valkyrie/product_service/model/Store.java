package com.valkyrie.product_service.model;

import org.springframework.http.HttpStatus;

public class Store< Response> {
    private final HttpStatus status;
    private final Response response;

    private Store(HttpStatus status, Response response) {
        this.status = status; this.response = response;
    }

    public static <Response> Store<Response> initialize(HttpStatus status, Response response) {
        return new Store<>(status, response);
    }

    public Response getResponse() {return response;}

    public HttpStatus getStatus() {return status;}
}
