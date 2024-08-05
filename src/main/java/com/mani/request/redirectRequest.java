package com.mani.request;

import jakarta.persistence.Column;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;

public class redirectRequest {
@NotNull
private String alias;
@NotNull

    private String url;

    public redirectRequest(final  String alias,final String url) {
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
}
