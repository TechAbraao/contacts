package org.techabraao.api.contacts.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "contacts", schema = "public")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ContactsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false)
    private Long phone;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity user;
}
