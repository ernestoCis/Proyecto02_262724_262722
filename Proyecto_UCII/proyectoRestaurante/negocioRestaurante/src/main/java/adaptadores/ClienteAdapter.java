/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.ClienteDTO;
import entidades.Cliente;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteAdapter {
    
    public static Cliente dtoAEntidad(ClienteDTO dto){
        if(dto == null){
            return null;
        }
        Cliente entidad = new Cliente();
        entidad.setIdCliente(dto.getIdCliente());
        entidad.setNombres(dto.getNombres());
        entidad.setApellidoPaterno(dto.getApellidoPaterno());
        entidad.setApellidoMaterno(dto.getApellidoMaterno());
        entidad.setTelefono(dto.getTelefono());
        entidad.setFechaRegistro(dto.getFechaRegistro());
        
        return entidad;
    }
    
    public static ClienteDTO entidadADto(Cliente entidad){
        if(entidad == null){
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(entidad.getIdCliente());
        dto.setNombres(entidad.getNombres());
        dto.setApellidoPaterno(entidad.getApellidoPaterno());
        dto.setApellidoMaterno(entidad.getApellidoMaterno());
        dto.setTelefono(entidad.getTelefono());
        dto.setFechaRegistro(entidad.getFechaRegistro());
        
        return dto;
    }
    
}
