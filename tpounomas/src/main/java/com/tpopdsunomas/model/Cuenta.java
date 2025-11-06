package com.tpopdsunomas.model;
import java.util.ArrayList;
import java.util.List;
import com.tpopdsunomas.model.observer.IObserverNotificacion;

public class Cuenta{
    private int id;
    private String nombre;
    private String email;
    private String clave;
    //private INivelJugador nivel;
    //private List<Deporte> deportes;
    //private List<Ubicacion> ubicaciones;
    private List<Partido> partidosCreados;
    private List<Partido> partidosInscritos;
    
    public Cuenta(int id, String nombre, String email, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
        //this.nivel = Principiante.getInstance();
        //this.deportes = new ArrayList<>();
        //this.ubicaciones = new ArrayList<>();
        this.partidosCreados = new ArrayList<>();
        this.partidosInscritos = new ArrayList<>();
    }
    /* 
    public Cuenta(int id, String nombre, String email, String clave, INivelJugador nivel) {
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
    /* 
    public INivelJugador getNivel() {
        return nivel;
    }
    
    public void setNivel(INivelJugador nivel) {
        this.nivel = nivel;
    }
    
    public List<Deporte> getDeportes() {
        return deportes;
    }
    
    public void agregarDeporte(Deporte deporte) {
        if (!deportes.contains(deporte)) {
            deportes.add(deporte);
        }
    }
    
    public List<Ubicacion> getUbicaciones() {
        return ubicaciones;
    }
    
    public void agregarUbicacion(Ubicacion ubicacion) {
        ubicaciones.add(ubicacion);
    }*/
    
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
        return nombre + " (" + email + ") - Nivel: " /*+ nivel.getNombre()*/;
    }


    
}
