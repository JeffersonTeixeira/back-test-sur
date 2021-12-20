package com.example.surittec.desafio.exception;

public class ObsoleteDataEntity extends Exception {

    Object latestVersion;

    public ObsoleteDataEntity(String message, Object latestVersion) {
        super(message);
        this.latestVersion = latestVersion;
    }

    public Object getLatestVersion() {
        return latestVersion;
    }
}
