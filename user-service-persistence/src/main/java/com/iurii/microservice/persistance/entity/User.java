package com.iurii.microservice.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(schema="WEBSERVICE", name = "USER")
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class User implements Persistable<String> {

    @Id
    @Column(name = "ID", columnDefinition = "char")
    private String id;

    @Column(name = "NAME", columnDefinition = "char", nullable = false)
    private String name;

    @Column(name = "BIRTH_DATE", columnDefinition = "date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "UPDATED_TIME", columnDefinition = "timestamp", nullable = false)
    private ZonedDateTime updatedTime;

    @Column(name = "MONEY", columnDefinition = "decimal", nullable = false)
    private long money;

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
