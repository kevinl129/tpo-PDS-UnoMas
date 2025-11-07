package com.tpopdsunomas.patterns.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.tpopdsunomas.model.Deporte;

public class DeporteRepoLocal implements IDeporteRepository{
    private List<Deporte> deportesRepo;
    private AtomicInteger proximoId = new AtomicInteger(1); // Para IDs autoincrementales

    public DeporteRepoLocal() {
        this.deportesRepo = new ArrayList<>();
    }

    @Override
    public void guardar(Deporte deporte) {
        if (deporte.getId() == 0) {
            deporte.setId(proximoId.getAndIncrement());
            this.deportesRepo.add(deporte);
        } else {
            eliminar(deporte.getId()); 
            this.deportesRepo.add(deporte); 
        }
        System.out.println("Repo: Deporte guardado -> " + deporte.getNombre());
    }

    @Override
    public Optional<Deporte> buscarPorId(int id) {
        return this.deportesRepo.stream()
                .filter(d -> d.getId() == id)
                .findFirst();
    }

    @Override
    public List<Deporte> buscarTodos() {
        return new ArrayList<>(this.deportesRepo);
    }

    @Override
    public void eliminar(int id) {
        this.deportesRepo.removeIf(d -> d.getId() == id);
    }
}
