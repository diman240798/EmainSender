package com.nanicky.emailsender.mail.prop;

public enum PortType {
    TLS(25), SSL_465(465), SSL_587(587);

    public final int port;

    PortType(int port) {
        this.port = port;
    }
}
