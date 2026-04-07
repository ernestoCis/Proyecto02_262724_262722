package adaptadores;

import dtos.ProductoDTO;
import dtos.RecetaDTO;
import entidades.Producto;
import entidades.Receta;
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
        dto.setTipo(p.getTipo());
        dto.setDisponibilidad(p.getDisponibilidad());

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
        entidad.setTipo(dto.getTipo());
        entidad.setDisponibilidad(dto.getDisponibilidad());

        if (dto.getRecetas() != null) {
            for (RecetaDTO rDTO : dto.getRecetas()) {
                Receta receta = new Receta();
                receta.setCantidad(rDTO.getCantidad());
                receta.setIngrediente(
                        IngredienteAdapter.dtoAEntidadExistente(rDTO.getIngrediente())
                );

                entidad.agregarReceta(receta); // 🔥 IMPORTANTE
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
        entidad.setTipo(dto.getTipo());
        entidad.setDisponibilidad(dto.getDisponibilidad());

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