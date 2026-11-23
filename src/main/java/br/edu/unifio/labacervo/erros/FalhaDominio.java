package br.edu.unifio.labacervo.erros;

import org.springframework.http.HttpStatus;

public class FalhaDominio extends RuntimeException {
    public final HttpStatus status;
    private FalhaDominio(HttpStatus status, String mensagem) {
        super(mensagem);
        this.status = status;
    }

    public static FalhaDominio ausente(String tipo, long id) {
        return new FalhaDominio(HttpStatus.NOT_FOUND, tipo + " de ID " + id + " não encontrado(a).");
    }

    public static FalhaDominio conflito(String mensagem) {
        return new FalhaDominio(HttpStatus.CONFLICT, mensagem);
    }
}
