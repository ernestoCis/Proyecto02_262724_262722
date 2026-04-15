package objetosnegocio;

import adaptadores.ProductoAdapter;
import daos.ProductoDAO;
import dtos.ProductoDTO;
import dtos.RecetaDTO;
import entidades.Ingrediente;
import entidades.Producto;
import enums.DisponibilidadProducto;
import enums.DisponibilidadProductoDTO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IProductoBO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ProductoBO implements IProductoBO {

    private static ProductoBO instance;
    private ProductoDAO productoDAO;

    private ProductoBO() {
        productoDAO = ProductoDAO.getInstance();
    }

    public static ProductoBO getInstance() {
        if (instance == null) {
            instance = new ProductoBO();
        }
        return instance;
    }

    @Override
    public void registrarProducto(ProductoDTO dto) throws NegocioException {
        validarDatos(dto);

        try {
            // validar duplicado
            Producto existente = productoDAO.buscarPorNombreExacto(dto.getNombre());
            if (existente != null) {
                throw new NegocioException("Ya existe un producto con ese nombre activo");
            }

            Producto entidad = ProductoAdapter.dtoAEntidadNuevo(dto);

            productoDAO.guardar(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar producto", e);
        }
    }

    @Override
    public void actualizarProducto(ProductoDTO dto) throws NegocioException {
        if (dto.getIdProducto() == null) {
            throw new NegocioException("No se puede actualizar sin ID");
        }

        validarDatos(dto);

        try {
            Producto existente = productoDAO.buscarPorNombreExacto(dto.getNombre());

            if (existente != null && !existente.getIdProducto().equals(dto.getIdProducto())) {
                throw new NegocioException("Ya existe un producto con ese nombre activo");
            }

            Producto entidad = ProductoAdapter.dtoAEntidadExistente(dto);

            productoDAO.actualizar(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar producto", e);
        }
    }

    @Override
    public void eliminarProducto(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El ID del producto es obligatorio");
        }

        try {
            Producto producto = productoDAO.buscarPorId(id);

            if (producto == null) {
                throw new NegocioException("Producto no encontrado");
            }

            if (productoDAO.estaEnComandaAbierta(id)) {
                productoDAO.cambiarDisponibilidad(id, DisponibilidadProducto.NO_DISPONIBLE);
                return;
            }
            if (productoDAO.estaEnUso(id)) {
                // SOLO DESACTIVAR
                productoDAO.cambiarDisponibilidad(id, DisponibilidadProducto.NO_DISPONIBLE);
                return;
            }

            // productoDAO.eliminar(id);
            productoDAO.cambiarDisponibilidad(id, DisponibilidadProducto.NO_DISPONIBLE);

        } catch (PersistenciaException e) {
            e.printStackTrace();
            throw new NegocioException("Error al eliminar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ProductoDTO> consultarTodos() throws NegocioException {
        try {
            List<Producto> entidades = productoDAO.obtenerProductos();
            List<ProductoDTO> dtos = new ArrayList<>();

            for (Producto p : entidades) {
                dtos.add(ProductoAdapter.entidadADTO(p));
            }

            return dtos;

        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudieron cargar los productos", e);
        }
    }

    @Override
    public List<ProductoDTO> buscarPorNombre(String nombre) throws NegocioException {
        try {
            List<Producto> entidades = productoDAO.buscarPorNombre(nombre);
            List<ProductoDTO> dtos = new ArrayList<>();

            for (Producto p : entidades) {
                dtos.add(ProductoAdapter.entidadADTO(p));
            }

            return dtos;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar productos", e);
        }
    }

    @Override
    public void cambiarDisponibilidad(Long id, DisponibilidadProductoDTO estado) throws NegocioException {
        try {

            if (estado == DisponibilidadProductoDTO.DISPONIBLE) {

                Producto producto = productoDAO.buscarPorId(id);
                ProductoDTO dto = ProductoAdapter.entidadADTO(producto);

                // validación de stock
                if (!hayStockSuficiente(dto, 1)) {
                    throw new NegocioException(
                            "No hay suficiente stock para poner el producto DISPONIBLE"
                    );
                }
            }

            productoDAO.cambiarDisponibilidad(
                    id,
                    enums.DisponibilidadProducto.valueOf(estado.name())
            );

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cambiar disponibilidad", e);
        }
    }

    private void validarDatos(ProductoDTO dto) throws NegocioException {

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new NegocioException("El nombre es obligatorio");
        }

        if (dto.getPrecio() == null || dto.getPrecio() <= 0) {
            throw new NegocioException("El precio debe ser mayor a 0");
        }

        if (dto.getTipo() == null) {
            throw new NegocioException("El tipo es obligatorio");
        }

        if (dto.getDisponibilidad() == null) {
            throw new NegocioException("La disponibilidad es obligatoria");
        }

        if (dto.getRecetas() == null || dto.getRecetas().isEmpty()) {
            throw new NegocioException("El producto debe tener al menos un ingrediente");
        }
    }

    @Override
    public boolean hayStockSuficiente(ProductoDTO dto, int cantidadSolicitada) {
        if (dto.getRecetas() == null || dto.getRecetas().isEmpty()) {
            return true;
        }

        for (RecetaDTO receta : dto.getRecetas()) {
            double necesario = receta.getCantidad() * cantidadSolicitada;

            if (receta.getIngrediente().getCantidadActual() < necesario) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ProductoDTO> consultarProductosDisponibles() throws NegocioException {
        try {
            List<Producto> entidades = productoDAO.obtenerProductosDisponibles();
            List<ProductoDTO> dtos = new ArrayList<>();

            for (Producto p : entidades) {
                dtos.add(ProductoAdapter.entidadADTO(p));
            }

            return dtos;

        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudieron cargar los productos disponibles", e);
        }
    }
}
