package br.com.reciclagem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reciclagem.model.Dispositivo;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

    public List<Dispositivo> findByUsuario_Id(Long id);
}
