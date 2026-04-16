/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import entidades.Ingrediente;
import enums.UnidadMedida;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad
 * Ingrediente. Establece el contrato para gestionar el catálogo de insumos,
 * permitiendo el control de stock, validaciones de unidades de medida y
 * verificaciones de uso en recetas.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IIngredienteDAO {

    /**
     * Persiste un nuevo ingrediente en el sistema.
     *
     * * @param ingrediente El objeto {@link Ingrediente} a registrar.
     * @throws PersistenciaException Si ocurre un error durante el guardado.
     */
    public void guardar(Ingrediente ingrediente) throws PersistenciaException;

    /**
     * Actualiza la información de un ingrediente existente (nombre, stock o
     * unidad).
     *
     * * @param ingrediente Objeto con los datos actualizados.
     * @throws PersistenciaException Si la actualización falla.
     */
    public void actualizar(Ingrediente ingrediente) throws PersistenciaException;

    /**
     * Busca un ingrediente específico por su identificador único.
     *
     * * @param id Identificador del ingrediente.
     * @return El {@link Ingrediente} encontrado o null si no existe.
     * @throws PersistenciaException Si ocurre un error en la capa de datos.
     */
    public Ingrediente buscarPorId(Long id) throws PersistenciaException;

    /**
     * Obtiene la lista completa de ingredientes registrados en el catálogo.
     *
     * * @return Lista de todos los objetos {@link Ingrediente}.
     * @throws PersistenciaException Si la consulta falla.
     */
    public List<Ingrediente> obtenerIngredientes() throws PersistenciaException;

    /**
     * Realiza una búsqueda de ingredientes aplicando un filtro dinámico sobre
     * el nombre.
     *
     * * @param filtro Cadena de texto para filtrar los resultados.
     * @return Lista de ingredientes que coinciden con el criterio.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    public List<Ingrediente> buscarPorFiltro(String filtro) throws PersistenciaException;

    /**
     * Localiza un ingrediente mediante la combinación exacta de su nombre y
     * unidad de medida. Útil para evitar duplicados con diferentes magnitudes
     * (ej. "Sal" en gramos vs "Sal" en kilos).
     *
     * * @param nombre Nombre del insumo.
     * @param unidad Objeto {@link UnidadMedida} asociado.
     * @return El {@link Ingrediente} coincidente o null si no se encuentra.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    public Ingrediente buscarPorNombreYUnidad(String nombre, UnidadMedida unidad) throws PersistenciaException;

    /**
     * Verifica si el ingrediente está vinculado a alguna receta de producto.
     * Esencial para validar la integridad antes de intentar una eliminación.
     *
     * * @param idIngrediente Identificador del ingrediente a verificar.
     * @return true si el ingrediente tiene dependencias activas, false en caso
     * contrario.
     * @throws PersistenciaException Si ocurre un error en la verificación.
     */
    public boolean estaEnUso(Long idIngrediente) throws PersistenciaException;

    /**
     * Elimina el registro del ingrediente del sistema.
     *
     * * @param id Identificador del ingrediente a eliminar.
     * @throws PersistenciaException Si el ingrediente no puede ser borrado.
     */
    public void eliminar(Long id) throws PersistenciaException;

}
