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
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ProductoDAOTest {

    private ProductoDAO productoDAO;
    private List<Long> idsProductos;
    private List<Long> idsIngredientes;

    public ProductoDAOTest() {
        this.productoDAO = ProductoDAO.getInstance();
    }

    @BeforeEach
    public void setUp() {
        idsProductos = new ArrayList<>();
        idsIngredientes = new ArrayList<>();
    }

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

    @Test
    public void test1_GuardarYBuscarPorId() throws PersistenciaException {
        Producto p = new Producto("Taco de Asada", 35.0, TipoProducto.PLATILLO, "Clásico", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        Producto resultado = productoDAO.buscarPorId(p.getIdProducto());
        assertNotNull(resultado);
        assertEquals("Taco de Asada", resultado.getNombre());
    }

    //actualizar datos basicos
    @Test
    public void test2_ActualizarProducto() throws PersistenciaException {
        Producto p = new Producto("Agua", 15.0, TipoProducto.BEBIDA, "", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        p.setPrecio(20.0);
        productoDAO.actualizar(p);

        assertEquals(20.0, productoDAO.buscarPorId(p.getIdProducto()).getPrecio());
    }

    //buscar por nombre con LIKE (Case Insensitive)
    @Test
    public void test3_BuscarPorNombreLike() throws PersistenciaException {
        Producto p = new Producto("Enchiladas Verdes", 100.0, TipoProducto.PLATILLO, "", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        List<Producto> lista = productoDAO.buscarPorNombre("enchi");
        assertFalse(lista.isEmpty());
        assertTrue(lista.get(0).getNombre().contains("Enchiladas"));
    }

    //buscar nombre exacto
    @Test
    public void test4_BuscarPorNombreExactoFiltro() throws PersistenciaException {
        Producto p = new Producto("Flan", 40.0, TipoProducto.POSTRE, "", DisponibilidadProducto.NO_DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        assertNull(productoDAO.buscarPorNombreExacto("Flan"));
    }

    //cambiar disponibilidad manualmente
    @Test
    public void test5_CambiarDisponibilidadManual() throws PersistenciaException {
        Producto p = new Producto("Temp", 10.0, TipoProducto.POSTRE, "", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        productoDAO.cambiarDisponibilidad(p.getIdProducto(), DisponibilidadProducto.NO_DISPONIBLE);
        assertEquals(DisponibilidadProducto.NO_DISPONIBLE, productoDAO.buscarPorId(p.getIdProducto()).getDisponibilidad());
    }

    //producto sin recetas
    @Test
    public void test6_BuscarProductoSinRecetas() throws PersistenciaException {
        Producto p = new Producto("Cerveza", 50.0, TipoProducto.BEBIDA, "Botella", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        idsProductos.add(p.getIdProducto());

        Producto resultado = productoDAO.buscarPorId(p.getIdProducto());
        assertNotNull(resultado);
        assertTrue(resultado.getRecetas().isEmpty());
    }

    //logica de Stock (Faltantes > 0)
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

    //actualizar productos por ingrediente (Efecto Cascada)
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

    @Test
    public void test9_EliminarProducto() throws PersistenciaException {
        Producto p = new Producto("Eliminar Me", 1.0, TipoProducto.POSTRE, "", DisponibilidadProducto.DISPONIBLE, "");
        productoDAO.guardar(p);
        Long id = p.getIdProducto();

        productoDAO.eliminar(id);
        assertThrows(PersistenciaException.class, () -> productoDAO.buscarPorId(id));
    }

    //obtener solo productos disponibles
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

}
