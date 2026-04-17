/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocio;

import adaptadores.MesaAdapter;
import daos.MesaDAO;
import dtos.MesaDTO;
import entidades.Mesa;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IMesaBO;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la lógica de negocio para la gestión de mesas.
 * Proporciona métodos para el registro, consulta y actualización de las mesas
 * del establecimiento, asegurando que no existan duplicidad en los números de
 * mesa asignados.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class MesaBO implements IMesaBO {

    /**
     * Instancia única de la clase (Patrón Singleton).
     */
    private static MesaBO instance;

    /**
     * Objeto de acceso a datos para la persistencia de mesas.
     */
    private MesaDAO mesaDAO;

    /**
     * Constructor privado que inicializa el acceso a la capa de persistencia.
     */
    private MesaBO() {
        mesaDAO = MesaDAO.getInstance();
    }

    /**
     * Obtiene la instancia única de MesaBO.
     *
     * @return La instancia Singleton de esta clase.
     */
    public static MesaBO getInstance() {
        if (instance == null) {
            instance = new MesaBO();
        }
        return instance;
    }

    /**
     * Registra una nueva mesa en el sistema. Verifica previamente que el número
     * de mesa no se encuentre ya ocupado por otro registro.
     *
     * * @param mesaDTO El objeto de transferencia con los datos de la mesa a
     * registrar.
     * @return El DTO de la mesa registrada, incluyendo su ID generado.
     * @throws NegocioException Si el número de mesa ya existe o si ocurre un
     * error en la persistencia.
     */
    @Override
    public MesaDTO registrarMesa(MesaDTO mesaDTO) throws NegocioException {
        try {
            Mesa existente = mesaDAO.consultarMesaPorNumero(mesaDTO.getNumero());
            if (existente != null) {
                throw new NegocioException("No se puede registrar una mesa con un numero ya existente");
            }

            Mesa guardada = mesaDAO.registrarMesa(MesaAdapter.dtoAEntidad(mesaDTO));

            mesaDTO.setIdMesa(guardada.getIdMesa());

            return mesaDTO;

        } catch (PersistenciaException e) {
            throw new NegocioException("No se puedo registrar la mesa", e);
        }
    }

    /**
     * Consulta todas las mesas registradas en el sistema.
     *
     * * @return Una lista de {@link MesaDTO} con la información de todas las
     * mesas.
     * @throws NegocioException Si no hay mesas registradas o si ocurre un error
     * al consultar la base de datos.
     */
    @Override
    public List<MesaDTO> consultarTodas() throws NegocioException {
        try {
            List<Mesa> mesasEntidad = mesaDAO.consultarTodas();
            List<MesaDTO> mesasDto = new ArrayList<>();

            for (Mesa m : mesasEntidad) {
                mesasDto.add(MesaAdapter.entidadADto(m));
            }

            if (mesasDto.isEmpty()) {
                throw new NegocioException("No hay mesas registradas");
            }

            return mesasDto;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar las mesas", e);
        }
    }

    /**
     * Busca una mesa específica utilizando su número identificador.
     *
     * * @param numero El número de la mesa a buscar.
     * @return El DTO de la mesa encontrada.
     * @throws NegocioException Si ocurre un error durante la consulta en la
     * capa de persistencia.
     */
    @Override
    public MesaDTO buscarMesaPorNumero(Integer numero) throws NegocioException {
        try {
            Mesa mesaEntidad = mesaDAO.consultarMesaPorNumero(numero);

            return MesaAdapter.entidadADto(mesaEntidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar la mesa", e);
        }
    }

    /**
     * Actualiza la información de una mesa existente (por ejemplo, su estado o
     * ubicación).
     *
     * * @param mesaDTO El DTO con los datos actualizados de la mesa.
     * @return El DTO de la mesa tras ser actualizada en la base de datos.
     * @throws NegocioException Si no se puede completar la actualización en la
     * capa de persistencia.
     */
    @Override
    public MesaDTO actualizarMesa(MesaDTO mesaDTO) throws NegocioException {
        try {
            Mesa entidad = MesaAdapter.dtoAEntidad(mesaDTO);
            Mesa actualizada = mesaDAO.actualizarMesa(entidad);
            return MesaAdapter.entidadADto(actualizada);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar la mesa", e);
        }
    }

}
