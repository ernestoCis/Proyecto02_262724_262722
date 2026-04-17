/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.ClienteFrecuenteDTO;
import entidades.ClienteFrecuente;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase adaptadora encargada de la transformación de datos entre objetos de
 * dominio (ClienteFrecuente) y objetos de transferencia de datos
 * (ClienteFrecuenteDTO). * Este adaptador permite desacoplar la capa de
 * persistencia de la capa de vista, asegurando que la lógica de negocio no
 * exponga directamente las entidades de la base de datos.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteFrecuenteAdapter {

    /**
     * Convierte una entidad de tipo {@link ClienteFrecuente} a su equivalente
     * {@link ClienteFrecuenteDTO}. Incluye una lógica de limpieza (trim) para
     * asegurar que las cadenas de texto no contengan espacios innecesarios.
     *
     * * @param c La entidad de persistencia.
     * @return Un objeto DTO con la información del cliente, o null si la
     * entidad es nula.
     */
    public static ClienteFrecuenteDTO entidadADTO(ClienteFrecuente c) {
        if (c == null) {
            return null;
        }

        ClienteFrecuenteDTO dto = new ClienteFrecuenteDTO();

        dto.setIdCliente(c.getIdCliente());
        dto.setNombres(c.getNombres());

        dto.setApellidoPaterno(
                c.getApellidoPaterno() != null ? c.getApellidoPaterno().trim() : ""
        );

        dto.setApellidoMaterno(
                c.getApellidoMaterno() != null ? c.getApellidoMaterno().trim() : ""
        );

        dto.setTelefono(
                c.getTelefono() != null ? c.getTelefono().trim() : ""
        );

        dto.setEmail(
                c.getEmail() != null ? c.getEmail().trim() : ""
        );

        return dto;
    }

    /**
     * Transforma una lista de entidades en una lista de DTOs.
     *
     * * @param entidades Lista de objetos {@link ClienteFrecuente}.
     * @return Una lista de objetos {@link ClienteFrecuenteDTO}.
     */
    public static List<ClienteFrecuenteDTO> listaEntidadADTO(List<ClienteFrecuente> entidades) {

        List<ClienteFrecuenteDTO> lista = new ArrayList<>();

        for (ClienteFrecuente c : entidades) {
            lista.add(entidadADTO(c));
        }

        return lista;
    }

    /**
     * Convierte un {@link ClienteFrecuenteDTO} a una entidad JPA existente. Se
     * utiliza principalmente para operaciones de actualización (update) donde
     * el ID del cliente ya está definido.
     *
     * * @param dto El objeto de transferencia de datos.
     * @return Una entidad {@link ClienteFrecuente} con el ID mapeado.
     */
    public static ClienteFrecuente dtoAEntidadExistente(ClienteFrecuenteDTO dto) {
        if (dto == null) {
            return null;
        }

        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setIdCliente(dto.getIdCliente());

        cliente.setNombres(dto.getNombres());
        cliente.setApellidoPaterno(dto.getApellidoPaterno());
        if (dto.getApellidoMaterno() != null && !dto.getApellidoMaterno().trim().isEmpty()) {
            cliente.setApellidoMaterno(dto.getApellidoMaterno());
        }

        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());

        return cliente;
    }

    /**
     * Convierte un {@link ClienteFrecuenteDTO} a una nueva entidad JPA. Se
     * utiliza para operaciones de inserción (insert), omitiendo el ID para que
     * la base de datos lo genere automáticamente.
     *
     * * @param dto El objeto de transferencia de datos con la información del
     * nuevo cliente.
     * @return Una entidad {@link ClienteFrecuente} lista para persistirse.
     */
    public static ClienteFrecuente dtoAEntidadNuevo(ClienteFrecuenteDTO dto) {
        if (dto == null) {
            return null;
        }

        ClienteFrecuente cliente = new ClienteFrecuente();

        cliente.setNombres(dto.getNombres());
        cliente.setApellidoPaterno(dto.getApellidoPaterno());
        if (dto.getApellidoMaterno() != null && !dto.getApellidoMaterno().trim().isEmpty()) {
            cliente.setApellidoMaterno(dto.getApellidoMaterno());
        }

        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());

        return cliente;
    }
}
