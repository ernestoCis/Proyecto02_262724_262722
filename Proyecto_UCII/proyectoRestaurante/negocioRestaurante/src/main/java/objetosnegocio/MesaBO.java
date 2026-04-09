/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocio;

import adaptadores.MesaAdapter;
import daos.MesaDAO;
import dtos.MesaDTO;
import entidades.Mesa;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IMesaBO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class MesaBO implements IMesaBO{
    
    private static MesaBO instance;
    
    private MesaDAO mesaDAO;
    
    private MesaBO(){
        mesaDAO = MesaDAO.getInstance();
    }
    
    public static MesaBO getInstance() {
        if (instance == null) {
            instance = new MesaBO();
        }
        return instance;
    }

    @Override
    public MesaDTO registrarMesa(MesaDTO mesaDTO) throws NegocioException {
        try{
            Mesa existente = mesaDAO.consultarMesaPorNumero(mesaDTO.getNumero());
            if(existente != null){
                throw new NegocioException("No se puede registrar una mesa con un numero ya existente");
            }
            
            Mesa guardada = mesaDAO.registrarMesa(MesaAdapter.dtoAEntidad(mesaDTO));
            
            mesaDTO.setIdMesa(guardada.getIdMesa());
            
            return mesaDTO;
            
        }catch(PersistenciaException e){
            throw new NegocioException("No se puedo registrar la mesa", e);
        }
    }

    @Override
    public List<MesaDTO> consultarTodas() throws NegocioException {
        try{
            List<Mesa> mesasEntidad = mesaDAO.consultarTodas();
            List<MesaDTO> mesasDto = new ArrayList<>();
            
            for(Mesa m : mesasEntidad){
                mesasDto.add(MesaAdapter.entidadADto(m));
            }
            
            if(mesasDto.isEmpty()){
                throw new NegocioException("No hay mesas registradas");
            }
            
            return mesasDto;
            
        }catch(PersistenciaException e){
            throw new NegocioException("Error al consultar las mesas", e);
        }
    }

    @Override
    public MesaDTO buscarMesaPorNumero(Integer numero) throws NegocioException {
        try{
            Mesa mesaEntidad = mesaDAO.consultarMesaPorNumero(numero);
            
            return MesaAdapter.entidadADto(mesaEntidad);
            
        }catch(PersistenciaException e){
            throw new NegocioException("Error al consultar la mesa", e);
        }
    }

    @Override
    public MesaDTO actualizarMesa(MesaDTO mesaDTO) throws NegocioException {
        try {
            Mesa entidad = MesaAdapter.dtoAEntidad(mesaDTO);
            Mesa actualizada = mesaDAO.actualizarMesa(entidad);
            return MesaAdapter.entidadADto(actualizada);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar la mesa", e);
        }
    }
    
}
