package com.ceiba.biblioteca.service.Impl;

import com.ceiba.biblioteca.DTO.*;
import com.ceiba.biblioteca.entities.UsuarioBook;
import com.ceiba.biblioteca.repository.UsuarioBookRepository;
import com.ceiba.biblioteca.service.IPretamosService;
import com.ceiba.biblioteca.service.IfechaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrestamosService implements IPretamosService {
    private final UsuarioBookRepository usuarioBookRepository;
    private final IfechaService ifechaService;

    @Override
    public ResponseEntity<Object> guardarUsuarioBook(UsuarioBookInDTO usuarioBookInDTO) throws Exception {

        if (Objects.isNull(usuarioBookInDTO)) {
            System.out.println("Error en parametro de entrada.");
            return null;
        }

        MensajeDTO mensajeDTO = validaciones(usuarioBookInDTO);

        if (!Objects.isNull(mensajeDTO.getMensaje())) {
            return ResponseEntity.status(400).body(mensajeDTO);
        }

        FechaDTO fechaVigencia = validacionFecha(usuarioBookInDTO);

        ModelMapper mapper = new ModelMapper();

        UsuarioBook usuarioBook = mapper.map(usuarioBookInDTO, UsuarioBook.class);
        usuarioBook.setFechaMaximaDevolucion(fechaVigencia.getFecha());
        usuarioBook.setTipoUsuario(usuarioBookInDTO.getTipoUsuario());

        usuarioBookRepository.save(usuarioBook);

        UsuarioBookOutDTO usuarioBookOutDTO = new UsuarioBookOutDTO();
        usuarioBookOutDTO.setId(usuarioBook.getId());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(usuarioBook.getFechaMaximaDevolucion());

        usuarioBookOutDTO.setFechaMaximaDevolucion(date);

        return ResponseEntity.status(200).body(usuarioBookOutDTO);

    }

    @Override
    public UsuarioBookDTO getUserBook(Long id) throws Exception {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en parametro de entrada");
        }

        try {

            Optional<UsuarioBook> usuarioBook = usuarioBookRepository.findById(id);
            if (Objects.isNull(usuarioBook)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no existe o no a prestado ningun libro");
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String date = formatter.format(usuarioBook.get().getFechaMaximaDevolucion());

            UsuarioBookDTO usuarioBookDTO = UsuarioBookDTO.builder()
                    .id(id)
                    .isbn(usuarioBook.get().getIsbn())
                    .tipoUsuario(usuarioBook.get().getTipoUsuario())
                    .fechaMaximaDevolucion(date)
                    .identificacionUsuario(usuarioBook.get().getIdentificacionUsuario())
                    .build();

            return usuarioBookDTO;

        } catch (Exception ex) {
            System.out.println(ex);
            throw new Exception(ex.getMessage());
        }

    }

    private FechaDTO validacionFecha(UsuarioBookInDTO usuarioBookInDTO) throws Exception {

        Integer dias = 0;
        if (usuarioBookInDTO.getTipoUsuario() == 1) {
            dias = 10;
        } else if (usuarioBookInDTO.getTipoUsuario() == 2) {
            dias = 8;
        } else if (usuarioBookInDTO.getTipoUsuario() == 3) {
            dias = 7;
        }

        FechaDTO fecha = ifechaService.sumarDiasHabilesAFecha(new Date(), dias);

        return fecha;

    }

    private MensajeDTO validaciones(UsuarioBookInDTO usuarioBookInDTO) throws Exception {

        MensajeDTO mensajeDTO = new MensajeDTO();

        List<UsuarioBook> consulta = usuarioBookRepository.findUsuarioBookByIdentificacionUsuario(usuarioBookInDTO.getIdentificacionUsuario());

        if (usuarioBookInDTO.getTipoUsuario() == 3 && consulta.size() >= 1) {
            mensajeDTO.setMensaje("El usuario con identificación " + usuarioBookInDTO.getIdentificacionUsuario() +
                    " ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo");
        }

        if (usuarioBookInDTO.getTipoUsuario() > 3) {
            mensajeDTO.setMensaje("Tipo de usuario no permitido en la biblioteca");
        }

        return mensajeDTO;

    }

}
