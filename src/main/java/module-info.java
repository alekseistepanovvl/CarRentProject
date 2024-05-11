module com.rent.carrent {
    requires lombok;
    requires spring.context;
    requires spring.tx;
    requires spring.data.jpa;
    requires java.persistence;
    requires org.mapstruct;
    requires io.swagger.v3.oas.annotations;
    requires org.hibernate.orm.core;
    requires java.validation;
    requires liquibase.core;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires spring.data.commons;
    requires spring.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires com.zaxxer.hikari;
    requires spring.beans;
    requires spring.core;
    requires org.hsqldb;
    requires spring.aop;
    requires org.apache.tomcat.embed.core;
    requires io.github.classgraph;

    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires jdk.unsupported;

    requires java.xml.bind;
    requires jakarta.activation;

    opens com.rent.carrent to spring.core, spring.beans, spring.context;
    opens com.rent.carrent.config to spring.core, spring.beans, spring.context, liquibase.core;
    opens db.changelog;

    opens com.rent.carrent.model to org.hibernate.orm.core, spring.core;
    opens com.rent.carrent.mapper to spring.beans, spring.core, spring.context, com.fasterxml.jackson.databind;

    opens com.rent.carrent.service.car to spring.beans, spring.core;
    opens com.rent.carrent.service.reservation to spring.beans, spring.core;
    opens com.rent.carrent.service.user to spring.beans, spring.core;
    opens com.rent.carrent.controller to spring.beans, spring.core;
    opens com.rent.carrent.exception to spring.beans;

    exports com.rent.carrent;
    exports com.rent.carrent.dto.user to com.fasterxml.jackson.databind;
    exports com.rent.carrent.dto.car to com.fasterxml.jackson.databind;
    exports com.rent.carrent.dto.reservation to com.fasterxml.jackson.databind;
    exports com.rent.carrent.dto.error to com.fasterxml.jackson.databind;

    exports com.rent.carrent.exception to spring.web;
    exports com.rent.carrent.controller to spring.web;
}
