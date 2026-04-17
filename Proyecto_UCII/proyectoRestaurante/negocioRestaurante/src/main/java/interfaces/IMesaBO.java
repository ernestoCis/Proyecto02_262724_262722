/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.MesaDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de lógica de negocio para la gestión de
 * Mesas. Define los métodos necesarios para administrar el inventario de mesas
 * del establecimiento, controlando su registro, identificación y actualización
 * de estado.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IMesaBO {

    /**
     * Registra una nueva mesa en el sistema. Debe validar que el número
     * asignado a la mesa no se encuentre registrado previamente para evitar
     * duplicidad en el área de servicio.
     *
     * * @param mesaDTO El objeto de transferencia con los datos de la mesa a
     * dar de alta.
     * @return El {@link MesaDTO} registrado, incluyendo su identificador único
     * generado.
     * @throws NegocioException Si el número de mesa ya existe o los datos son
     * inconsistentes.
     */
    public MesaDTO registrarMesa(MesaDTO mesaDTO) throws NegocioException;

    /**
     * Recupera una lista con todas las mesas configuradas en el sistema.
     * Permite obtener una visión global del aforo y disposición del
     * restaurante.
     *
     * * @return Lista de {@link MesaDTO} con la información de todas las mesas
     * registradas.
     * @throws NegocioException Si ocurre un error al consultar la persistencia
     * o no hay registros.
     */
    public List<MesaDTO> consultarTodas() throws NegocioException;

    /**
     * Busca una mesa específica utilizando su número identificador comercial.
     *
     * * @param numero El número de mesa (e.g., Mesa 1, Mesa 2) a localizar.
     * @return El {@link MesaDTO} correspondiente al número proporcionado.
     * @throws NegocioException Si no se encuentra la mesa o el número es
     * inválido.
     */
    public MesaDTO buscarMesaPorNumero(Integer numero) throws NegocioException;

    /**
     * Actualiza la información técnica o de estado de una mesa existente. Se
     * utiliza comúnmente para cambiar la disponibilidad o ubicación de la mesa.
     *
     * * @param mesaDTO El DTO con los datos actualizados de la mesa.
     * @return El {@link MesaDTO} tras haber sido persistido con los nuevos
     * cambios.
     * @throws NegocioException Si la mesa no cuenta con ID o si la
     * actualización viola reglas de negocio.
     */
    public MesaDTO actualizarMesa(MesaDTO mesaDTO) throws NegocioException;
}
