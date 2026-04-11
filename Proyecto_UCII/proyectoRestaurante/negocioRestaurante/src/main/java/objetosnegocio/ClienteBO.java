/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocio;

import adaptadores.ClienteAdapter;
import daos.ClienteDAO;
import dtos.ClienteDTO;
import entidades.Cliente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IClienteBO;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteBO implements IClienteBO{
    
    private static ClienteBO instance;
    
    private ClienteDAO clienteDAO;
    
    private ClienteBO(){
        clienteDAO = ClienteDAO.getInstance();
    }
    
    public static ClienteBO getInstance() {
        if (instance == null) {
            instance = new ClienteBO();
        }
        return instance;
    }

    @Override
    public ClienteDTO registrarClienteGeneral(ClienteDTO clienteDTO) throws NegocioException {
        if(clienteDTO == null){
            throw new NegocioException("Se necesita un cliente para registrar");
        }
        
        try{
            return ClienteAdapter.entidadADto(clienteDAO.registrarClienteGeneral(ClienteAdapter.dtoAEntidad(clienteDTO)));
        }catch(PersistenciaException e){
            throw new NegocioException("Error al registrar al cliente", e);
        }
    }
    
}
