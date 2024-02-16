package com.ishanitech.ipalika.utils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ApprovalStatusUtil {
    UNVERIFIED("unverified"),
    STAMPED("stamped"),
    REGISTERED("registered"),
    PROCESSED("processed"),
    VERIFIED("verified");

    @JsonValue
    private final String statusLevel;

    private ApprovalStatusUtil(String statusLevel) {
        this.statusLevel = statusLevel;
    }

    public String getStatusLevel() {
        return statusLevel;
    }
}
