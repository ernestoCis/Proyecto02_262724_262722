/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import entidades.Producto;
import enums.DisponibilidadProducto;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IProductoDAO {

    public void guardar(Producto producto) throws PersistenciaException;

    public void actualizar(Producto producto) throws PersistenciaException;

    public Producto buscarPorId(Long id) throws PersistenciaException;

    public List<Producto> obtenerProductos() throws PersistenciaException;

    public List<Producto> buscarPorNombre(String nombre) throws PersistenciaException;

    public Producto buscarPorNombreExacto(String nombre) throws PersistenciaException;

    public void cambiarDisponibilidad(Long id, DisponibilidadProducto estado) throws PersistenciaException;
    
    public boolean estaEnUso(Long idProducto) throws PersistenciaException;
}
