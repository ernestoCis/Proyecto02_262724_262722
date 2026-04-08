package adaptadores;

import dtos.IngredienteDTO;
import entidades.Ingrediente;
import enums.UnidadMedida;
import enums.UnidadMedidaDTO;
import java.util.ArrayList;
import java.util.List;

public class IngredienteAdapter {

    public static IngredienteDTO entidadADTO(Ingrediente i) {
        if (i == null) {
            return null;
        }

        IngredienteDTO dto = new IngredienteDTO();
        dto.setIdIngrediente(i.getIdIngrediente());
        dto.setNombre(i.getNombre());
        dto.setCantidadActual(i.getCantidadActual());
        dto.setRutaImagen(i.getRutaImg());

        dto.setUnidadMedida(
                UnidadMedidaDTO.valueOf(i.getUnidadMedida().name())
        );
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
        entidad.setCantidadActual(dto.getCantidadActual());
        entidad.setRutaImg(dto.getRutaImagen());

        entidad.setUnidadMedida(
                UnidadMedida.valueOf(dto.getUnidadMedida().name())
        );

        return entidad;
    }

    public static Ingrediente dtoAEntidadNuevo(IngredienteDTO dto) {
        if (dto == null) {
            return null;
        }

        Ingrediente entidad = new Ingrediente();
        entidad.setNombre(dto.getNombre());
        entidad.setCantidadActual(dto.getCantidadActual());
        entidad.setRutaImg(dto.getRutaImagen());

        // convertir enum
        entidad.setUnidadMedida(
                UnidadMedida.valueOf(dto.getUnidadMedida().name())
        );
        
        return entidad;
    }
}
