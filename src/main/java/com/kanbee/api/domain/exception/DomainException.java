package com.kanbee.api.domain.exception;

public abstract class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}

// A DomainException funciona como uma classe base para todas as exceções relacionadas ao domínio
// da aplicação, ou seja, regras de negócio. Ela centraliza esse tipo de erro em um único ponto,
// deixando explícito que qualquer exceção que herda dela representa uma violação de regra do sistema
// (como “email já em uso”). Isso ajuda a organizar o código, reforça a separação de responsabilidades
// do DDD e facilita manutenção e leitura, especialmente conforme o sistema cresce.