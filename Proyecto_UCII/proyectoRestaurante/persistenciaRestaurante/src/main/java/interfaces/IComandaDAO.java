/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Comanda;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.List;

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
    
    public Comanda buscarComandaAbiertaPorMesa(Integer numeroMesa) throws PersistenciaException;
    
    public List<Comanda> consultarPorRangoFechas(LocalDate inicio, LocalDate fin) throws PersistenciaException;
}
