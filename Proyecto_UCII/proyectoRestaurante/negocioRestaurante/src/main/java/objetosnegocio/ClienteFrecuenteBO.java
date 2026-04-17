/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocio;

import adaptadores.ClienteFrecuenteAdapter;
import daos.ClienteFrecuenteDAO;
import daos.ComandaDAO;
import dtos.ClienteFrecuenteDTO;
import entidades.ClienteFrecuente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IClienteFrecuenteBO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Implementación de la lógica de negocio para la gestión de Clientes
 * Frecuentes. Coordina las operaciones entre la capa de presentación y la
 * persistencia, aplicando reglas de negocio como el cálculo de puntos de
 * fidelidad y validaciones de integridad.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteFrecuenteBO implements IClienteFrecuenteBO {

    /**
     * Instancia única de la clase (Singleton).
     */
    private static ClienteFrecuenteBO instance;
    /**
     * Acceso a los datos de clientes frecuentes.
     */
    private ClienteFrecuenteDAO clienteFrecuenteDAO;
    /**
     * Acceso a los datos de comandas para cálculos de puntos y visitas.
     */
    private ComandaDAO comandaDAO;

    /**
     * Constructor privado para implementar el patrón Singleton. Inicializa los
     * DAOs necesarios para la operación.
     */
    private ClienteFrecuenteBO() {
        clienteFrecuenteDAO = ClienteFrecuenteDAO.getInstance();
        comandaDAO = ComandaDAO.getInstance();
    }

    /**
     * Obtiene la instancia única de ClienteFrecuenteBO.
     *
     * * @return La instancia de la clase.
     */
    public static ClienteFrecuenteBO getInstance() {
        if (instance == null) {
            instance = new ClienteFrecuenteBO();
        }
        return instance;
    }

    /**
     * Consulta todos los clientes frecuentes registrados y calcula sus
     * métricas.
     *
     * * @return Una lista de {@link ClienteFrecuenteDTO} con la información
     * procesada.
     * @throws NegocioException Si ocurre un error en la capa de persistencia.
     */
    @Override
    public List<ClienteFrecuenteDTO> consultarTodos() throws NegocioException {
        try {
            List<ClienteFrecuente> entidades = clienteFrecuenteDAO.obtenerFrecuentes();
            List<ClienteFrecuenteDTO> dtos = new ArrayList<>();

            for (ClienteFrecuente c : entidades) {
                dtos.add(convertir_calcular_clientes(c));
            }

            return dtos;

        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudieron cargar los clientes frecuentes", e);
        }
    }

    /**
     * Convierte una entidad ClienteFrecuente a su DTO y calcula los valores
     * dinámicos como visitas, total gastado y puntos acumulados.
     *
     * @param c La entidad del cliente a procesar.
     * @return El DTO con los cálculos de fidelidad integrados.
     * @throws NegocioException Si hay errores al consultar datos de comandas.
     */
    private ClienteFrecuenteDTO convertir_calcular_clientes(ClienteFrecuente c) throws NegocioException {

        try {
            ClienteFrecuenteDTO dto = ClienteFrecuenteAdapter.entidadADTO(c);

            int visitas = comandaDAO.contarVisitas(c.getIdCliente());
            double total = comandaDAO.totalGastado(c.getIdCliente());
            int puntos = (int) (total / 20);
            LocalDateTime ultima = comandaDAO.obtenerUltimaComanda(c.getIdCliente());

            dto.setNumeroVisitas(visitas);
            dto.setTotalGastado(total);
            dto.setPuntos(puntos);
            dto.setUltimaComanda(ultima);

            return dto;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al convertir y calcular puntos", e);
        }
    }

    /**
     * Busca clientes frecuentes aplicando un filtro de texto.
     *
     * * @param filtro Cadena de texto para filtrar la búsqueda (nombre o
     * teléfono).
     * @return Lista de DTOs que coinciden con el criterio.
     * @throws NegocioException Si ocurre un error al realizar la búsqueda.
     */
    @Override
    public List<ClienteFrecuenteDTO> consultarClientesPorFiltro(String filtro) throws NegocioException {
        try {
            String busqueda = (filtro == null) ? "" : filtro.trim();

            List<ClienteFrecuente> entidades = clienteFrecuenteDAO.buscarPorFiltro(busqueda);

            List<ClienteFrecuenteDTO> dtos = new ArrayList<>();

            for (ClienteFrecuente c : entidades) {
                dtos.add(convertir_calcular_clientes(c));
            }

            return dtos;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar clientes", e);
        }
    }

    /**
     * Registra un nuevo cliente frecuente previa validación de datos. Verifica
     * que el teléfono no esté duplicado en el sistema.
     *
     * * @param dto El objeto con los datos del cliente a registrar.
     * @return El DTO del cliente registrado con su ID generado.
     * @throws NegocioException Si el teléfono ya existe o los datos son
     * inválidos.
     */
    @Override
    public ClienteFrecuenteDTO registrarCliente(ClienteFrecuenteDTO dto) throws NegocioException {

        validarDatos(dto);

        try {
            //validar que el cliente no este registrado
            ClienteFrecuente existente = clienteFrecuenteDAO.buscarPorTelefono(dto.getTelefono());
            if (existente != null) {
                throw new NegocioException("Ya existe un cliente registrado con el telefono: " + dto.getTelefono());
            }

            ClienteFrecuente entidad = ClienteFrecuenteAdapter.dtoAEntidadNuevo(dto);
            entidad = clienteFrecuenteDAO.guardar(entidad);

            return ClienteFrecuenteAdapter.entidadADTO(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar cliente", e);
        }
    }

    /**
     * Valida que los campos obligatorios y numéricos cumplan con las reglas de
     * negocio.
     *
     * * @param dto DTO del cliente a validar.
     * @throws NegocioException Si el teléfono es nulo o está vacío.
     */
    private void validarDatos(ClienteFrecuenteDTO dto) throws NegocioException {
        if (dto.getTelefono() == null || dto.getTelefono().isBlank()) {
            throw new NegocioException("El teléfono es obligatorio");
        }
    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * * @param dto El DTO con los nuevos datos del cliente.
     * @throws NegocioException Si el ID es nulo o si el nuevo teléfono ya
     * pertenece a otro cliente.
     */
    @Override
    public void actualizarCliente(ClienteFrecuenteDTO dto) throws NegocioException {
        try {

            if (dto.getIdCliente() == null) {
                throw new NegocioException("No se puede actualizar un cliente sin ID.");
            }

            //validar que el cliente no este registrado
            ClienteFrecuente existente = clienteFrecuenteDAO.buscarPorTelefono(dto.getTelefono());

            if (existente != null && !existente.getIdCliente().equals(dto.getIdCliente())) {
                throw new NegocioException("Ya existe un cliente registrado con el telefono: " + dto.getTelefono());
            }

            validarDatos(dto);

            ClienteFrecuente entidad = ClienteFrecuenteAdapter.dtoAEntidadExistente(dto);
            entidad.setIdCliente(dto.getIdCliente());
            clienteFrecuenteDAO.actualizar(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar el cliente frecuente.", e);
        }
    }

    /**
     * Elimina un cliente del sistema.
     *
     * * @param dto DTO del cliente que contiene el ID a eliminar.
     * @throws NegocioException Si el ID es nulo o ocurre un error en
     * persistencia.
     */
    @Override
    public void eliminarCliente(ClienteFrecuenteDTO dto) throws NegocioException {
        if (dto.getIdCliente() == null) {
            throw new NegocioException("No se puede eliminar un cliente sin ID.");
        }

        try {
            //eliminar cliente
            clienteFrecuenteDAO.eliminarCliente(dto.getIdCliente());
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al eliminar el cliente.", e);
        }
    }

    /**
     * Busca los datos del cliente general (público en general).
     *
     * * @return DTO del cliente general.
     * @throws NegocioException Si no se encuentra el registro base.
     */
    @Override
    public ClienteFrecuenteDTO buscarClienteFrecuenteGeneral() throws NegocioException {
        try {
            return ClienteFrecuenteAdapter.entidadADTO(clienteFrecuenteDAO.buscarClienteFrecuenteGeneral());
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar al cliente general", e);
        }
    }

    /**
     * Genera un reporte de clientes filtrado por nombre y/o mínimo de visitas.
     *
     * * @param nombre Nombre del cliente (opcional).
     * @param minimoVisitas Número mínimo de visitas para filtrar (opcional).
     * @return Lista de clientes que cumplen con el criterio del reporte.
     * @throws NegocioException Si ocurre un error al procesar el reporte.
     */
    public List<ClienteFrecuenteDTO> consultarReporte(String nombre, Integer minimoVisitas) throws NegocioException {
        try {
            List<ClienteFrecuente> entidades = clienteFrecuenteDAO.consultarReporte(nombre, minimoVisitas);

            List<ClienteFrecuenteDTO> dtos = new ArrayList<>();

            for (ClienteFrecuente c : entidades) {
                dtos.add(convertir_calcular_clientes(c));
            }

            return dtos;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al generar reporte", e);
        }
    }

    /**
     * Verifica si un cliente tiene comandas asociadas antes de permitir ciertas
     * acciones.
     *
     * * @param idCliente ID del cliente a consultar.
     * @return true si tiene comandas, false en caso contrario.
     * @throws NegocioException Si ocurre un error en la base de datos.
     */
    @Override
    public boolean clienteConComandas(Long idCliente) throws NegocioException {
        try {
            return clienteFrecuenteDAO.tieneComandas(idCliente);
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
