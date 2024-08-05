package com.mani.request;

import org.antlr.v4.runtime.misc.NotNull;

public class redirectRequest {
    @NotNull
    private String alias;
    @NotNull
    private String url;

    public redirectRequest() {
    }

    public redirectRequest(final String alias, final String url) {
        this.alias = alias;
        this.url = url;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "redirectRequest{" +
                "alias='" + alias + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
