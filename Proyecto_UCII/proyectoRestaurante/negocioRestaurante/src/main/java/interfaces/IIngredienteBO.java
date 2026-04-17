/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dtos.IngredienteDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de lógica de negocio para la gestión de
 * Ingredientes. Proporciona los métodos necesarios para el control de
 * inventario, permitiendo el registro, actualización, eliminación y consulta de
 * los insumos base del sistema.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IIngredienteBO {

    /**
     * Registra un nuevo ingrediente en el sistema. Debe validar que no existan
     * duplicados por nombre y unidad de medida antes de persistir.
     *
     * * @param dto El objeto de transferencia con los datos del nuevo
     * ingrediente.
     * @throws NegocioException Si los datos son inconsistentes o el ingrediente
     * ya existe.
     */
    public void registrarIngrediente(IngredienteDTO dto) throws NegocioException;

    /**
     * Actualiza la información de un ingrediente existente. Esta operación
     * puede desencadenar la actualización de disponibilidad en los productos
     * que utilizan este ingrediente si el stock cambia.
     *
     * * @param dto El DTO con los datos actualizados.
     * @throws NegocioException Si el ID es inválido o se intenta duplicar un
     * nombre existente.
     */
    public void actualizarIngrediente(IngredienteDTO dto) throws NegocioException;

    /**
     * Obtiene una lista con todos los ingredientes registrados en la base de
     * datos.
     *
     * * @return Lista de {@link IngredienteDTO} con la información completa
     * del inventario.
     * @throws NegocioException Si ocurre un error técnico al recuperar los
     * datos.
     */
    public List<IngredienteDTO> consultarTodos() throws NegocioException;

    /**
     * Busca ingredientes que coincidan con un criterio de búsqueda.
     *
     * * @param filtro Cadena de texto para filtrar por nombre o
     * características.
     * @return Lista de {@link IngredienteDTO} que cumplen con el filtro
     * proporcionado.
     * @throws NegocioException Si ocurre un error durante el proceso de
     * filtrado.
     */
    public List<IngredienteDTO> buscarPorFiltro(String filtro) throws NegocioException;

    /**
     * Recupera la información detallada de un ingrediente mediante su
     * identificador único.
     *
     * * @param id El identificador único del ingrediente.
     * @return El {@link IngredienteDTO} correspondiente al ID.
     * @throws NegocioException Si el ingrediente no existe o el ID es nulo.
     */
    public IngredienteDTO buscarPorId(Long id) throws NegocioException;

    /**
     * Elimina un ingrediente del sistema. Generalmente, debe validar que el
     * ingrediente no esté asociado a ninguna receta activa antes de proceder
     * con la eliminación.
     *
     * * @param id El identificador único del ingrediente a eliminar.
     * @throws NegocioException Si el ingrediente está en uso o no puede ser
     * borrado.
     */
    public void eliminarIngrediente(Long id) throws NegocioException;

}
