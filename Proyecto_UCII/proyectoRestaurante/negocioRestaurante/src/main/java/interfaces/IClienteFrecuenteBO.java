/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dtos.ClienteFrecuenteDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IClienteFrecuenteBO {
    
    public List<ClienteFrecuenteDTO> consultarTodos() throws NegocioException;
    
    public List<ClienteFrecuenteDTO> consultarClientesPorFiltro(String filtro) throws NegocioException;
    
    public void registrarCliente(ClienteFrecuenteDTO dto) throws NegocioException;
    
    public void actualizarCliente(ClienteFrecuenteDTO dto) throws NegocioException;
    
    public void eliminarCliente(ClienteFrecuenteDTO dto) throws NegocioException;
    
}
