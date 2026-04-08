package adaptadores;

import dtos.ProductoDTO;
import dtos.RecetaDTO;
import entidades.Producto;
import entidades.Receta;
import enums.DisponibilidadProducto;
import enums.DisponibilidadProductoDTO;
import enums.TipoProducto;
import enums.TipoProductoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ProductoAdapter {

    public static ProductoDTO entidadADTO(Producto p) {
        if (p == null) return null;

        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(p.getIdProducto());
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());

        // convertir enums
        if (p.getTipo() != null) {
            dto.setTipo(
                TipoProductoDTO.valueOf(p.getTipo().name())
            );
        }

        if (p.getDisponibilidad() != null) {
            dto.setDisponibilidad(
                DisponibilidadProductoDTO.valueOf(p.getDisponibilidad().name())
            );
        }

        // recetas
//        List<RecetaDTO> recetasDTO = new ArrayList<>();
//        if (p.getRecetas() != null) {
//            for (Receta r : p.getRecetas()) {
//                RecetaDTO rDTO = new RecetaDTO();
//                rDTO.setIdReceta(r.getId());
//                rDTO.setCantidad(r.getCantidad());
//                rDTO.setIngrediente(
//                        IngredienteAdapter.entidadADTO(r.getIngrediente())
//                );
//                recetasDTO.add(rDTO);
//            }
//        }
//
//        dto.setRecetas(recetasDTO);

        return dto;
    }

    public static List<ProductoDTO> listaEntidadADTO(List<Producto> entidades) {
        List<ProductoDTO> lista = new ArrayList<>();
        for (Producto p : entidades) {
            lista.add(entidadADTO(p));
        }
        return lista;
    }

    public static Producto dtoAEntidadNuevo(ProductoDTO dto) {
        if (dto == null) return null;

        Producto entidad = new Producto();
        entidad.setNombre(dto.getNombre());
        entidad.setPrecio(dto.getPrecio());

        // convertir enums
        if (dto.getTipo() != null) {
            entidad.setTipo(
                TipoProducto.valueOf(dto.getTipo().name())
            );
        }

        if (dto.getDisponibilidad() != null) {
            entidad.setDisponibilidad(
                DisponibilidadProducto.valueOf(dto.getDisponibilidad().name())
            );
        }

        if (dto.getRecetas() != null) {
            for (RecetaDTO rDTO : dto.getRecetas()) {
                Receta receta = new Receta();
                receta.setCantidad(rDTO.getCantidad());
                receta.setIngrediente(
                        IngredienteAdapter.dtoAEntidadExistente(rDTO.getIngrediente())
                );
                entidad.agregarReceta(receta);
            }
        }

        return entidad;
    }

    public static Producto dtoAEntidadExistente(ProductoDTO dto) {
        if (dto == null) return null;

        Producto entidad = new Producto();
        entidad.setIdProducto(dto.getIdProducto());
        entidad.setNombre(dto.getNombre());
        entidad.setPrecio(dto.getPrecio());

        // convertir enums
        if (dto.getTipo() != null) {
            entidad.setTipo(
                TipoProducto.valueOf(dto.getTipo().name())
            );
        }

        if (dto.getDisponibilidad() != null) {
            entidad.setDisponibilidad(
                DisponibilidadProducto.valueOf(dto.getDisponibilidad().name())
            );
        }

        if (dto.getRecetas() != null) {
            for (RecetaDTO rDTO : dto.getRecetas()) {
                Receta receta = new Receta();
                receta.setId(rDTO.getIdReceta());
                receta.setCantidad(rDTO.getCantidad());
                receta.setIngrediente(
                        IngredienteAdapter.dtoAEntidadExistente(rDTO.getIngrediente())
                );
                entidad.agregarReceta(receta);
            }
        }

        return entidad;
    }
}