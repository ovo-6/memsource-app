package com.ovo6.memsource.model;

import java.util.Set;

public class Project {

    private String name;
    private String status;
    private String sourceLang;
    private Set<String> targetLangs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public Set<String> getTargetLangs() {
        return targetLangs;
    }

    public void setTargetLangs(Set<String> targetLangs) {
        this.targetLangs = targetLangs;
    }
}
