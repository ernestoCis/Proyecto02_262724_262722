/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dtos.IngredienteDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IIngredienteBO {

    public void registrarIngrediente(IngredienteDTO dto) throws NegocioException;

    public void actualizarIngrediente(IngredienteDTO dto) throws NegocioException;

    public List<IngredienteDTO> consultarTodos() throws NegocioException;

    public List<IngredienteDTO> buscarPorFiltro(String filtro) throws NegocioException;
    
    public IngredienteDTO buscarPorId(Long id) throws NegocioException;
    
}
