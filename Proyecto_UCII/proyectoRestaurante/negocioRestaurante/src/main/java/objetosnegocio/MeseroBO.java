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
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void registrarMesero(MeseroDTO meseroDTO) throws NegocioException {
        if(meseroDTO.getUsuario() == null || meseroDTO.getUsuario().trim().isEmpty()){
            throw new NegocioException("El usuario es oblihatorio");
        }
        
        try{
            //validar si el usuario ya esta en la BD
            Mesero existente = meseroDAO.consultarMeseroPorUsuario(meseroDTO.getUsuario());
            if(existente != null){
                throw new NegocioException("El usuario '" + meseroDTO.getUsuario() + "' ya esta registrado");
            }
            
            Mesero meseroEntidad = MeseroAdapter.dtoAEntidad(meseroDTO);
            
            meseroDAO.registrarMesero(meseroEntidad);
        }catch(PersistenciaException e){
            throw new NegocioException("Error al registrar al mesero", e);
        }
        
    }

    @Override
    public List<MeseroDTO> consultarTodos() throws NegocioException {
        try {
            List<Mesero> listaEntidades = meseroDAO.consultarTodos();

            List<MeseroDTO> listaDtos = new ArrayList<>();

            for (Mesero entidad : listaEntidades) {
                listaDtos.add(MeseroAdapter.entidadADto(entidad));
            }

            return listaDtos;

        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de meseros", e);
        }
    }
    
}
