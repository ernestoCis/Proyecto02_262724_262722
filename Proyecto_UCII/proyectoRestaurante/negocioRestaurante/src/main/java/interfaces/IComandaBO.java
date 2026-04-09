/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.ComandaDTO;
import entidades.Comanda;
import excepciones.NegocioException;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IComandaBO {
    public ComandaDTO registrarComanda(ComandaDTO comanda)throws NegocioException;
    
    public ComandaDTO actualizarComanda(ComandaDTO comanda) throws NegocioException;
    
    public ComandaDTO eliminarComanda(ComandaDTO comanda) throws NegocioException;
    
    public ComandaDTO buscarComanda(ComandaDTO comanda) throws NegocioException;
    
    String generarFolio() throws NegocioException;
    
    public ComandaDTO obtenerComandaAbiertaPorMesa(Integer numeroMesa) throws NegocioException;
}
