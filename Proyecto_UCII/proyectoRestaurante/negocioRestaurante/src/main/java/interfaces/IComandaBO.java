/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.ComandaDTO;
import dtos.ReporteComandaDTO;
import excepciones.NegocioException;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz que define las operaciones de lógica de negocio para la gestión de
 * Comandas. Define el contrato para el ciclo de vida de un pedido, desde su
 * apertura y asignación de folio, hasta la generación de reportes
 * administrativos.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IComandaBO {

    /**
     * Registra una nueva comanda en el sistema. Debe encargarse de asignar la
     * fecha actual, generar el folio correspondiente y realizar el descuento de
     * inventario basado en los productos seleccionados.
     *
     * * @param comanda El objeto DTO con la información del pedido y sus
     * detalles.
     * @return El {@link ComandaDTO} registrado con su ID y folio asignados.
     * @throws NegocioException Si la comanda no contiene productos o hay
     * errores de persistencia.
     */
    public ComandaDTO registrarComanda(ComandaDTO comanda) throws NegocioException;

    /**
     * Actualiza la información de una comanda existente. Se utiliza para
     * agregar productos a una mesa abierta o modificar estados de pago.
     *
     * * @param comanda El DTO con los datos actualizados.
     * @return El {@link ComandaDTO} tras aplicar los cambios en la base de
     * datos.
     * @throws NegocioException Si la comanda no cuenta con un ID válido o no
     * existe.
     */
    public ComandaDTO actualizarComanda(ComandaDTO comanda) throws NegocioException;

    /**
     * Elimina una comanda del registro del sistema.
     *
     * * @param comanda El DTO que contiene el identificador de la comanda a
     * eliminar.
     * @return El {@link ComandaDTO} de la comanda que fue removida.
     * @throws NegocioException Si ocurre un error al intentar eliminar el
     * registro.
     */
    public ComandaDTO eliminarComanda(ComandaDTO comanda) throws NegocioException;

    /**
     * Busca una comanda específica por su identificador único.
     *
     * * @param comanda DTO que contiene el ID a consultar.
     * @return El {@link ComandaDTO} con la información completa del pedido.
     * @throws NegocioException Si no se encuentra ningún registro con el ID
     * proporcionado.
     */
    public ComandaDTO buscarComanda(ComandaDTO comanda) throws NegocioException;

    /**
     * Genera un identificador alfanumérico único (folio) para una nueva
     * comanda. El formato suele incluir la fecha actual y un número secuencial.
     *
     * * @return Una cadena de texto que representa el folio único de la
     * comanda.
     * @throws NegocioException Si hay errores al consultar el contador de
     * registros.
     */
    String generarFolio() throws NegocioException;

    /**
     * Recupera una comanda que se encuentre en estado abierto vinculada a una
     * mesa específica. Útil para retomar pedidos en curso en el área de
     * servicio.
     *
     * * @param numeroMesa El número físico de la mesa a consultar.
     * @return El {@link ComandaDTO} de la comanda abierta, o {@code null} si no
     * hay ninguna.
     * @throws NegocioException Si el número de mesa es nulo o hay errores de
     * conexión.
     */
    public ComandaDTO obtenerComandaAbiertaPorMesa(Integer numeroMesa) throws NegocioException;

    /**
     * Genera una lista de comandas procesadas dentro de un periodo de tiempo
     * determinado.
     *
     * * @param inicio Fecha de inicio del reporte.
     * @param fin Fecha de fin del reporte.
     * @return Una lista de {@link ReporteComandaDTO} con los datos resumidos
     * para administración.
     * @throws NegocioException Si las fechas son nulas o el rango es
     * cronológicamente inválido.
     */
    public List<ReporteComandaDTO> obtenerComandasPorRango(LocalDate inicio, LocalDate fin) throws NegocioException;
}
