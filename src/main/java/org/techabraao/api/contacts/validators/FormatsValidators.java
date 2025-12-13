package org.techabraao.api.contacts.validators;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.techabraao.api.contacts.exceptions.InvalidUUIDException;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FormatsValidators {

    private static final Logger logger =
            LoggerFactory.getLogger(FormatsValidators.class);

    public UUID validateUUID(String value) {
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid UUID format: '{}'.", value);
            throw new InvalidUUIDException("Invalid UUID format.");
        }
    }

}
