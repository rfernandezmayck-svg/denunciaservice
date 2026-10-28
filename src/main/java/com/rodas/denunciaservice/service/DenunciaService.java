
package com.rodas.denunciaservice.service;

import com.rodas.denunciaservice.entity.Denuncia;
import com.rodas.denunciaservice.repository.DenunciaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DenunciaService {

    private final DenunciaRepository repository;

    public DenunciaService(DenunciaRepository repository) {
        this.repository = repository;
    }

    public Denuncia registrar(Denuncia denuncia) {
        denuncia.setId(null);
        denuncia.setFecha(LocalDateTime.now());
        denuncia.setEstado(1);
        return repository.save(denuncia);
    }

    public Page<Denuncia> listar(int offset, int limit) {
        Pageable pageable = PageRequest.of(
                offset / limit,
                limit,
                org.springframework.data.domain.Sort.by("id").ascending()
        );
        return repository.findAll(pageable);
    }

    public Optional<Denuncia> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public List<Denuncia> buscarPorDni(String dni) {
        return repository.findByDni(dni);
    }

    public Optional<Denuncia> atender(Integer id) {
        return repository.findById(id).map(denuncia -> {
            denuncia.setEstado(0);
            return repository.save(denuncia);
        });
    }
}
