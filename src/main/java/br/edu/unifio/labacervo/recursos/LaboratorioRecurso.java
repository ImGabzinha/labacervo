package br.edu.unifio.labacervo.recursos;

import br.edu.unifio.labacervo.entidades.Laboratorio;
import br.edu.unifio.labacervo.dto.LaboratorioEntrada;
import br.edu.unifio.labacervo.servicos.LaboratorioServico;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/laboratorios")
@RequiredArgsConstructor
public class LaboratorioRecurso {
    private final LaboratorioServico servico;

    @GetMapping
    public List<Laboratorio> listar() {
        return servico.listar();
    }

    @GetMapping("/{id}")
    public Laboratorio buscar(@PathVariable long id) {
        return servico.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Laboratorio> criar(@Valid @RequestBody LaboratorioEntrada dados) {
        var registro = servico.criar(dados);
        return ResponseEntity.created(URI.create("/api/laboratorios/" + registro.getId())).body(registro);
    }

    @PutMapping("/{id}")
    public Laboratorio atualizar(@PathVariable long id, @Valid @RequestBody LaboratorioEntrada dados) {
        return servico.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        servico.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
