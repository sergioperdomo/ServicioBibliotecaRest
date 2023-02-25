package com.ceiba.biblioteca.controller;


import com.ceiba.biblioteca.DTO.UsuarioBookDTO;
import com.ceiba.biblioteca.DTO.UsuarioBookInDTO;
import com.ceiba.biblioteca.DTO.UsuarioBookOutDTO;
import com.ceiba.biblioteca.service.Impl.PrestamosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/prestamo", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrestamoControlador  {

    @Autowired
    private PrestamosService prestamosService;

    @PostMapping
    public UsuarioBookOutDTO guardarUsuarioBook(@RequestBody UsuarioBookInDTO usuarioBookInDTO) throws Exception {
        return prestamosService.guardarUsuarioBook(usuarioBookInDTO);
    }
    @GetMapping(path = "/{id-prestamo}")
    public UsuarioBookDTO getUserBook(@PathVariable("id-prestamo") Long id) throws Exception{
        return prestamosService.getUserBook(id);
    }

}

