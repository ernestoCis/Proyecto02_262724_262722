/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Mesero;
import excepciones.PersistenciaException;
import interfaces.IMeseroDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class MeseroDAO implements IMeseroDAO{
    
    private static MeseroDAO instance;

    public MeseroDAO() {
    }
    
    public static MeseroDAO getInstance() {
        if (instance == null) {
            instance = new MeseroDAO();
        }
        return instance;
    }

    @Override
    public Mesero consultarMeseroPorUsuario(String usuario) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            String consultaJPQL = "SELECT m FROM Mesero m WHERE m.usuario = :usuarioConsulta";
            TypedQuery<Mesero> query = em.createQuery(consultaJPQL, Mesero.class);
            query.setParameter("usuarioConsulta", usuario);
            
            List<Mesero> meseros = query.getResultList();
            
            if(meseros.isEmpty()){
                return null;
            }
            
            return meseros.get(0);

        }catch(Exception e){
            throw new PersistenciaException(e.getMessage());
        }finally{
            em.close();
        }
    }

    @Override
    public void registrarMesero(Mesero mesero) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        
        try{
            em.getTransaction().begin();
            
            em.persist(mesero);
            
            em.getTransaction().commit();
            
        }catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al registrar al mesero", e);
        }finally{
            em.close();
        }
    }

    @Override
    public List<Mesero> consultarTodos() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String comandoJPQL = "SELECT m FROM Mesero m";
            
            TypedQuery<Mesero> query = em.createQuery(comandoJPQL, Mesero.class);

            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar la lista de meseros", e);
        }finally{
            em.close();
        }
    }
    
}
