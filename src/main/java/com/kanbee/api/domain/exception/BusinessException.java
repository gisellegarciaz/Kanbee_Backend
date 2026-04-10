package com.kanbee.api.domain.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public static BusinessException emailAlreadyInUse() {
        return new BusinessException("Email já está em uso.");
    }
}

//A BusinessException representa violações de regras de negócio, ou seja, situações
// que fazem sentido dentro do domínio da aplicação, mas não podem acontecer segundo
// as regras definidas (ex: “email já está em uso”). Ela vive no domain porque independe
// de tecnologia, HTTP ou framework — é pura regra de negócio.
// Isso mantém o domínio isolado e reutilizável, além de deixar claro que o erro não é técnico,
// mas sim uma decisão do sistema baseada nas regras.