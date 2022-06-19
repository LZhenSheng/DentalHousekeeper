//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

/**
 *
 */
module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;
    requires lombok;
    requires dcm4che.core;
    requires unirest.java;
    requires com.google.gson;
    requires java.sql;
    requires json;
    requires aliyun.sdk.oss;
    requires org.joda.time;
    requires java.desktop;
    requires opencv;
    requires javafx.web;
    requires com.jfoenix;
    requires dcm4che.imageio;
    exports com.example.demo.domain;
    exports com.example.demo;
    opens com.example.demo to
            javafx.fxml;
    opens com.example.demo.api to
            javafx.fxml;
    opens com.example.demo.domain to
            com.google.gson;
}
