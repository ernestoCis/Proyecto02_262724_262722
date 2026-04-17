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
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteFrecuenteBO implements IClienteFrecuenteBO {

    private static ClienteFrecuenteBO instance;

    private ClienteFrecuenteDAO clienteFrecuenteDAO;
    private ComandaDAO comandaDAO;

    private ClienteFrecuenteBO() {
        clienteFrecuenteDAO = ClienteFrecuenteDAO.getInstance();
        comandaDAO = ComandaDAO.getInstance();
    }

    public static ClienteFrecuenteBO getInstance() {
        if (instance == null) {
            instance = new ClienteFrecuenteBO();
        }
        return instance;
    }

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
     * Convierte una entidad ClienteFrecuente a su dtoy calcula los valores
     * faltantes del cliente frecuente
     *
     * @param c
     * @return
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
     */
    private void validarDatos(ClienteFrecuenteDTO dto) throws NegocioException {
        if (dto.getTelefono() == null || dto.getTelefono().isBlank()) {
            throw new NegocioException("El teléfono es obligatorio");
        }
    }

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

    @Override
    public ClienteFrecuenteDTO buscarClienteFrecuenteGeneral() throws NegocioException {
        try {
            return ClienteFrecuenteAdapter.entidadADTO(clienteFrecuenteDAO.buscarClienteFrecuenteGeneral());
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar al cliente general", e);
        }
    }

    public List<ClienteFrecuenteDTO> consultarReporte(String nombre, Integer minimoVisitas) throws NegocioException {
        try {
            List<ClienteFrecuente> entidades = clienteFrecuenteDAO.consultarReporte(nombre);
            List<ClienteFrecuenteDTO> filtrada = new ArrayList<>();

            for (ClienteFrecuente c : entidades) {
                ClienteFrecuenteDTO dto = convertir_calcular_clientes(c);

                if (minimoVisitas == null || dto.getNumeroVisitas() >= minimoVisitas) {
                    filtrada.add(dto);
                }
            }
            return filtrada;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al generar reporte", e);
        }
    }

    @Override
    public boolean clienteConComandas(Long idCliente) throws NegocioException {
        try {
            return clienteFrecuenteDAO.tieneComandas(idCliente);
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
