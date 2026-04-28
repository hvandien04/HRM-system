package com.example.hrmsystem.infrastructure.persistence.identity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "role", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "role_code_key", columnNames = {"code"})
})
public class Role {
    @Id
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "code", nullable = false, length = Integer.MAX_VALUE)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "role")
    private List<User> users = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID();
    }
}