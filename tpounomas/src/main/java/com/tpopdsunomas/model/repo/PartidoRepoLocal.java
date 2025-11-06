package com.tpopdsunomas.model.repo;

import java.util.ArrayList;
import java.util.List;

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
    
}
