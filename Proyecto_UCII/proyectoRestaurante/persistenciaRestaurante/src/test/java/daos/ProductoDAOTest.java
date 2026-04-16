/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Ingrediente;
import entidades.Producto;
import entidades.Receta;
import enums.DisponibilidadProducto;
import enums.TipoProducto;
import enums.UnidadMedida;
import excepciones.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para ProductoDAO. Evalúa la gestión del catálogo
 * de productos, la integridad de las recetas y la lógica de negocio que
 * determina la disponibilidad de un producto en función del stock de sus
 * ingredientes.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ProductoDAOTest {

    private ProductoDAO productoDAO;
    private List<Long> idsProductos;
    private List<Long> idsIngredientes;

    public ProductoDAOTest() {
        this.productoDAO = ProductoDAO.getInstance();
    }

    /**
     * Prepara el entorno antes de cada prueba inicializando las listas de
     * seguimiento de identificadores.
     */
    @BeforeEach
    public void setUp() {
        idsProductos = new ArrayList<>();
        idsIngredientes = new ArrayList<>();
    }

    /**
     * Realiza la limpieza profunda de la base de datos tras cada prueba.
     * Elimina registros en cascada (Recetas, Productos, Ingredientes) para
     * mantener la consistencia y evitar errores de integridad referencial.
     */
    @AfterEach
    public void tearDown() {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            if (!idsProductos.isEmpty()) {
                em.createQuery("DELETE FROM Receta r WHERE r.producto.idProducto IN :ids")
                        .setParameter("ids", idsProductos).executeUpdate();
            }
            for (Long id : idsProductos) {
                Producto p = em.find(Producto.class, id);
                if (p != null) {
                    em.remove(p);
                }
            }
            for (Long id : idsIngredientes) {
                Ingrediente i = em.find(Ingrediente.class, id);
                if (i != null) {
                    em.remove(i);
                }
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Prueba el flujo básico de persistencia y recuperación de un producto.
     * Valida que tras invocar el método guardar, el objeto adquiera un
     * identificador único y que los datos recuperados por ID mantengan la
     * integridad respecto a los valores originales.
     *
     * * @throws PersistenciaException Si ocurre un error durante la escritura
     * o lectura en la base de datos.
     */
    @Test
    public void test1_GuardarYBuscarPorId() throws PersistenciaException {
        Producto p = new Producto("Taco de Asada", 35.0, TipoProducto.PLATILLO, "Clásico", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        Producto resultado = productoDAO.buscarPorId(p.getIdProducto());
        assertNotNull(resultado);
        assertEquals("Taco de Asada", resultado.getNombre());
    }

    /**
     * Verifica la capacidad de modificar los atributos de un producto
     * existente. El test asegura que los cambios realizados en el objeto (como
     * el precio) se sincronicen correctamente en la base de datos mediante el
     * método actualizar.
     *
     * * @throws PersistenciaException Si la transacción de actualización
     * falla.
     */
    @Test
    public void test2_ActualizarProducto() throws PersistenciaException {
        Producto p = new Producto("Agua", 15.0, TipoProducto.BEBIDA, "", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        p.setPrecio(20.0);
        productoDAO.actualizar(p);

        assertEquals(20.0, productoDAO.buscarPorId(p.getIdProducto()).getPrecio());
    }

    /**
     * Evalúa la funcionalidad de búsqueda dinámica utilizando coincidencias
     * parciales. Valida que el motor de persistencia sea capaz de filtrar
     * registros ignorando mayúsculas/minúsculas o nombres incompletos,
     * simulando el comportamiento de una barra de búsqueda en la interfaz de
     * usuario.
     *
     * * @throws PersistenciaException Si la consulta por criterios (Criteria
     * API o JPQL) falla.
     */
    @Test
    public void test3_BuscarPorNombreLike() throws PersistenciaException {
        Producto p = new Producto("Enchiladas Verdes", 100.0, TipoProducto.PLATILLO, "", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        List<Producto> lista = productoDAO.buscarPorNombre("enchi");
        assertFalse(lista.isEmpty());
        assertTrue(lista.get(0).getNombre().contains("Enchiladas"));
    }

    /**
     * Valida la búsqueda por nombre exacto. Nota: En este escenario se verifica
     * que el filtro funcione correctamente cuando un producto existe pero
     * podría no cumplir con otros criterios de búsqueda.
     *
     * @throws PersistenciaException Si ocurre un error en la capa de datos.
     */
    @Test
    public void test4_BuscarPorNombreExactoFiltro() throws PersistenciaException {
        Producto p = new Producto("Flan", 40.0, TipoProducto.POSTRE, "", DisponibilidadProducto.NO_DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        assertNull(productoDAO.buscarPorNombreExacto("Flan"));
    }

    /**
     * Valida que el cambio manual de disponibilidad afecte correctamente al
     * registro. Esencial para funciones administrativas donde se desea ocultar
     * un producto temporalmente.
     *
     * @throws PersistenciaException Si la actualización de estado falla.
     */
    @Test
    public void test5_CambiarDisponibilidadManual() throws PersistenciaException {
        Producto p = new Producto("Temp", 10.0, TipoProducto.POSTRE, "", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        productoDAO.cambiarDisponibilidad(p.getIdProducto(), DisponibilidadProducto.NO_DISPONIBLE);
        assertEquals(DisponibilidadProducto.NO_DISPONIBLE, productoDAO.buscarPorId(p.getIdProducto()).getDisponibilidad());
    }

    /**
     * Verifica que los productos nuevos o sin ingredientes asociados se
     * recuperen con una lista de recetas vacía, evitando errores de puntero
     * nulo.
     *
     * @throws PersistenciaException Si ocurre un error al consultar el
     * producto.
     */
    @Test
    public void test6_BuscarProductoSinRecetas() throws PersistenciaException {
        Producto p = new Producto("Cerveza", 50.0, TipoProducto.BEBIDA, "Botella", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        Producto resultado = productoDAO.buscarPorId(p.getIdProducto());
        assertNotNull(resultado);
        assertTrue(resultado.getRecetas().isEmpty());
    }

    /**
     * Verifica la lógica de stock: un producto debe quedar NO DISPONIBLE si sus
     * ingredientes son insuficientes para preparar al menos una porción.
     *
     * @throws PersistenciaException Si falla el cálculo de disponibilidad.
     */
    @Test
    public void test7_DisponibilidadPorStockInsuficiente() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        em.getTransaction().begin();
        Ingrediente ing = new Ingrediente("Carne", UnidadMedida.GRAMOS, 0.1, "");
        em.persist(ing);
        idsIngredientes.add(ing.getIdIngrediente());
        Producto p = new Producto("Corte 500g", 300.0, TipoProducto.PLATILLO, "", DisponibilidadProducto.DISPONIBLE, "");
        em.persist(p);
        idsProductos.add(p.getIdProducto());
        Receta r = new Receta(0.5, p, ing);
        em.persist(r);
        em.getTransaction().commit();
        em.close();

        productoDAO.actualizarDisponibilidadPorStock(p.getIdProducto());
        assertEquals(DisponibilidadProducto.NO_DISPONIBLE, productoDAO.buscarPorId(p.getIdProducto()).getDisponibilidad());
    }

    /**
     * Evalúa la actualización en cascada. Al agotarse un ingrediente, todos los
     * productos que lo utilizan deben pasar a estado NO DISPONIBLE
     * automáticamente.
     *
     * @throws PersistenciaException Si la actualización masiva falla.
     */
    @Test
    public void test8_ActualizarProductosPorIngrediente() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        em.getTransaction().begin();
        Ingrediente ing = new Ingrediente("Sal", UnidadMedida.GRAMOS, 0.0, "");
        em.persist(ing);
        idsIngredientes.add(ing.getIdIngrediente());
        Producto p = new Producto("Sopa", 30.0, TipoProducto.PLATILLO, "", DisponibilidadProducto.DISPONIBLE, "");
        em.persist(p);
        idsProductos.add(p.getIdProducto());
        em.persist(new Receta(10.0, p, ing));
        em.getTransaction().commit();
        em.close();

        productoDAO.actualizarProductosPorIngrediente(idsIngredientes.get(0));
        assertEquals(DisponibilidadProducto.NO_DISPONIBLE, productoDAO.buscarPorId(idsProductos.get(0)).getDisponibilidad());
    }

    /**
     * Valida la eliminación física del producto y el manejo de excepciones al
     * intentar buscar un registro inexistente (ID eliminado).
     *
     * @throws PersistenciaException Si la eliminación es rechazada.
     */
    @Test
    public void test9_EliminarProducto() throws PersistenciaException {
        Producto p = new Producto("Eliminar Me", 1.0, TipoProducto.POSTRE, "", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        Long id = p.getIdProducto();

        productoDAO.eliminar(id);
        assertThrows(PersistenciaException.class, () -> productoDAO.buscarPorId(id));
    }

    /**
     * Verifica que el método de recuperación de productos disponibles filtre
     * correctamente a aquellos que están marcados como NO_DISPONIBLE.
     *
     * @throws PersistenciaException Si la consulta filtrada falla.
     */
    @Test
    public void test10_ObtenerProductosDisponibles() throws PersistenciaException {
        Producto p1 = new Producto("P1", 10.0, TipoProducto.PLATILLO, "", DisponibilidadProducto.DISPONIBLE, "");
        Producto p2 = new Producto("P2", 10.0, TipoProducto.PLATILLO, "", DisponibilidadProducto.NO_DISPONIBLE, "");

        productoDAO.guardar(p1);
        productoDAO.guardar(p2);

        idsProductos.add(p1.getIdProducto());
        idsProductos.add(p2.getIdProducto());

        List<Producto> disponibles = productoDAO.obtenerProductosDisponibles();

        assertTrue(disponibles.stream().anyMatch(p -> p.getNombre().equals("P1")));
        assertTrue(disponibles.stream().noneMatch(p -> p.getNombre().equals("P2")));
    }

    /**
     * Valida la recuperación total del catálogo de productos.
     *
     * @throws PersistenciaException Si falla la consulta masiva.
     */
    @Test
    public void test11_ObtenerTodosLosProductos() throws PersistenciaException {
        Producto p1 = new Producto("Torta Ahogada", 65.0, TipoProducto.PLATILLO, "", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p1);
        idsProductos.add(p1.getIdProducto());

        List<Producto> todos = productoDAO.obtenerProductos();

        assertFalse(todos.isEmpty(), "La lista de productos no debería estar vacía");
        assertTrue(todos.stream().anyMatch(p -> p.getNombre().equals("Torta Ahogada")));
    }

    /**
     * Comprueba el funcionamiento de los filtros dinámicos por nombre parcial.
     *
     * @throws PersistenciaException Si el procesamiento del filtro falla.
     */
    @Test
    public void test12_ConsultarProductosConFiltro() throws PersistenciaException {
        Producto p1 = new Producto("Taco de Pastor", 25.0, TipoProducto.PLATILLO, "", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p1);
        idsProductos.add(p1.getIdProducto());

        List<Producto> filtrados = productoDAO.consultarProductosConFiltro("pastor");

        assertFalse(filtrados.isEmpty());
        assertTrue(filtrados.get(0).getNombre().toLowerCase().contains("pastor"));
    }

    /**
     * Verifica la intersección de filtros: debe buscar por nombre parcial pero
     * solo entre aquellos productos que tengan stock disponible.
     *
     * @throws PersistenciaException Si la búsqueda compuesta falla.
     */
    @Test
    public void test13_ConsultarProductosDisponiblesConFiltro() throws PersistenciaException {
        Producto p1 = new Producto("Jugo Verde", 35.0, TipoProducto.BEBIDA, "", DisponibilidadProducto.DISPONIBLE, "");
        Producto p2 = new Producto("Jugo de Naranja", 35.0, TipoProducto.BEBIDA, "", DisponibilidadProducto.NO_DISPONIBLE, "");

        productoDAO.guardar(p1);
        productoDAO.guardar(p2);
        idsProductos.add(p1.getIdProducto());
        idsProductos.add(p2.getIdProducto());

        List<Producto> filtrados = productoDAO.consultarProductosDisponiblesConFiltro("jugo");

        assertTrue(filtrados.stream().anyMatch(p -> p.getNombre().equals("Jugo Verde")));
        assertTrue(filtrados.stream().noneMatch(p -> p.getNombre().equals("Jugo de Naranja")));
    }

    /**
     * Verifica la detección de uso en comandas activas o históricas. Este test
     * es vital para prevenir la eliminación de productos que tienen registros
     * contables (DetallePedido), protegiendo la integridad referencial.
     *
     * @throws PersistenciaException Si ocurre un error en la validación de uso.
     */
    @Test
    public void test14_EstaEnUso() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        em.getTransaction().begin();

        Producto p = new Producto("Nachos", 50.0, TipoProducto.PLATILLO, "", DisponibilidadProducto.DISPONIBLE, "");
        em.persist(p);
        idsProductos.add(p.getIdProducto());

        entidades.Comanda comanda = new entidades.Comanda();
        comanda.setEstado(enums.EstadoComanda.CANCELADA);
        comanda.setFecha(LocalDateTime.now());
        comanda.setFolio("OB-" + System.currentTimeMillis());

        entidades.ClienteFrecuente clienteDummy = new entidades.ClienteFrecuente();
        clienteDummy.setNombres("Prueba");
        clienteDummy.setTelefono("12345");
        em.persist(clienteDummy);
        comanda.setCliente(clienteDummy);
        comanda.setFecha(LocalDateTime.now());
        comanda.setFolio("OB-" + System.currentTimeMillis());

        entidades.Mesero mesero = new entidades.Mesero();
        mesero.setUsuario("" + System.currentTimeMillis());
        em.persist(mesero);
        comanda.setMesero(mesero);
        comanda.setTotal(100.0);

        em.persist(comanda);

        entidades.DetallePedido detalle = new entidades.DetallePedido();
        detalle.setProducto(p);
        detalle.setComanda(comanda);
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(10.0);
        detalle.setSubtotal(20.0);
        em.persist(detalle);

        em.getTransaction().commit();
        em.close();

        boolean enUso = productoDAO.estaEnUso(p.getIdProducto());
        assertTrue(enUso);

        em = ConexionBD.crearConexion();
        em.getTransaction().begin();

        em.remove(em.find(entidades.DetallePedido.class, detalle.getIdDetallePedido()));
        em.remove(em.find(entidades.Comanda.class, comanda.getIdComanda()));
        em.remove(em.find(entidades.ClienteFrecuente.class, clienteDummy.getIdCliente()));
        em.remove(em.find(entidades.Mesero.class, mesero.getIdEmpleado()));
        em.getTransaction().commit();
        em.close();
    }

}
