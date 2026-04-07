/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.MesaDTO;
import entidades.Mesa;
import enums.EstadoMesa;
import enums.EstadoMesaDTO;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class MesaAdapter {
    public static Mesa dtoAEntidad(MesaDTO dto){
        if(dto == null){
            return null;
        }
        
        Mesa entidad = new Mesa();
        entidad.setIdMesa(dto.getIdMesa());
        entidad.setNumero(dto.getNumero());
        
        //enums
        if(dto.getDisponibilidad() == EstadoMesaDTO.DISPONIBLE){
            entidad.setDisponibilidad(EstadoMesa.DISPONIBLE);
        }else if(dto.getDisponibilidad() == EstadoMesaDTO.NO_DISPONIBLE){
            entidad.setDisponibilidad(EstadoMesa.NO_DISPONIBLE);
        }
        
        return entidad;
    }
    
    public static MesaDTO entidadADto(Mesa entidad){
        if(entidad == null){
            return null;
        }
        
        MesaDTO dto = new MesaDTO();
        dto.setIdMesa(entidad.getIdMesa());
        dto.setNumero(entidad.getNumero());
        if(entidad.getDisponibilidad() == EstadoMesa.DISPONIBLE){
            dto.setDisponibilidad(EstadoMesaDTO.DISPONIBLE);
        }else if(entidad.getDisponibilidad() == EstadoMesa.NO_DISPONIBLE){
            dto.setDisponibilidad(EstadoMesaDTO.NO_DISPONIBLE);
        }
        
        return dto;
    }
    
    
}
