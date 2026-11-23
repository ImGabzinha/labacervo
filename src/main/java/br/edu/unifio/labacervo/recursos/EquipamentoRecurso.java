package br.edu.unifio.labacervo.recursos;

import br.edu.unifio.labacervo.entidades.Equipamento;
import br.edu.unifio.labacervo.dto.EquipamentoEntrada;
import br.edu.unifio.labacervo.servicos.EquipamentoServico;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipamentos")
@RequiredArgsConstructor
public class EquipamentoRecurso {
    private final EquipamentoServico servico;

    @GetMapping
    public List<Equipamento> listar() {
        return servico.listar();
    }

    @GetMapping("/{id}")
    public Equipamento buscar(@PathVariable long id) {
        return servico.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Equipamento> criar(@Valid @RequestBody EquipamentoEntrada dados) {
        var registro = servico.criar(dados);
        return ResponseEntity.created(URI.create("/api/equipamentos/" + registro.getId())).body(registro);
    }

    @PutMapping("/{id}")
    public Equipamento atualizar(@PathVariable long id, @Valid @RequestBody EquipamentoEntrada dados) {
        return servico.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        servico.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponiveis")
    public List<Equipamento> disponiveis(@RequestParam long laboratorioId) {
        return servico.disponiveis(laboratorioId);
    }
}
