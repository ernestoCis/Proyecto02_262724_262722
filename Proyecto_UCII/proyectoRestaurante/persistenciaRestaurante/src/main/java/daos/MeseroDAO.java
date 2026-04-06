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

            return query.getSingleResult();
        }catch(Exception e){
            throw new PersistenciaException(e.getMessage());
        }finally{
            em.close();
        }
    }
    
}
