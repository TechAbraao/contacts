package org.techabraao.api.contacts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.UUID;

@Table(name = "contacts", schema = "public")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ContactsModel {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    UUID id;

    @Column(name = "fullname", nullable = false)
    String fullName;

    @Column(name = "phone", unique = true, nullable = false)
    Long phone;

    @Column(name = "email", unique = true, nullable = false)
    String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersModel user;
}
