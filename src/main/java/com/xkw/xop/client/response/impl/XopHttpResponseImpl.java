/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.client.response.impl;

import com.xkw.xop.client.response.XopHttpResponse;
import kong.unirest.Cookies;
import kong.unirest.Headers;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestParsingException;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * XopHttpResponse
 * Xop请求的Http请求响应，包含http响应码、header、body，requestId等
 * @author LiuJibin
 */
public class XopHttpResponseImpl<T> implements XopHttpResponse<T> {

    private final HttpResponse<T> delegate;
    private String requestId;

    public XopHttpResponseImpl(HttpResponse<T> delegate, String requestId) {
        this.delegate = delegate;
        this.requestId = requestId;
    }

    @Override
    public int getStatus() {
        return delegate.getStatus();
    }

    @Override
    public String getStatusText() {
        return delegate.getStatusText();
    }

    @Override
    public Headers getHeaders() {
        return delegate.getHeaders();
    }

    @Override
    public T getBody() {
        return delegate.getBody();
    }

    @Override
    public Optional<UnirestParsingException> getParsingError() {
        return delegate.getParsingError();
    }

    @Override
    public <V> V mapBody(Function<T, V> func) {
        return delegate.mapBody(func);
    }

    @Override
    public <V> HttpResponse<V> map(Function<T, V> func) {
        return delegate.map(func);
    }

    @Override
    public HttpResponse<T> ifSuccess(Consumer<HttpResponse<T>> consumer) {
        return delegate.ifSuccess(consumer);
    }

    @Override
    public HttpResponse<T> ifFailure(Consumer<HttpResponse<T>> consumer) {
        return delegate.ifFailure(consumer);
    }

    @Override
    public <E> HttpResponse<T> ifFailure(Class<? extends E> errorClass, Consumer<HttpResponse<E>> consumer) {
        return delegate.ifFailure(errorClass, consumer);
    }

    @Override
    public boolean isSuccess() {
        return delegate.isSuccess();
    }

    @Override
    public <E> E mapError(Class<? extends E> errorClass) {
        return delegate.mapError(errorClass);
    }

    @Override
    public Cookies getCookies() {
        return delegate.getCookies();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
