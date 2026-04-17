/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocio;

import adaptadores.IngredienteAdapter;
import daos.IngredienteDAO;
import daos.ProductoDAO;
import dtos.IngredienteDTO;
import entidades.Ingrediente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IIngredienteBO;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la lógica de negocio para la gestión de ingredientes. Se
 * encarga de validar reglas de integridad, evitar duplicados por nombre y
 * unidad, y coordinar la actualización de disponibilidad de productos cuando el
 * stock cambia.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class IngredienteBO implements IIngredienteBO {

    /**
     * Instancia única de la clase para el patrón Singleton.
     */
    private static IngredienteBO instance;
    /**
     * Acceso a los datos de ingredientes en la base de datos.
     */
    private IngredienteDAO ingredienteDAO;
    /**
     * Acceso a los datos de productos para actualizar disponibilidad por stock.
     */
    private ProductoDAO productoDAO;

    /**
     * Constructor privado que inicializa los DAOs de persistencia.
     */
    private IngredienteBO() {
        ingredienteDAO = IngredienteDAO.getInstance();
        productoDAO = ProductoDAO.getInstance();
    }

    /**
     * Obtiene la instancia única de la clase IngredienteBO.
     *
     * @return La instancia Singleton de esta clase.
     */
    public static IngredienteBO getInstance() {
        if (instance == null) {
            instance = new IngredienteBO();
        }
        return instance;
    }

    /**
     * Registra un nuevo ingrediente en el sistema. Valida que no exista otro
     * ingrediente con el mismo nombre y unidad de medida.
     *
     * * @param dto El objeto de transferencia con la información del nuevo
     * ingrediente.
     * @throws NegocioException Si los datos son inválidos, el ingrediente ya
     * existe o hay un error en la persistencia.
     */
    @Override
    public void registrarIngrediente(IngredienteDTO dto) throws NegocioException {
        validarDatos(dto);

        try {

            Ingrediente existente = ingredienteDAO.buscarPorNombreYUnidad(dto.getNombre(), enums.UnidadMedida.valueOf(dto.getUnidadMedida().name()));

            if (existente != null) {
                throw new NegocioException("Ya existe un ingrediente con ese nombre y unidad"
                );
            }

            Ingrediente entidad = IngredienteAdapter.dtoAEntidadNuevo(dto);

            ingredienteDAO.guardar(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar ingrediente", e);
        }
    }

    /**
     * Actualiza la información de un ingrediente existente. Al actualizar, se
     * verifica la integridad del nombre/unidad y se dispara la actualización de
     * disponibilidad en los productos que lo utilizan.
     *
     * * @param dto DTO con los datos actualizados del ingrediente.
     * @throws NegocioException Si el ID es nulo, los datos son inválidos o
     * existe duplicidad.
     */
    @Override
    public void actualizarIngrediente(IngredienteDTO dto) throws NegocioException {
        if (dto.getIdIngrediente() == null) {
            throw new NegocioException("No se puede actualizar sin ID");
        }

        validarDatos(dto);

        try {

            Ingrediente existente = ingredienteDAO.buscarPorNombreYUnidad(dto.getNombre(), enums.UnidadMedida.valueOf(dto.getUnidadMedida().name()));

            if (existente != null && !existente.getIdIngrediente().equals(dto.getIdIngrediente())) {
                throw new NegocioException("Ya existe un ingrediente con ese nombre y unidad");
            }

            Ingrediente entidad = ingredienteDAO.buscarPorId(dto.getIdIngrediente());
            IngredienteAdapter.actualizarEntidad(dto, entidad);

            ingredienteDAO.actualizar(entidad);

            productoDAO.actualizarProductosPorIngrediente(entidad.getIdIngrediente());

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar ingrediente", e);
        }
    }

    /**
     * Consulta la lista completa de ingredientes registrados.
     *
     * @return Una lista de {@link IngredienteDTO} con todos los ingredientes.
     * @throws NegocioException Si ocurre un error al cargar los datos desde la
     * persistencia.
     */
    @Override
    public List<IngredienteDTO> consultarTodos() throws NegocioException {
        try {
            List<Ingrediente> entidades = ingredienteDAO.obtenerIngredientes();
            List<IngredienteDTO> dtos = new ArrayList<>();

            for (Ingrediente i : entidades) {
                dtos.add(IngredienteAdapter.entidadADTO(i));
            }

            return dtos;

        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudieron cargar los ingredientes", e);
        }
    }

    /**
     * Busca ingredientes que coincidan con un criterio de búsqueda (filtro).
     *
     * @param filtro Cadena de texto para filtrar los nombres de los
     * ingredientes.
     * @return Lista de DTOs que cumplen con el criterio.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    @Override
    public List<IngredienteDTO> buscarPorFiltro(String filtro) throws NegocioException {
        try {
            List<Ingrediente> lista = ingredienteDAO.buscarPorFiltro(filtro);
            List<IngredienteDTO> dtos = new ArrayList<>();

            for (Ingrediente i : lista) {
                dtos.add(IngredienteAdapter.entidadADTO(i));
            }

            return dtos;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar ingredientes", e);
        }
    }

    /**
     * Realiza las validaciones de negocio básicas para un ingrediente. Verifica
     * que el nombre no esté vacío, la unidad esté presente y el stock no sea
     * negativo.
     *
     * * @param dto El ingrediente a validar.
     * @throws NegocioException Si alguna de las reglas de validación falla.
     */
    private void validarDatos(IngredienteDTO dto) throws NegocioException {

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new NegocioException("El nombre es obligatorio");
        }

        if (dto.getUnidadMedida() == null) {
            throw new NegocioException("La unidad es obligatoria");
        }

        if (dto.getCantidadActual() == null || dto.getCantidadActual() < 0) {

            throw new NegocioException("La cantidad debe ser mayor o igual a 0");
        }
    }

    /**
     * Busca un ingrediente específico mediante su identificador único.
     *
     * @param id El ID del ingrediente.
     * @return El DTO del ingrediente encontrado.
     * @throws NegocioException Si el ID es nulo o el ingrediente no existe.
     */
    @Override
    public IngredienteDTO buscarPorId(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El ID del ingrediente es obligatorio");
        }

        try {
            Ingrediente entidad = ingredienteDAO.buscarPorId(id);

            if (entidad == null) {
                throw new NegocioException("Ingrediente no encontrado");
            }

            return IngredienteAdapter.entidadADTO(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar ingrediente", e);
        }
    }

    /**
     * Elimina un ingrediente del sistema siempre y cuando no esté siendo
     * utilizado en alguna receta de productos.
     *
     * * @param id El ID del ingrediente a eliminar.
     * @throws NegocioException Si el ID es nulo, no se encuentra el ingrediente
     * o si el ingrediente está en uso.
     */
    @Override
    public void eliminarIngrediente(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El ID del ingrediente es obligatorio");
        }

        try {
            Ingrediente ingrediente = ingredienteDAO.buscarPorId(id);

            if (ingrediente == null) {
                throw new NegocioException("Ingrediente no encontrado");
            }

            if (ingredienteDAO.estaEnUso(id)) {
                throw new NegocioException("No se puede eliminar el ingrediente porque está en uso en recetas");
            }

            ingredienteDAO.eliminar(id);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al eliminar ingrediente", e);
        }
    }

}
