package com.rodas.denunciaservice.repository;

import com.rodas.denunciaservice.entity.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DenunciaRepository extends JpaRepository<Denuncia, Integer> {

    List<Denuncia> findByDni(String dni);
}
