package br.edu.unifio.labacervo.servicos;

import br.edu.unifio.labacervo.entidades.*;
import br.edu.unifio.labacervo.dto.EquipamentoEntrada;
import br.edu.unifio.labacervo.repositorios.*;
import br.edu.unifio.labacervo.erros.FalhaDominio;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipamentoServico {
    private final EquipamentoRepositorio repositorio;
    private final CategoriaRepositorio categorias;
    private final LaboratorioRepositorio laboratorios;

    public List<Equipamento> listar() {
        return repositorio.findAll();
    }

    public Equipamento buscar(long id) {
        return repositorio.findById(id).orElseThrow(() -> FalhaDominio.ausente("Equipamento", id));
    }

    @Transactional
    public Equipamento criar(EquipamentoEntrada dados) {
        var registro = new Equipamento();
        aplicar(registro, dados);
        return repositorio.save(registro);
    }

    @Transactional
    public Equipamento atualizar(long id, EquipamentoEntrada dados) {
        var registro = buscar(id);
        aplicar(registro, dados);
        return registro;
    }

    @Transactional
    public void excluir(long id) {
        var registro = buscar(id);
        repositorio.delete(registro);
        repositorio.flush();
    }

    private void aplicar(Equipamento registro, EquipamentoEntrada dados) {
        registro.setNome(dados.nome().strip());
        registro.setPatrimonio(dados.patrimonio().strip());
        registro.setCategoria(categorias.findById(dados.categoriaId()).orElseThrow(() -> FalhaDominio.ausente("Categoria", dados.categoriaId())));
        registro.setLaboratorio(laboratorios.findById(dados.laboratorioId()).orElseThrow(() -> FalhaDominio.ausente("Laboratório", dados.laboratorioId())));
    }

    public List<Equipamento> disponiveis(long laboratorioId) {
        if (!laboratorios.existsById(laboratorioId)) throw FalhaDominio.ausente("Laboratório", laboratorioId);
        return repositorio.disponiveis(laboratorioId);
    }
}
