package com.example.hrmsystem.infrastructure.persistence.identity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "permission", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "permission_code_key", columnNames = {"code"})
})
public class Permission {
    @Id
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "code", nullable = false, length = Integer.MAX_VALUE)
    private String code;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new LinkedHashSet<>();

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID();
    }
}