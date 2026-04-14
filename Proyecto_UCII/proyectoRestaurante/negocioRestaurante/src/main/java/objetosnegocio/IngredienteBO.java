/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocio;

import adaptadores.IngredienteAdapter;
import daos.IngredienteDAO;
import daos.ProductoDAO;
import dtos.IngredienteDTO;
import entidades.Ingrediente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IIngredienteBO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class IngredienteBO implements IIngredienteBO {

    private static IngredienteBO instance;
    private IngredienteDAO ingredienteDAO;
    private ProductoDAO productoDAO;

    private IngredienteBO() {
        ingredienteDAO = IngredienteDAO.getInstance();
        productoDAO = ProductoDAO.getInstance();
    }

    public static IngredienteBO getInstance() {
        if (instance == null) {
            instance = new IngredienteBO();
        }
        return instance;
    }

    @Override
    public void registrarIngrediente(IngredienteDTO dto) throws NegocioException {
        validarDatos(dto);

        try {

            Ingrediente existente = ingredienteDAO.buscarPorNombreYUnidad(dto.getNombre(), enums.UnidadMedida.valueOf(dto.getUnidadMedida().name()));

            if (existente != null) {
                throw new NegocioException("Ya existe un ingrediente con ese nombre y unidad"
                );
            }

            Ingrediente entidad = IngredienteAdapter.dtoAEntidadNuevo(dto);

            ingredienteDAO.guardar(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar ingrediente", e);
        }
    }

    @Override
    public void actualizarIngrediente(IngredienteDTO dto) throws NegocioException {
        if (dto.getIdIngrediente() == null) {
            throw new NegocioException("No se puede actualizar sin ID");
        }

        validarDatos(dto);

        try {

            Ingrediente existente = ingredienteDAO.buscarPorNombreYUnidad(dto.getNombre(), enums.UnidadMedida.valueOf(dto.getUnidadMedida().name()));

            if (existente != null && !existente.getIdIngrediente().equals(dto.getIdIngrediente())) {
                throw new NegocioException("Ya existe un ingrediente con ese nombre y unidad");
            }

            Ingrediente entidad = ingredienteDAO.buscarPorId(dto.getIdIngrediente());
            IngredienteAdapter.actualizarEntidad(dto, entidad);

            ingredienteDAO.actualizar(entidad);
            
            productoDAO.actualizarProductosPorIngrediente(entidad.getIdIngrediente());

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar ingrediente", e);
        }
    }

    @Override
    public List<IngredienteDTO> consultarTodos() throws NegocioException {
        try {
            List<Ingrediente> entidades = ingredienteDAO.obtenerIngredientes();
            List<IngredienteDTO> dtos = new ArrayList<>();

            for (Ingrediente i : entidades) {
                dtos.add(IngredienteAdapter.entidadADTO(i));
            }

            return dtos;

        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudieron cargar los ingredientes", e);
        }
    }

    @Override
    public List<IngredienteDTO> buscarPorFiltro(String filtro) throws NegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void validarDatos(IngredienteDTO dto) throws NegocioException {

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new NegocioException("El nombre es obligatorio");
        }

        if (dto.getUnidadMedida() == null) {
            throw new NegocioException("La unidad es obligatoria");
        }

        if (dto.getCantidadActual() == null || dto.getCantidadActual() < 0) {

            throw new NegocioException("La cantidad debe ser mayor o igual a 0");
        }
    }

    @Override
    public IngredienteDTO buscarPorId(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El ID del ingrediente es obligatorio");
        }

        try {
            Ingrediente entidad = ingredienteDAO.buscarPorId(id);

            if (entidad == null) {
                throw new NegocioException("Ingrediente no encontrado");
            }

            return IngredienteAdapter.entidadADTO(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar ingrediente", e);
        }
    }

    @Override
    public void eliminarIngrediente(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El ID del ingrediente es obligatorio");
        }

        try {
            Ingrediente ingrediente = ingredienteDAO.buscarPorId(id);

            if (ingrediente == null) {
                throw new NegocioException("Ingrediente no encontrado");
            }

            if (ingredienteDAO.estaEnUso(id)) {
                throw new NegocioException("No se puede eliminar el ingrediente porque está en uso en recetas");
            }

            ingredienteDAO.eliminar(id);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al eliminar ingrediente", e);
        }
    }
}
