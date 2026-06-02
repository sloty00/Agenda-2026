package com.example.agenda2026;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Maneja las operaciones CRUD del RUT (que es un String)
public interface ContactoRepository extends JpaRepository<Contacto, String> {
}