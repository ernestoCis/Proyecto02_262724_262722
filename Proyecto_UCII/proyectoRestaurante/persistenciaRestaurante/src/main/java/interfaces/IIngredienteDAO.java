/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import entidades.Ingrediente;
import enums.UnidadMedida;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IIngredienteDAO {

    public void guardar(Ingrediente ingrediente) throws PersistenciaException;

    public void actualizar(Ingrediente ingrediente) throws PersistenciaException;

    public Ingrediente buscarPorId(Long id) throws PersistenciaException;

    public List<Ingrediente> obtenerIngredientes() throws PersistenciaException;

    public List<Ingrediente> buscarPorFiltro(String filtro) throws PersistenciaException;

    public Ingrediente buscarPorNombreYUnidad(String nombre, UnidadMedida unidad) throws PersistenciaException;
    
    public boolean estaEnUso(Long idIngrediente) throws PersistenciaException;
    
    public void eliminar(Long id) throws PersistenciaException;
    
}