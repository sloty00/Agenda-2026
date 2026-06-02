package com.example.agenda2026;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Contacto {

    @Id // Define el RUT como la Llave Primaria (PK)
    private String rut; 
    
    private String nombre;
    private String empresa;
    private String telefono;
    private String celular;
    private String email;

    // Constructor vacío obligatorio para Hibernate
    public Contacto() {}

    // Constructor para facilidad de uso
    public Contacto(String rut, String nombre, String empresa, String telefono, String celular, String email) {
        this.rut = normalizarRut(rut);
        this.nombre = nombre;
        this.empresa = empresa;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
    }

    // Lógica del Validador de RUT Chileno (Algoritmo Módulo 11)
    public static boolean validarRut(String rut) {
        if (rut == null) return false;
        
        // Limpiar puntos y guiones
        rut = rut.replace(".", "").replace("-", "").trim().toUpperCase();
        if (rut.length() < 2) return false;

        String cuerpo = rut.substring(0, rut.length() - 1);
        char dv = rut.charAt(rut.length() - 1);

        try {
            int rutNum = Integer.parseInt(cuerpo);
            int m = 0, s = 1;
            for (; rutNum != 0; rutNum /= 10) {
                s = (s + rutNum % 10 * (9 - m++ % 6)) % 11;
            }
            char dvCalculado = (s != 0) ? (char) (s + 47) : 'K';
            return dvCalculado == dv;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Formatea el RUT para que quede estándar (ej: 12345678-K)
    public static String normalizarRut(String rut) {
        if (rut == null) return null;
        return rut.replace(".", "").replace("-", "").trim().toUpperCase();
    }

    // --- GETTERS Y SETTERS (Obligatorios para que Spring maneje los datos) ---
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = normalizarRut(rut); }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCelular() { return celular; }
    public void setCellular(String celular) { this.celular = celular; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}