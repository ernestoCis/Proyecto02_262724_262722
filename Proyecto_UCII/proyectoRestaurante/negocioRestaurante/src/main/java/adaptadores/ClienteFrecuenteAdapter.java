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
        
        dto.setNombres(c.getNombres());
        dto.setApellidoPaterno(c.getApellidoPaterno());
        if(c.getApellidoMaterno() != null || !c.getApellidoMaterno().trim().isEmpty()){
            dto.setApellidoMaterno(c.getApellidoMaterno());
        }
        dto.setTelefono(c.getTelefono());
        if(c.getEmail() != null || !c.getEmail().trim().isEmpty()){
            dto.setEmail(c.getEmail());
        }
        
        return dto;
    }

    public static List<ClienteFrecuenteDTO> listaEntidadADTO(List<ClienteFrecuente> entidades) {

        List<ClienteFrecuenteDTO> lista = new ArrayList<>();

        for (ClienteFrecuente c : entidades) {
            lista.add(entidadADTO(c));
        }

        return lista;
    }

    public static ClienteFrecuente dtoAEntidadExistente(ClienteFrecuenteDTO dto) {
        if (dto == null) {
            return null;
        }

        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setIdCliente(dto.getIdCliente());
        
        cliente.setNombres(dto.getNombres());
        cliente.setApellidoPaterno(dto.getApellidoPaterno());
        if(dto.getApellidoMaterno() != null || !dto.getApellidoMaterno().trim().isEmpty()){
            cliente.setApellidoMaterno(dto.getApellidoMaterno());
        }

        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());

        return cliente;
    }
    
    public static ClienteFrecuente dtoAEntidadNuevo(ClienteFrecuenteDTO dto) {
        if (dto == null) {
            return null;
        }

        ClienteFrecuente cliente = new ClienteFrecuente();
        

        cliente.setNombres(dto.getNombres());
        cliente.setApellidoPaterno(dto.getApellidoPaterno());
        if(dto.getApellidoMaterno() != null || !dto.getApellidoMaterno().trim().isEmpty()){
            cliente.setApellidoMaterno(dto.getApellidoMaterno());
        }

        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());

        return cliente;
    }
}
