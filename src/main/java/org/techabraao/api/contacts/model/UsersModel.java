package org.techabraao.api.contacts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "users", schema = "public")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class UsersModel {}
