/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Mesa;
import excepciones.PersistenciaException;
import interfaces.IMesaDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class MesaDAO implements IMesaDAO{
    
    private static MesaDAO instance;
    
    public MesaDAO() {
    }
    
    public static MesaDAO getInstance() {
        if (instance == null) {
            instance = new MesaDAO();
        }
        return instance;
    }

    @Override
    public Mesa registrarMesa(Mesa mesa) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            
            em.persist(mesa);
            
            em.getTransaction().commit();
            
            return mesa;
            
        }catch(Exception e){
            throw new PersistenciaException("Error al registrar la mesa", e);
        }finally{
            em.close();
        }
    }

    @Override
    public List<Mesa> consultarTodas() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            String consultaJPQL = "SELECT m FROM Mesa m";
            TypedQuery<Mesa> query = em.createQuery(consultaJPQL, Mesa.class);
            
            return query.getResultList();
        }catch(Exception e){
            throw new PersistenciaException("Error al consultar las mesas", e);
        }finally{
            em.close();
        }
    }

    @Override
    public Mesa consultarMesaPorNumero(Integer numeroMesa) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            String consultaJPQL = "SELECT m FROM Mesa m WHERE numero = :num";
            TypedQuery<Mesa> query = em.createQuery(consultaJPQL, Mesa.class);
            query.setParameter("num", numeroMesa);
            
            List<Mesa> mesasEncontradas = query.getResultList();
            
            if(mesasEncontradas.isEmpty()){
                return null;
            }
            
            return mesasEncontradas.get(0);
            
        }catch(Exception e){
            throw new PersistenciaException("Error al consultar el numero de mesa", e);
        }finally{
            em.close();
        }
    }
    
}
