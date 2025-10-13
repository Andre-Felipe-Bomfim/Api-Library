package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller.common;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.ErroResposta;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.ErrosCampo;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.exceptions.AnoAcimaDoisMilEVinteException;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.exceptions.OperacaoNaoPermitidaException;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice//captura exceptions e retorna respotas de erro e validação é comum com erros
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)//serve para mapear o erro no caso sem isso ele retorna codigo 200 mas queremos 422
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)//faz a captura do erro e joga na classe
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrosCampo> ListaErros = fieldErrors
                .stream()
                .map(fe -> new ErrosCampo
                        (fe.getField(),
                                fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                ListaErros);
    }

    @ExceptionHandler (RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroCuplicadoException (RegistroDuplicadoException e){
        return ErroResposta.conflito(e.getMessage());//conflito = registro duplicado
    }

    @ExceptionHandler(AnoAcimaDoisMilEVinteException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleAnoAcimaDoisMilEVinteException(AnoAcimaDoisMilEVinteException e){
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação!",
                List.of(new ErrosCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErroResposta.respostapadrao(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAccesDeniedException(AccessDeniedException e){
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Acesso Negado", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e){
        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro um erro inesperado. Entre em contato com o suporte!",
                List.of());
    }
}
