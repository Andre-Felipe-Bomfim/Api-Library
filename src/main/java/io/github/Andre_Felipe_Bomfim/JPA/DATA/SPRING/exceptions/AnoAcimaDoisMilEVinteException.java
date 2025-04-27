package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.exceptions;

import lombok.Getter;

public class AnoAcimaDoisMilEVinteException extends RuntimeException {

    @Getter
    private String campo;

    public AnoAcimaDoisMilEVinteException(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }
}
