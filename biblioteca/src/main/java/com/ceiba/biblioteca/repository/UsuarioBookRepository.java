package com.ceiba.biblioteca.repository;

import com.ceiba.biblioteca.entities.UsuarioBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioBookRepository extends JpaRepository<UsuarioBook,Long> {

    @Query(value = "SELECT * FROM usuariobook WHERE identificacion_usuario = :indetificacionUsuario", nativeQuery = true)
    List<UsuarioBook> findUsuarioBookByIdentificacionUsuario(@Param("indetificacionUsuario") String indetificacionUsuario);
}
