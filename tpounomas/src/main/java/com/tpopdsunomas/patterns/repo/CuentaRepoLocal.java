package com.tpopdsunomas.patterns.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.tpopdsunomas.model.Cuenta;

public class CuentaRepoLocal implements ICuentaRepository{
    
    private List<Cuenta> cuentasRepo;
    private AtomicInteger proximoId = new AtomicInteger(1);

    public CuentaRepoLocal(){
        this.cuentasRepo = new ArrayList<>();
    }

    @Override
    public void guardar(Cuenta cuenta) {

        if (cuenta.getId() == 0) {
            cuenta.setId(proximoId.getAndIncrement()); 
            this.cuentasRepo.add(cuenta);
        }else{
            eliminar(cuenta.getId());
            this.cuentasRepo.add(cuenta);
        }
    }

    @Override
    public List<Cuenta> buscarTodos() {
        return new ArrayList<>(this.cuentasRepo);
    }

    @Override
    public void eliminar(int id) {
        this.cuentasRepo.removeIf(c -> c.getId() == id);
    }
}
