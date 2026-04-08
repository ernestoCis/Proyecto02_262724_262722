package pruebas;

import daos.ProductoDAO;
import entidades.Producto;
import enums.TipoProducto;
import enums.DisponibilidadProducto;

public class PruebaProductos {

    public static void main(String[] args) {

        ProductoDAO productoDAO = ProductoDAO.getInstance();

        try {

            productoDAO.guardar(new Producto("Pizza Pepperoni", 150.0, TipoProducto.PLATILLO, DisponibilidadProducto.DISPONIBLE, null));
            productoDAO.guardar(new Producto("Pizza Hawaiana", 160.0, TipoProducto.PLATILLO, DisponibilidadProducto.DISPONIBLE, null));
            productoDAO.guardar(new Producto("Pizza Mexicana", 170.0, TipoProducto.PLATILLO, DisponibilidadProducto.DISPONIBLE, null));
            productoDAO.guardar(new Producto("Pizza Vegetariana", 155.0, TipoProducto.PLATILLO, DisponibilidadProducto.DISPONIBLE, null));
            productoDAO.guardar(new Producto("Pizza 4 Quesos", 180.0, TipoProducto.PLATILLO, DisponibilidadProducto.DISPONIBLE, null));

            productoDAO.guardar(new Producto("Refresco Coca-Cola", 30.0, TipoProducto.BEBIDA, DisponibilidadProducto.DISPONIBLE, null));
            productoDAO.guardar(new Producto("Refresco Sprite", 30.0, TipoProducto.BEBIDA, DisponibilidadProducto.DISPONIBLE, null));
            productoDAO.guardar(new Producto("Agua Natural", 20.0, TipoProducto.BEBIDA, DisponibilidadProducto.DISPONIBLE, null));
            productoDAO.guardar(new Producto("Agua de Jamaica", 25.0, TipoProducto.BEBIDA, DisponibilidadProducto.DISPONIBLE, null));

            productoDAO.guardar(new Producto("Brownie", 45.0, TipoProducto.POSTRE, DisponibilidadProducto.DISPONIBLE, null));

            System.out.println("Productos insertados correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}