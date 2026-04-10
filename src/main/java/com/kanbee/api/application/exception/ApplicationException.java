package com.kanbee.api.application.exception;

public abstract class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }
}

// A ApplicationException é a base para exceções da camada de aplicação, ligadas à execução dos casos
// de uso (use cases). Diferente do domínio, aqui os erros representam problemas no fluxo da aplicação,
// como recursos não encontrados ou operações inválidas dentro de um contexto específico. Ter essa classe
// base permite padronizar esse tipo de erro, manter a arquitetura organizada e facilitar o tratamento
// centralizado no GlobalExceptionHandler.