/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dtos.ClienteFrecuenteDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones permitidas para la lógica de negocio de
 * Clientes Frecuentes. Establece los métodos necesarios para la gestión de
 * puntos, registro y validación de clientes con beneficios de fidelidad.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IClienteFrecuenteBO {

    /**
     * Recupera una lista completa de todos los clientes frecuentes.
     *
     * * @return Lista de {@link ClienteFrecuenteDTO} registrados.
     * @throws NegocioException Si ocurre un error al procesar la solicitud en
     * la capa de negocio.
     */
    public List<ClienteFrecuenteDTO> consultarTodos() throws NegocioException;

    /**
     * Busca clientes frecuentes que coincidan con un criterio de filtrado.
     *
     * * @param filtro Cadena de texto que representa el nombre o teléfono del
     * cliente.
     * @return Lista de {@link ClienteFrecuenteDTO} que coinciden con el filtro.
     * @throws NegocioException Si hay errores en la búsqueda o filtrado de
     * datos.
     */
    public List<ClienteFrecuenteDTO> consultarClientesPorFiltro(String filtro) throws NegocioException;

    /**
     * Registra un nuevo cliente frecuente en el sistema.
     *
     * * @param dto Objeto con la información del cliente a dar de alta.
     * @return El {@link ClienteFrecuenteDTO} registrado con su identificador
     * generado.
     * @throws NegocioException Si los datos del cliente son inválidos o el
     * teléfono ya existe.
     */
    public ClienteFrecuenteDTO registrarCliente(ClienteFrecuenteDTO dto) throws NegocioException;

    /**
     * Actualiza la información de un cliente frecuente existente.
     *
     * * @param dto Objeto con los datos actualizados.
     * @throws NegocioException Si el cliente no existe o los nuevos datos
     * violan reglas de negocio.
     */
    public void actualizarCliente(ClienteFrecuenteDTO dto) throws NegocioException;

    /**
     * Elimina un cliente frecuente del sistema.
     *
     * * @param dto Objeto que contiene el ID del cliente a eliminar.
     * @throws NegocioException Si el cliente tiene vínculos activos que impiden
     * su eliminación.
     */
    public void eliminarCliente(ClienteFrecuenteDTO dto) throws NegocioException;

    /**
     * Obtiene el registro del cliente marcado como "General" (público sin
     * registro específico).
     *
     * * @return DTO del cliente frecuente general.
     * @throws NegocioException Si no se encuentra configurado el cliente
     * general en el sistema.
     */
    public ClienteFrecuenteDTO buscarClienteFrecuenteGeneral() throws NegocioException;

    /**
     * Verifica si un cliente posee historial de comandas registradas.
     *
     * * @param idCliente Identificador único del cliente.
     * @return {@code true} si el cliente tiene comandas, {@code false} de lo
     * contrario.
     * @throws NegocioException Si ocurre un error al consultar la integridad
     * referencial.
     */
    public boolean clienteConComandas(Long idCliente) throws NegocioException;

}
