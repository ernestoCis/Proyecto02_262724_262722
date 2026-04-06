/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocio;

import adaptadores.MeseroAdapter;
import daos.MeseroDAO;
import dtos.MeseroDTO;
import entidades.Mesero;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IMeseroBO;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class MeseroBO implements IMeseroBO{
    
    private static MeseroBO instance;
    
    private MeseroDAO meseroDAO;
    
    private MeseroBO(){
        meseroDAO = MeseroDAO.getInstance();
    }
    
    public static MeseroBO getInstance() {
        if (instance == null) {
            instance = new MeseroBO();
        }
        return instance;
    }

    @Override
    public MeseroDTO buscarMeseroPorUsuario(String usuario) throws NegocioException {
        try{
            Mesero entidadMesero = meseroDAO.consultarMeseroPorUsuario(usuario);
            if(entidadMesero == null){
                throw new NegocioException("No se encontró el usuario: " + usuario);
            }
            
            MeseroDTO meseroDTO = MeseroAdapter.entidadADto(entidadMesero);
                    
            return meseroDTO;
            
        }catch(PersistenciaException e){
            throw new NegocioException("Error al buscar al mesero");
        }
    }
    
}
