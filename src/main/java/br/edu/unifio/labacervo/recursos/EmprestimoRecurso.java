package br.edu.unifio.labacervo.recursos;

import br.edu.unifio.labacervo.entidades.Emprestimo;
import br.edu.unifio.labacervo.dto.EmprestimoEntrada;
import br.edu.unifio.labacervo.servicos.EmprestimoServico;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emprestimos")
@RequiredArgsConstructor
public class EmprestimoRecurso {
    private final EmprestimoServico servico;

    @GetMapping
    public List<Emprestimo> listar() {
        return servico.listar();
    }

    @GetMapping("/{id}")
    public Emprestimo buscar(@PathVariable long id) {
        return servico.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Emprestimo> criar(@Valid @RequestBody EmprestimoEntrada dados) {
        var registro = servico.criar(dados);
        return ResponseEntity.created(URI.create("/api/emprestimos/" + registro.getId())).body(registro);
    }

    @PutMapping("/{id}")
    public Emprestimo atualizar(@PathVariable long id, @Valid @RequestBody EmprestimoEntrada dados) {
        return servico.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        servico.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/historico")
    public List<Emprestimo> historico(@RequestParam long solicitanteId) {
        return servico.historico(solicitanteId);
    }

    @PostMapping("/{id}/devolucao")
    public Emprestimo devolver(@PathVariable long id) {
        return servico.devolver(id);
    }
}
