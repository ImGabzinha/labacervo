package br.edu.unifio.labacervo.recursos;

import br.edu.unifio.labacervo.entidades.Solicitante;
import br.edu.unifio.labacervo.dto.SolicitanteEntrada;
import br.edu.unifio.labacervo.servicos.SolicitanteServico;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitantes")
@RequiredArgsConstructor
public class SolicitanteRecurso {
    private final SolicitanteServico servico;

    @GetMapping
    public List<Solicitante> listar() {
        return servico.listar();
    }

    @GetMapping("/{id}")
    public Solicitante buscar(@PathVariable long id) {
        return servico.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Solicitante> criar(@Valid @RequestBody SolicitanteEntrada dados) {
        var registro = servico.criar(dados);
        return ResponseEntity.created(URI.create("/api/solicitantes/" + registro.getId())).body(registro);
    }

    @PutMapping("/{id}")
    public Solicitante atualizar(@PathVariable long id, @Valid @RequestBody SolicitanteEntrada dados) {
        return servico.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        servico.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
