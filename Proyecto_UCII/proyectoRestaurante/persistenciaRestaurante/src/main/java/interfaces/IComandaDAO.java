/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Comanda;
import excepciones.PersistenciaException;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IComandaDAO {

    public int contarVisitas(Long idCliente) throws PersistenciaException;

    public double totalGastado(Long idCliente) throws PersistenciaException;
    
    public Comanda registrarComanda(Comanda comanda) throws PersistenciaException;
    
    public Comanda actualizarComanda(Comanda comanda) throws PersistenciaException;
    
    public Comanda eliminarComanda(Comanda comanda)  throws PersistenciaException;
    
    public Comanda buscarComandaPorId(Long id)  throws PersistenciaException;
    
    public Long obtenerUltimoId();
}
