package com.tpopdsunomas.model;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tpopdsunomas.patterns.observer.IObserverNotificacion;
import com.tpopdsunomas.patterns.strategyNivel.Principiante;
import com.tpopdsunomas.service.Geolocation;
import com.tpopdsunomas.patterns.strategyNivel.INivelJugador;

public class Cuenta{
    private int id;
    private String nombre;
    private String email;
    private String clave;
    private INivelJugador nivel;
    private List<Deporte> deportes;
    private List<Ubicacion> ubicaciones;
    private List<Partido> partidosCreados;
    private List<Partido> partidosInscritos;
    private String codigoPostal;
    
    public Cuenta(int id, String nombre, String email, String clave, int nivel,String codigoPostal) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
        new Principiante(nivel,this);
        
        //this.nivel = Principiante.getInstance();
        this.deportes = new ArrayList<>();
        //this.ubicaciones = new ArrayList<>();
        this.partidosCreados = new ArrayList<>();
        this.partidosInscritos = new ArrayList<>();
        this.codigoPostal=codigoPostal;
        Ubicacion ubicacion = new Ubicacion();   
        ubicaciones = new ArrayList<>();     
        try {                   
            double[] coords = Geolocation.geocodeAddress(codigoPostal);
            ubicacion.setLatitud(coords[0]);
            ubicacion.setLongitud(coords[1]);
            ubicaciones.add(ubicacion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*public Cuenta(int id, String nombre, String email, String clave, INivelJugador nivel) {
        this(id, nombre, email, clave);
        this.nivel = nivel;
    }*/
    
    public void buscarPartido(String ciudad) {
        System.out.println("Buscando partidos en: " + ciudad);
    }
    
    public void crearPartido() {
        System.out.println("Creando nuevo partido...");
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getClave() {
        return clave;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public INivelJugador getNivel() {
        return nivel;
    }
    
    public void setNivel(INivelJugador nivelJugador) {
        this.nivel = nivelJugador;
    }

    /* 

    
    public List<Deporte> getDeportes() {
        return deportes;
    }
    
    public void agregarDeporte(Deporte deporte) {
        if (!deportes.contains(deporte)) {
            deportes.add(deporte);
        }
    }
    public void agregarUbicacion(Ubicacion ubicacion) {
        ubicaciones.add(ubicacion);
    }
     */
    public List<Ubicacion> getUbicaciones() {
        return ubicaciones;
    }
    
    
    
    public List<Partido> getPartidosCreados() {
        return partidosCreados;
    }
    
    public void agregarPartidoCreado(Partido partido) {
        partidosCreados.add(partido);
    }
    
    public List<Partido> getPartidosInscritos() {
        return partidosInscritos;
    }
    
    public void agregarPartidoInscrito(Partido partido) {
        partidosInscritos.add(partido);
    }
    
    @Override
    public String toString() {
        return nombre + " (" + email + ") - Nivel: " + nivel.getNombre();
    }

    public List<Cuenta> listarAmigos(){
        List<Cuenta> amigos = new ArrayList<>();
        
        List<Partido> partidosFinalizados = this.partidosInscritos.stream()
            .filter(partido -> partido.getEstado().getNombre().equals("Finalizado")).collect(Collectors.toList());
                 
        for(Partido partido : partidosFinalizados){
            List<Cuenta> participantes = partido.getParticipantes();
            for(Cuenta jugador:participantes){
                if(!amigos.contains(jugador)){
                    amigos.add(jugador);
                }
            }
        }

        partidosFinalizados = this.partidosCreados.stream()
            .filter(partido -> partido.getEstado().getNombre().equals("Finalizado")).collect(Collectors.toList());

        for(Partido partido : partidosFinalizados){
            List<Cuenta> participantes = partido.getParticipantes();
            for(Cuenta jugador:participantes){
                if(!amigos.contains(jugador)){
                    amigos.add(jugador);
                }
            }
        }


        return amigos;

    }
    
}
