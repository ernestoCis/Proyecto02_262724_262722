/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Comanda;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Comanda.
 * Proporciona los métodos necesarios para la gestión de pedidos, cálculos de
 * lealtad de clientes y consultas de historial de ventas por rangos de fechas.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IComandaDAO {

    /**
     * Calcula la cantidad total de comandas realizadas por un cliente
     * específico.
     *
     * * @param idCliente Identificador único del cliente.
     * @return El número de visitas registradas.
     * @throws PersistenciaException Si ocurre un error al realizar el conteo en
     * la base de datos.
     */
    public int contarVisitas(Long idCliente) throws PersistenciaException;

    /**
     * Suma el importe total de todas las comandas finalizadas de un cliente.
     *
     * * @param idCliente Identificador único del cliente.
     * @return El monto total acumulado de consumo.
     * @throws PersistenciaException Si ocurre un error en el cálculo del total.
     */
    public double totalGastado(Long idCliente) throws PersistenciaException;

    /**
     * Registra una nueva comanda en el sistema.
     *
     * * @param comanda El objeto {@link Comanda} con la información inicial.
     * @return La comanda persistida con sus datos actualizados (incluyendo ID).
     * @throws PersistenciaException Si ocurre un error durante el registro.
     */
    public Comanda registrarComanda(Comanda comanda) throws PersistenciaException;

    /**
     * Actualiza la información de una comanda existente (detalles, total o
     * estado).
     *
     * * @param comanda El objeto comanda con los cambios a aplicar.
     * @return La comanda actualizada.
     * @throws PersistenciaException Si la comanda no existe o falla la
     * actualización.
     */
    public Comanda actualizarComanda(Comanda comanda) throws PersistenciaException;

    /**
     * Elimina una comanda del sistema de persistencia.
     *
     * * @param comanda El objeto comanda a eliminar.
     * @return La comanda que fue eliminada.
     * @throws PersistenciaException Si ocurre un error durante el borrado.
     */
    public Comanda eliminarComanda(Comanda comanda) throws PersistenciaException;

    /**
     * Busca una comanda específica utilizando su identificador único.
     *
     * * @param id Identificador de la comanda.
     * @return La {@link Comanda} encontrada o null si no existe registro.
     * @throws PersistenciaException Si ocurre un error en la búsqueda.
     */
    public Comanda buscarComandaPorId(Long id) throws PersistenciaException;

    /**
     * Recupera el identificador más reciente generado en la tabla de comandas.
     * Útil para procesos de sincronización o validación de folios.
     *
     * * @return El valor máximo de ID encontrado, o 0 si no hay registros.
     */
    public Long obtenerUltimoId();

    /**
     * Localiza la comanda que se encuentra actualmente en estado ABIERTA
     * vinculada a una mesa. Solo puede existir una comanda abierta por mesa a
     * la vez.
     *
     * * @param numeroMesa El número físico de la mesa.
     * @return La {@link Comanda} en curso o null si la mesa está disponible.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    public Comanda buscarComandaAbiertaPorMesa(Integer numeroMesa) throws PersistenciaException;

    /**
     * Obtiene una lista de comandas cuyo registro se encuentre dentro del
     * periodo especificado.
     *
     * * @param inicio Fecha inicial del rango de búsqueda.
     * @param fin Fecha final del rango de búsqueda.
     * @return Lista de comandas que coinciden con el rango de fechas.
     * @throws PersistenciaException Si ocurre un error al filtrar por fechas.
     */
    public List<Comanda> consultarPorRangoFechas(LocalDate inicio, LocalDate fin) throws PersistenciaException;
}
