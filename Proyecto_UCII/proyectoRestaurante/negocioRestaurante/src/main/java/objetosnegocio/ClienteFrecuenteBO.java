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
import java.util.ArrayList;
import java.util.List;

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
     * Convierte una entidad ClienteFrecuente a su dtoy calcula los valores faltantes del cliente frecuente
     * @param c
     * @return 
     */
    private ClienteFrecuenteDTO convertir_calcular_clientes(ClienteFrecuente c) {

        ClienteFrecuenteDTO dto = ClienteFrecuenteAdapter.entidadADTO(c);

        int visitas = comandaDAO.contarVisitas(c.getIdCliente());
        double total = comandaDAO.totalGastado(c.getIdCliente());
        int puntos = (int) (total / 20);

        dto.setNumeroVisitas(visitas);
        dto.setTotalGastado(total);
        dto.setPuntos(puntos);

        return dto;
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
    public void registrarCliente(ClienteFrecuenteDTO dto) throws NegocioException {

        validarDatos(dto);

        try {
            ClienteFrecuente entidad = ClienteFrecuenteAdapter.dtoAEntidadNuevo(dto);
            clienteFrecuenteDAO.guardar(entidad);

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

        if (dto.getIdCliente() == null) {
            throw new NegocioException("No se puede actualizar un cliente sin ID.");
        }

        validarDatos(dto);
        try {
            ClienteFrecuente entidad = ClienteFrecuenteAdapter.dtoAEntidadExistente(dto);
            entidad.setIdCliente(dto.getIdCliente());
            clienteFrecuenteDAO.actualizar(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar el cliente frecuente.", e);
        }
    }
}
