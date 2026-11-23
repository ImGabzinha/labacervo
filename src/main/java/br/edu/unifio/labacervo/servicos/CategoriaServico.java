package br.edu.unifio.labacervo.servicos;

import br.edu.unifio.labacervo.entidades.*;
import br.edu.unifio.labacervo.dto.CategoriaEntrada;
import br.edu.unifio.labacervo.repositorios.*;
import br.edu.unifio.labacervo.erros.FalhaDominio;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriaServico {
    private final CategoriaRepositorio repositorio;

    public List<Categoria> listar() {
        return repositorio.findAll();
    }

    public Categoria buscar(long id) {
        return repositorio.findById(id).orElseThrow(() -> FalhaDominio.ausente("Categoria", id));
    }

    @Transactional
    public Categoria criar(CategoriaEntrada dados) {
        var registro = new Categoria();
        aplicar(registro, dados);
        return repositorio.save(registro);
    }

    @Transactional
    public Categoria atualizar(long id, CategoriaEntrada dados) {
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

    private void aplicar(Categoria registro, CategoriaEntrada dados) {
        registro.setNome(dados.nome().strip());
        registro.setDescricao(dados.descricao().strip());
    }
}
