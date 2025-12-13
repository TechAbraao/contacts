package org.techabraao.api.contacts.mappers;

import org.techabraao.api.contacts.dto.ContactsDTO;
import org.techabraao.api.contacts.dto.request.ContactsRequest;
import org.techabraao.api.contacts.dto.response.ContactsResponse;
import org.techabraao.api.contacts.entity.ContactsEntity;

public class ContactsMapper {
    /* DTO -> Entity */
    public static ContactsEntity toEntity(ContactsDTO contactDTO) {
        ContactsEntity entity = new ContactsEntity();
        entity.setFullName(contactDTO.fullName());
        entity.setPhone(contactDTO.phone());
        entity.setEmail(contactDTO.email());
        return entity;
    }

    /* Entity -> DTO */
    public static ContactsDTO toDTO(ContactsEntity entity) {
        return new ContactsDTO(
                entity.getFullName(),
                entity.getPhone(),
                entity.getEmail()
        );
    }

    /* Request -> DTO */
    public static ContactsDTO toDTO(ContactsRequest request) {
        return new ContactsDTO(
                request.fullname(),
                request.phone(),
                request.email()
        );
    }

    /* Entity -> Response */
    public static ContactsResponse toResponse(ContactsEntity entity) {
        return new ContactsResponse(
                entity.getId(),
                entity.getFullName(),
                entity.getPhone(),
                entity.getEmail()
        );
    }
}
