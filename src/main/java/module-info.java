open module co.edu.upb.inventarioquimicos {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.security.crypto;
    requires spring.tx;
    requires spring.jdbc;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires static lombok;

    exports co.edu.upb.inventarioquimicos;
    exports co.edu.upb.inventarioquimicos.controladores;
}