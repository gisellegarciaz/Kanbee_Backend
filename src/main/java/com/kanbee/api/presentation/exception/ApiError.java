package com.kanbee.api.presentation.exception;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class ApiError {
    private int status;
    private String message;
}

// O ApiError é o contrato de resposta da API para erros. Ele vive na camada de presentation
// porque é responsável por como os erros são expostos para o cliente (frontend, mobile, etc.).
// Sua importância está na padronização: independentemente de onde o erro ocorreu (domain ou application),
// a API sempre responde no mesmo formato. Isso facilita o consumo da API, melhora a experiência do frontend
// e deixa o sistema mais profissional e previsível.