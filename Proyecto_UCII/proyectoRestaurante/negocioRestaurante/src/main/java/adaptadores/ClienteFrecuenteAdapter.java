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
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteFrecuenteAdapter {

    public static ClienteFrecuenteDTO entidadADTO(ClienteFrecuente c) {
        if (c == null) {
            return null;
        }

        ClienteFrecuenteDTO dto = new ClienteFrecuenteDTO();

        dto.setIdCliente(c.getIdCliente());
        if(c.getApellidoPaterno() == null){
            c.setApellidoPaterno("");
        }
        if(c.getApellidoMaterno() == null){
            c.setApellidoMaterno("");
        }
        dto.setNombreCompleto(
                c.getNombres() + " " + c.getApellidoPaterno() + " " + c.getApellidoMaterno()
        );
        dto.setTelefono(c.getTelefono());
        dto.setEmail(c.getEmail());

        return dto;
    }

    public static List<ClienteFrecuenteDTO> listaEntidadADTO(List<ClienteFrecuente> entidades) {

        List<ClienteFrecuenteDTO> lista = new ArrayList<>();

        for (ClienteFrecuente c : entidades) {
            lista.add(entidadADTO(c));
        }

        return lista;
    }

    public static ClienteFrecuente dtoAEntidad(ClienteFrecuenteDTO dto) {
        if (dto == null) {
            return null;
        }

        ClienteFrecuente cliente = new ClienteFrecuente();

        cliente.setIdCliente(dto.getIdCliente());

        if (dto.getNombreCompleto() != null) {
            String[] partes = dto.getNombreCompleto().split(" ");

            cliente.setNombres(partes.length > 0 ? partes[0] : "");
            cliente.setApellidoPaterno(partes.length > 1 ? partes[1] : "");
            cliente.setApellidoMaterno(partes.length > 2 ? partes[2] : "");
        }

        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());

        return cliente;
    }
}
