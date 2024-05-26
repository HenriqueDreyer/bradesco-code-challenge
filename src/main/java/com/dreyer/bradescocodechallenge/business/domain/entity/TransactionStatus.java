package com.dreyer.bradescocodechallenge.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatus {
    NEGADO(-1),
    AGUARDANDO(0),
    REALIZADO(1),
    EM_ANALISE(2);

    private int value;

    public static TransactionStatus get(final int value) {
        for(TransactionStatus status : TransactionStatus.values()) {
            if(status.getValue() == value) {
                return status;
            }
        }
        return null;
    }

    public static boolean isValid(final int value) {
        for(TransactionStatus status : TransactionStatus.values()) {
            if(status.getValue() == value) {
                return true;
            }
        }
        return false;
    }
}
