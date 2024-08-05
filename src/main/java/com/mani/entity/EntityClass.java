package com.mani.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.NaturalId;


@Entity
public class EntityClass {
    @Id
    @GeneratedValue
    private long id;

     @NaturalId
     @Column(unique = true,nullable = false)
     private String alias;
     @Column(nullable = false)
     private String url;

    public EntityClass(long id, String alias, String url) {
        this.id = id;
        this.alias = alias;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

