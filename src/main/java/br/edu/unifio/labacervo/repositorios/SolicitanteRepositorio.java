package br.edu.unifio.labacervo.repositorios;

import br.edu.unifio.labacervo.entidades.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitanteRepositorio extends JpaRepository<Solicitante, Long> {
}
