/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IClienteFrecuenteDAO {

    public List<ClienteFrecuente> obtenerFrecuentes() throws PersistenciaException;

    public List<ClienteFrecuente> buscarPorFiltro(String filtro) throws PersistenciaException;

    public ClienteFrecuente guardar(ClienteFrecuente cliente) throws PersistenciaException;
    
    public ClienteFrecuente buscarPorId(Long id) throws PersistenciaException;
    
    public ClienteFrecuente buscarPorTelefono(String telefono) throws PersistenciaException;
    
    public void eliminarCliente(Long idCliente) throws PersistenciaException;
    
    public ClienteFrecuente buscarClienteFrecuenteGeneral() throws PersistenciaException;
    
    public boolean tieneComandas(Long idCliente) throws PersistenciaException;

}
