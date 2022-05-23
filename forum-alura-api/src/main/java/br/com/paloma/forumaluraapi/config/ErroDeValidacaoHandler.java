package br.com.paloma.forumaluraapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    //para pegar erro em diversos idiomas
    @Autowired
    private MessageSource messageSource;


    //para não devolver 200:
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    //para dizer qual tipo de excessão sera tratada:
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormularioDTO> handle(MethodArgumentNotValidException e){
        List<ErroDeFormularioDTO> dto = new ArrayList<>();
        //metodos do erro q acessam a validacao
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        //transformar o fieldErro em dto:
            fieldErrors.forEach(er -> {
                //armazenando a mensagem no idioma local usando o atributo MessageSource:
                String mensagem = messageSource.getMessage(er, LocaleContextHolder.getLocale());
                    ErroDeFormularioDTO erro = new ErroDeFormularioDTO(er.getField(), mensagem);
                    dto.add(erro);
            });
        return dto;
    }
}
