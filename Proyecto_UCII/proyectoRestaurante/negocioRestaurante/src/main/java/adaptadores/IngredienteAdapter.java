/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.IngredienteDTO;
import entidades.Ingrediente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class IngredienteAdapter {

    public static IngredienteDTO entidadADTO(Ingrediente i) {
        if (i == null) {
            return null;
        }

        IngredienteDTO dto = new IngredienteDTO();

        dto.setIdIngrediente(i.getIdIngrediente());
        dto.setNombre(i.getNombre());
        dto.setUnidadMedida(i.getUnidadMedida());
        dto.setCantidadActual(i.getCantidadActual());
        dto.setRutaImagen(i.getRutaImg());

        return dto;
    }

    public static List<IngredienteDTO> listaEntidadADTO(List<Ingrediente> entidades) {
        List<IngredienteDTO> lista = new ArrayList<>();

        for (Ingrediente i : entidades) {
            lista.add(entidadADTO(i));
        }

        return lista;
    }

    public static Ingrediente dtoAEntidadExistente(IngredienteDTO dto) {
        if (dto == null) {
            return null;
        }

        Ingrediente entidad = new Ingrediente();

        entidad.setIdIngrediente(dto.getIdIngrediente());
        entidad.setNombre(dto.getNombre());
        entidad.setUnidadMedida(dto.getUnidadMedida());
        entidad.setCantidadActual(dto.getCantidadActual());
        entidad.setRutaImg(dto.getRutaImagen());

        return entidad;
    }

    public static Ingrediente dtoAEntidadNuevo(IngredienteDTO dto) {
        if (dto == null) {
            return null;
        }

        Ingrediente entidad = new Ingrediente();

        entidad.setNombre(dto.getNombre());
        entidad.setUnidadMedida(dto.getUnidadMedida());
        entidad.setCantidadActual(dto.getCantidadActual());
        entidad.setRutaImg(dto.getRutaImagen());

        return entidad;
    }
}