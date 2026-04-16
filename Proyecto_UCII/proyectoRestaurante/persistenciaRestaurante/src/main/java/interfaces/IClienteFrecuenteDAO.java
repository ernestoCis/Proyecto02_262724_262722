/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad
 * ClienteFrecuente. Establece los métodos necesarios para la gestión, búsqueda
 * y validación de clientes dentro del sistema.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IClienteFrecuenteDAO {

    /**
     * Obtiene una lista de todos los clientes frecuentes registrados en el
     * sistema.
     *
     * * @return Lista de objetos {@link ClienteFrecuente}.
     * @throws PersistenciaException Si ocurre un error en la capa de
     * persistencia.
     */
    public List<ClienteFrecuente> obtenerFrecuentes() throws PersistenciaException;

    /**
     * Busca clientes frecuentes que coincidan con un criterio de búsqueda
     * (nombre, teléfono o correo).
     *
     * * @param filtro Cadena de texto para filtrar los resultados.
     * @return Lista de clientes que cumplen con el filtro.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    public List<ClienteFrecuente> buscarPorFiltro(String filtro) throws PersistenciaException;

    /**
     * Almacena un nuevo cliente frecuente en la base de datos.
     *
     * * @param cliente El objeto {@link ClienteFrecuente} a guardar.
     * @return El cliente guardado con su identificador asignado.
     * @throws PersistenciaException Si el proceso de guardado falla.
     */
    public ClienteFrecuente guardar(ClienteFrecuente cliente) throws PersistenciaException;

    /**
     * Localiza a un cliente frecuente mediante su identificador único.
     *
     * * @param id Identificador único del cliente.
     * @return El {@link ClienteFrecuente} encontrado o null si no existe.
     * @throws PersistenciaException Si ocurre un error en la búsqueda.
     */
    public ClienteFrecuente buscarPorId(Long id) throws PersistenciaException;

    /**
     * Busca un cliente frecuente utilizando su número de teléfono como
     * criterio.
     *
     * * @param telefono Número telefónico del cliente.
     * @return El {@link ClienteFrecuente} asociado o null si no se encuentra.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    public ClienteFrecuente buscarPorTelefono(String telefono) throws PersistenciaException;

    /**
     * Elimina el registro de un cliente frecuente del sistema.
     *
     * * @param idCliente Identificador del cliente a eliminar.
     * @throws PersistenciaException Si el cliente no puede ser eliminado o no
     * existe.
     */
    public void eliminarCliente(Long idCliente) throws PersistenciaException;

    /**
     * Recupera el registro especial destinado al "Cliente General" (ventas al
     * público sin registro).
     *
     * * @return El objeto {@link ClienteFrecuente} del cliente general.
     * @throws PersistenciaException Si ocurre un error al consultar el
     * registro.
     */
    public ClienteFrecuente buscarClienteFrecuenteGeneral() throws PersistenciaException;

    /**
     * Verifica si un cliente tiene historial de comandas registradas. Este
     * método es útil para validar si un cliente puede ser eliminado
     * físicamente.
     *
     * * @param idCliente Identificador del cliente a consultar.
     * @return true si existen comandas asociadas, false en caso contrario.
     * @throws PersistenciaException Si ocurre un error durante la verificación.
     */
    public boolean tieneComandas(Long idCliente) throws PersistenciaException;
}
