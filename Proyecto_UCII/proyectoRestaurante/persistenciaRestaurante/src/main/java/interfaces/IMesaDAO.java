/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Mesa;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Mesa.
 * Proporciona el contrato para la administración del mobiliario del
 * restaurante, permitiendo el control de disponibilidad y la identificación
 * física de las áreas de servicio.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IMesaDAO {

    /**
     * Registra una nueva mesa en el sistema de persistencia.
     *
     * * @param mesa El objeto {@link Mesa} con la información de capacidad y
     * número.
     * @return La mesa registrada con su identificador generado.
     * @throws PersistenciaException Si ocurre un error durante el proceso de
     * guardado.
     */
    public Mesa registrarMesa(Mesa mesa) throws PersistenciaException;

    /**
     * Recupera el catálogo completo de mesas registradas en el establecimiento.
     *
     * * @return Lista de todos los objetos {@link Mesa}.
     * @throws PersistenciaException Si ocurre un error al consultar la base de
     * datos.
     */
    public List<Mesa> consultarTodas() throws PersistenciaException;

    /**
     * Localiza una mesa específica basándose en su número identificador físico.
     *
     * * @param numeroMesa El número asignado a la mesa (ej. Mesa 1, Mesa 2).
     * @return El objeto {@link Mesa} correspondiente o null si el número no
     * existe.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    public Mesa consultarMesaPorNumero(Integer numeroMesa) throws PersistenciaException;

    /**
     * Actualiza la información de una mesa, comúnmente utilizada para
     * sincronizar cambios en su estado de disponibilidad (Libre, Ocupada,
     * Reservada).
     *
     * * @param mesa El objeto mesa con los datos actualizados para persistir.
     * @return La {@link Mesa} tras ser actualizada en el sistema.
     * @throws PersistenciaException Si la actualización falla o la mesa no es
     * encontrada.
     */
    public Mesa actualizarMesa(Mesa mesa) throws PersistenciaException;
}
