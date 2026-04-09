/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.MesaDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IMesaBO {
    public MesaDTO registrarMesa(MesaDTO mesaDTO)throws NegocioException;
    
    public List<MesaDTO> consultarTodas() throws NegocioException;
    
    public MesaDTO buscarMesaPorNumero(Integer numero) throws NegocioException;
    
    public MesaDTO actualizarMesa(MesaDTO mesaDTO) throws NegocioException;
}
