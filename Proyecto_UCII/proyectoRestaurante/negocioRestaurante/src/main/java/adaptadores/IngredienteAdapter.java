package adaptadores;

import dtos.IngredienteDTO;
import entidades.Ingrediente;
import enums.UnidadMedida;
import enums.UnidadMedidaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase adaptadora para la entidad Ingrediente. Se encarga de la transformación
 * de datos para la gestión del inventario, permitiendo la conversión de
 * unidades de medida y el manejo de stock entre la capa de persistencia y la
 * interfaz de usuario.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class IngredienteAdapter {

    /**
     * Convierte una entidad {@link Ingrediente} a un {@link IngredienteDTO}.
     * Mapea la unidad de medida basándose en el nombre del Enum para mantener
     * la consistencia entre capas.
     *
     * * @param i La entidad de ingrediente proveniente de la base de datos.
     * @return Un DTO con la información del insumo, o null si la entrada es
     * nula.
     */
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

    /**
     * Transforma una lista de entidades de ingredientes a una lista de DTOs.
     * Útil para poblar tablas de inventario o catálogos de selección.
     *
     * * @param entidades Lista de objetos {@link Ingrediente}.
     * @return Una lista de {@link IngredienteDTO}.
     */
    public static List<IngredienteDTO> listaEntidadADTO(List<Ingrediente> entidades) {
        List<IngredienteDTO> lista = new ArrayList<>();
        for (Ingrediente i : entidades) {
            lista.add(entidadADTO(i));
        }
        return lista;
    }

    /**
     * Convierte un {@link IngredienteDTO} a una entidad con ID preservado. Se
     * utiliza para operaciones donde la identidad del registro es conocida.
     *
     * * @param dto El objeto de transferencia de datos.
     * @return Una entidad {@link Ingrediente} con su ID original mapeado.
     */
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

    /**
     * Crea una nueva entidad {@link Ingrediente} a partir de un DTO. No mapea
     * el ID para permitir que el motor de persistencia (JPA) maneje la
     * generación de la llave primaria.
     *
     * * @param dto Datos del nuevo ingrediente a registrar.
     * @return Una entidad lista para ser insertada en la base de datos.
     */
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

    /**
     * Sincroniza los datos de un DTO con una instancia de entidad ya existente.
     * Este método es altamente eficiente para actualizaciones, ya que modifica
     * la referencia del objeto cargado en el EntityManager sin necesidad de
     * instanciar nuevas entidades.
     *
     * * @param dto Fuente de datos actualizados.
     * @param entidad Destino de la actualización (objeto gestionado por JPA).
     */
    public static void actualizarEntidad(IngredienteDTO dto, Ingrediente entidad) {
        if (dto == null || entidad == null) {
            return;
        }

        entidad.setNombre(dto.getNombre());
        entidad.setCantidadActual(dto.getCantidadActual());
        entidad.setRutaImg(dto.getRutaImagen());
        entidad.setUnidadMedida(
                UnidadMedida.valueOf(dto.getUnidadMedida().name())
        );
    }
}
