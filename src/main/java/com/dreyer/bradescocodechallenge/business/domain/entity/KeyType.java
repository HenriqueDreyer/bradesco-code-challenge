package com.dreyer.bradescocodechallenge.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyType {
    PHONE(1),
    EMAIL(2),
    CPF(3),
    CNPJ(4),
    OTHER(5);

    private int value;

    public static KeyType get(final int value) {
        for(KeyType keyType : KeyType.values()) {
            if(keyType.getValue() == value) {
                return keyType;
            }
        }
        return null;
    }

    public static boolean isValid(final int value) {
        for(KeyType keyType : KeyType.values()) {
            if(keyType.getValue() == value) {
                return true;
            }
        }
        return false;
    }
}
