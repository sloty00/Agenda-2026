package com.example.agenda2026;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agenda")
@CrossOrigin(origins = "*")
public class ContactoController {

    @Autowired
    private ContactoRepository contactoRepository;

    // 1. Listar todos los contactos
    @GetMapping
    public List<Contacto> listarContactos() {
        return contactoRepository.findAll();
    }

    // 2. Guardar un contacto (Validando el RUT)
    @PostMapping
    public ResponseEntity<String> guardarContacto(@RequestBody Contacto nuevoContacto) {
        
        // Validamos el RUT antes de hacer cualquier cosa
        if (!Contacto.validarRut(nuevoContacto.getRut())) {
            return ResponseEntity.badRequest().body("Error: El RUT ingresado no es válido.");
        }

        // Normalizamos el RUT para evitar duplicados con diferente formato
        nuevoContacto.setRut(Contacto.normalizarRut(nuevoContacto.getRut()));

        // Guardamos en MySQL usando Hibernate
        contactoRepository.save(nuevoContacto);
        return ResponseEntity.ok("Contacto guardado exitosamente.");
    }

    // 3. Eliminar un contacto por su RUT
    @DeleteMapping("/{rut}")
    public ResponseEntity<String> eliminarContacto(@PathVariable String rut) {
        // Normalizamos el RUT que viene de la URL para buscar de forma exacta
        String rutLimpio = Contacto.normalizarRut(rut);

        // Verificamos si existe antes de intentar borrar
        if (contactoRepository.existsById(rutLimpio)) {
            contactoRepository.deleteById(rutLimpio);
            return ResponseEntity.ok("Contacto eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("Error: El contacto no existe.");
        }
    }
}