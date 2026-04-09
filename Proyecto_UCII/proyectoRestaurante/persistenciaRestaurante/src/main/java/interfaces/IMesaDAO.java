/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Mesa;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IMesaDAO {
    
    public Mesa registrarMesa(Mesa mesa)throws PersistenciaException;
    
    public List<Mesa> consultarTodas() throws PersistenciaException;
    
    public Mesa consultarMesaPorNumero(Integer numeroMesa) throws PersistenciaException;
    
    public Mesa actualizarMesa(Mesa mesa) throws PersistenciaException;
}
