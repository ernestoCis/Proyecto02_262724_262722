/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.ClienteDTO;
import excepciones.NegocioException;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IClienteBO {
    
    public ClienteDTO registrarClienteGeneral(ClienteDTO clienteDTO) throws NegocioException;
}
