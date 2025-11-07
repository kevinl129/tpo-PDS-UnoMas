package com.tpopdsunomas.service;

import java.util.List;
import java.util.Optional;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.patterns.repo.ICuentaRepository;

public class CuentaService {
    
    private ICuentaRepository cuentaRepo;

    public CuentaService(ICuentaRepository cuentaRepo) {
        this.cuentaRepo = cuentaRepo;
    }

    public Cuenta registrarCuenta(String nombre, String email, String clave, int puntosNivel,String codigoPostal) {

        Cuenta nuevaCuenta = new Cuenta(0, nombre, email, clave, puntosNivel,codigoPostal);
        cuentaRepo.guardar(nuevaCuenta);
        System.out.println("Servicio: Cuenta registrada -> " + nombre);
        return nuevaCuenta;
    }

    public Optional<Cuenta> buscarPorId(int id) {
        return cuentaRepo.buscarPorId(id);
    }

    public List<Cuenta> obtenerTodasLasCuentas() {
        return cuentaRepo.buscarTodos();
    }
}
