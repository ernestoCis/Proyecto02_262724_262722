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
 * Implementación de la lógica de negocio para la gestión de Productos.
 * Centraliza las validaciones de recetas, el control de inventario (stock
 * suficiente) y la gestión de estados de disponibilidad de los productos del
 * menú.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 * @version 1.0
 */
public class ProductoBO implements IProductoBO {

    /**
     * Instancia única de la clase (Patrón Singleton).
     */
    private static ProductoBO instance;
    /**
     * Objeto de acceso a datos para la persistencia de productos.
     */
    private ProductoDAO productoDAO;

    /**
     * Constructor privado que inicializa el acceso a la capa de persistencia.
     */
    private ProductoBO() {
        productoDAO = ProductoDAO.getInstance();
    }

    /**
     * Obtiene la instancia única de ProductoBO.
     *
     * @return La instancia Singleton de esta clase.
     */
    public static ProductoBO getInstance() {
        if (instance == null) {
            instance = new ProductoBO();
        }
        return instance;
    }

    /**
     * Registra un nuevo producto en el catálogo. Valida que el nombre no esté
     * duplicado y que el producto contenga al menos un ingrediente.
     *
     * * @param dto El objeto de transferencia con los datos del producto.
     * @throws NegocioException Si los datos son inválidos, el nombre ya existe
     * o hay falla en persistencia.
     */
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

    /**
     * Actualiza la información de un producto existente.
     *
     * * @param dto DTO con la información actualizada del producto.
     * @throws NegocioException Si el ID es nulo, los datos son inválidos o el
     * nombre ya pertenece a otro producto.
     */
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

    /**
     * Elimina un producto o cambia su estado a NO_DISPONIBLE si tiene
     * historial. Si el producto está en una comanda abierta o ya ha sido
     * utilizado previamente, se realiza una eliminación lógica para preservar
     * la integridad referencial.
     *
     * * @param id Identificador único del producto a eliminar.
     * @throws NegocioException Si el producto no existe o el ID es nulo.
     */
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

    /**
     * Consulta todos los productos registrados, independientemente de su
     * estado.
     *
     * * @return Una lista de {@link ProductoDTO}.
     * @throws NegocioException Si ocurre un error al cargar los datos.
     */
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

    /**
     * Busca productos cuyo nombre coincida parcialmente con el criterio
     * proporcionado.
     *
     * * @param nombre Nombre o fragmento del nombre a buscar.
     * @return Lista de productos que coinciden con la búsqueda.
     * @throws NegocioException Si hay error en la capa de datos.
     */
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

    /**
     * Cambia el estado de disponibilidad de un producto. Si se intenta marcar
     * como DISPONIBLE, se verifica que exista stock suficiente en los
     * ingredientes de su receta.
     *
     * * @param id ID del producto.
     * @param estado Nuevo estado deseado (DISPONIBLE / NO_DISPONIBLE).
     * @throws NegocioException Si no hay stock suficiente para activar el
     * producto.
     */
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

    /**
     * Valida que los campos obligatorios del producto cumplan con las reglas de
     * negocio.
     *
     * * @param dto DTO a validar.
     * @throws NegocioException Si falta el nombre, precio <= 0, tipo,
     * disponibilidad o ingredientes.
     */
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

    /**
     * Verifica si existe inventario suficiente en todos los ingredientes de la
     * receta para cubrir una cantidad solicitada del producto.
     *
     * * @param dto DTO del producto que contiene la receta.
     * @param cantidadSolicitada Unidades del producto que se desean preparar.
     * @return {@code true} si hay stock suficiente, {@code false} en caso
     * contrario.
     */
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

    /**
     * Consulta únicamente los productos que tienen el estado de DISPONIBLE.
     *
     * * @return Lista de productos disponibles para la venta.
     * @throws NegocioException Si ocurre un error en la persistencia.
     */
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

    /**
     * Consulta productos aplicando un filtro de texto sobre todos los
     * registros.
     *
     * * @param filtro Texto de búsqueda.
     * @return Lista de DTOs filtrados.
     * @throws NegocioException Si hay error al cargar los productos.
     */
    @Override
    public List<ProductoDTO> consultarProductosConFiltro(String filtro) throws NegocioException {
        try {
            return ProductoAdapter.listaEntidadADTO(productoDAO.consultarProductosConFiltro(filtro));
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cargar los productos", e);
        }
    }

    /**
     * Consulta productos aplicando un filtro de texto únicamente sobre los
     * productos disponibles.
     *
     * * @param filtro Texto de búsqueda.
     * @return Lista de DTOs filtrados y disponibles.
     * @throws NegocioException Si hay error al cargar los productos.
     */
    @Override
    public List<ProductoDTO> consultarProductosDisponiblesConFiltro(String filtro) throws NegocioException {
        try {
            return ProductoAdapter.listaEntidadADTO(productoDAO.consultarProductosDisponiblesConFiltro(filtro));
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cargar los productos", e);
        }
    }
}
