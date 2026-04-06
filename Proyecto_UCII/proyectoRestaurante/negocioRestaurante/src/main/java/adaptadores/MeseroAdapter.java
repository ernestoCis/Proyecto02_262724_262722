/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.MeseroDTO;
import entidades.Mesero;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class MeseroAdapter {
    
    public static MeseroDTO entidadADto(Mesero entidad){
        if(entidad == null){
            return null;
        }
        MeseroDTO dto = new MeseroDTO();
        dto.setUsuario(entidad.getUsuario());
        dto.setIdMesero(entidad.getIdEmpleado());
        dto.setRfc(entidad.getRfc());
        dto.setNombre(entidad.getNombres());
        dto.setApellidoPaterno(entidad.getApellidoPaterno());
        
        if(entidad.getApellidoMaterno() == null || entidad.getApellidoMaterno().trim().isEmpty()){
            dto.setApellidoMaterno("");
        }else{
            dto.setApellidoMaterno(dto.getApellidoMaterno());
        }
        
        return dto;
    }
}
