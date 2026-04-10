package com.kanbee.api.application.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException userNotFound() {
        return new ResourceNotFoundException("Usuário não encontrado.");
    }
}

// A ResourceNotFoundException representa a ausência de dados necessários para
// executar um caso de uso, como um usuário que não foi encontrado. Ela fica na
// camada de application porque está ligada ao fluxo da aplicação (use cases), e não
// exatamente a uma regra de negócio pura. Essa separação ajuda a deixar claro que
// o problema não é uma violação de regra, mas sim um estado esperado do sistema (ex: buscar algo que não existe).