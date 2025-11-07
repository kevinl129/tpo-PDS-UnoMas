package com.tpopdsunomas.patterns.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tpopdsunomas.model.Partido;

public class PartidoRepoLocal implements IPartidoRepository{

    private List<Partido> partidosRepo;

    public PartidoRepoLocal(){

        partidosRepo = new ArrayList<>();
    }

    @Override
    public void guardar(Partido partido) {
        partidosRepo.add(partido);
    }

    @Override
    public List<Partido> buscarTodos() {
        return new ArrayList<>(partidosRepo);
    }

    @Override
    public void eliminar(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    @Override
    public Optional<Partido> buscarPorId(int id) {
        return partidosRepo.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }
    
}
