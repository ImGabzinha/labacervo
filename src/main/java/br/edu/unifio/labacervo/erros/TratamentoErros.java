package br.edu.unifio.labacervo.erros;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class TratamentoErros {
    @ExceptionHandler(FalhaDominio.class)
    public ResponseEntity<?> dominio(FalhaDominio erro) {
        return ResponseEntity.status(erro.status).body(Map.of("mensagem", erro.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validacao(MethodArgumentNotValidException erro) {
        var campos = new TreeMap<String, String>();
        erro.getBindingResult().getFieldErrors().forEach(e -> campos.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Confira os campos informados.", "campos", campos));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> jsonInvalido() {
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Confira o JSON e o formato dos valores enviados."));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> integridade() {
        return ResponseEntity.status(409).body(Map.of("mensagem", "Existe um valor único repetido ou o registro possui vínculos que impedem a operação."));
    }
}
