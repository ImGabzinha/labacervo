package br.edu.unifio.labacervo.repositorios;

import br.edu.unifio.labacervo.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
}
