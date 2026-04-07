/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.MeseroDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IMeseroBO {
    
    public MeseroDTO buscarMeseroPorUsuario(String usuario) throws NegocioException;
    
    public MeseroDTO registrarMesero(MeseroDTO meseroDTO) throws NegocioException;
    
    public List<MeseroDTO> consultarTodos() throws NegocioException;
    
}
