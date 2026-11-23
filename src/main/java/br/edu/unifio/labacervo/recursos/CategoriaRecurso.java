package br.edu.unifio.labacervo.recursos;

import br.edu.unifio.labacervo.entidades.Categoria;
import br.edu.unifio.labacervo.dto.CategoriaEntrada;
import br.edu.unifio.labacervo.servicos.CategoriaServico;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaRecurso {
    private final CategoriaServico servico;

    @GetMapping
    public List<Categoria> listar() {
        return servico.listar();
    }

    @GetMapping("/{id}")
    public Categoria buscar(@PathVariable long id) {
        return servico.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@Valid @RequestBody CategoriaEntrada dados) {
        var registro = servico.criar(dados);
        return ResponseEntity.created(URI.create("/api/categorias/" + registro.getId())).body(registro);
    }

    @PutMapping("/{id}")
    public Categoria atualizar(@PathVariable long id, @Valid @RequestBody CategoriaEntrada dados) {
        return servico.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        servico.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
