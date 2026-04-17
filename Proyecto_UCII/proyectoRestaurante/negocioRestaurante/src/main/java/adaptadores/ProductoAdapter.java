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
 * Clase adaptadora encargada de la transformación de datos para la entidad
 * Producto. Administra la conversión jerárquica que incluye el detalle de las
 * recetas y sus ingredientes asociados, facilitando la gestión del catálogo de
 * alimentos y bebidas dentro del sistema.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ProductoAdapter {

    /**
     * Transforma una entidad {@link Producto} en un {@link ProductoDTO}. Este
     * proceso incluye la conversión profunda de la lista de recetas, asegurando
     * que la capa de vista tenga acceso a los ingredientes necesarios sin
     * exponer el modelo de persistencia.
     *
     * * @param p La entidad del producto con sus relaciones cargadas.
     * @return Un DTO con la información completa del producto, o null si la
     * entrada es nula.
     */
    public static ProductoDTO entidadADTO(Producto p) {
        if (p == null) {
            return null;
        }

        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(p.getIdProducto());
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());
        dto.setDescripcion(p.getDescripcion());
        dto.setDisponibilidad(dto.getDisponibilidad());
        dto.setRutaImagen(p.getRutaImg());

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
        List<RecetaDTO> recetasDTO = new ArrayList<>();
        if (p.getRecetas() != null) {
            for (Receta r : p.getRecetas()) {
                RecetaDTO rDTO = new RecetaDTO();
                rDTO.setIdReceta(r.getId());
                rDTO.setCantidad(r.getCantidad());
                rDTO.setIngrediente(
                        IngredienteAdapter.entidadADTO(r.getIngrediente())
                );
                recetasDTO.add(rDTO);
            }
        }

        dto.setRecetas(recetasDTO);

        return dto;
    }

    /**
     * Convierte una lista de entidades de productos a una lista de DTOs. Útil
     * para poblar el catálogo visual del menú o los módulos de inventario.
     *
     * * @param entidades Lista de objetos {@link Producto}.
     * @return Una lista de objetos {@link ProductoDTO}.
     */
    public static List<ProductoDTO> listaEntidadADTO(List<Producto> entidades) {
        List<ProductoDTO> lista = new ArrayList<>();
        for (Producto p : entidades) {
            lista.add(entidadADTO(p));
        }
        return lista;
    }

    /**
     * Crea una nueva instancia de {@link Producto} a partir de un DTO. Utiliza
     * el método agregarReceta para asegurar que la lógica interna de la entidad
     * mantenga la coherencia de las relaciones One-to-Many.
     *
     * * @param dto El objeto de transferencia con los datos del nuevo
     * producto.
     * @return Una entidad lista para ser persistida (sin ID).
     */
    public static Producto dtoAEntidadNuevo(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }

        Producto entidad = new Producto();
        entidad.setNombre(dto.getNombre());
        entidad.setPrecio(dto.getPrecio());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setDisponibilidad(entidad.getDisponibilidad());
        entidad.setRutaImg(dto.getRutaImagen());

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

    /**
     * Convierte un {@link ProductoDTO} a una entidad JPA existente conservando
     * su ID. Se utiliza para operaciones de actualización y mantenimiento de
     * recetas existentes.
     *
     * * @param dto El DTO con la información actualizada.
     * @return Una entidad {@link Producto} con su identidad y relaciones
     * reconstruidas.
     */
    public static Producto dtoAEntidadExistente(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }

        Producto entidad = new Producto();
        entidad.setIdProducto(dto.getIdProducto());
        entidad.setNombre(dto.getNombre());
        entidad.setPrecio(dto.getPrecio());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setDisponibilidad(entidad.getDisponibilidad());
        entidad.setRutaImg(dto.getRutaImagen());

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
