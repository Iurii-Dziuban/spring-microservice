package com.iurii.microservice.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema="WEBSERVICE", name = "USER")
@AllArgsConstructor
@Builder
@Data public class User implements Persistable<String> {

    @Id
    @Column(name = "ID", columnDefinition = "char")
    private String id;

    @Column(name = "NAME", columnDefinition = "char", nullable = false)
    private String name;

    @Column(name = "BIRTH_DATE", columnDefinition = "date", nullable = false)
    private Date birthDate;

    @Transient
    private boolean isNew = false;

    public User() { /* needed by reflection */ }

    public void setNew() {
        isNew = true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
