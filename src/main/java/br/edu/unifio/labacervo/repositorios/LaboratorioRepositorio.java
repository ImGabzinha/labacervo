package br.edu.unifio.labacervo.repositorios;

import br.edu.unifio.labacervo.entidades.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratorioRepositorio extends JpaRepository<Laboratorio, Long> {
}
