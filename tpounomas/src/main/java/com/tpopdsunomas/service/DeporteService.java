package com.tpopdsunomas.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.tpopdsunomas.model.Deporte;
import com.tpopdsunomas.patterns.repo.IDeporteRepository;

public class DeporteService {
    
    private IDeporteRepository deporteRepo;

    public DeporteService(IDeporteRepository deporteRepo) {
        this.deporteRepo = deporteRepo;
    }

    public void guardar(Deporte deporte) {
        deporteRepo.guardar(deporte);
    }

    public List<Deporte> obtenerTodos() {
        return deporteRepo.buscarTodos();
    }

    public Optional<Deporte> buscarPorId(int id) {
        return deporteRepo.buscarPorId(id);
    }

    public Deporte getDeporteOError(int id) {
        return deporteRepo.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio: No se encontró el deporte con ID: " + id));
    }

    public void eliminar(int id) {
        deporteRepo.eliminar(id);
    }
}
