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
 * Clase de Objeto de Acceso a Datos (DAO) para la entidad Mesero. Proporciona
 * los métodos necesarios para la gestión del personal, incluyendo la
 * autenticación mediante usuario y el mantenimiento del catálogo de meseros.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class MeseroDAO implements IMeseroDAO {

    private static MeseroDAO instance;

    /**
     * Constructor público de la clase.
     */
    public MeseroDAO() {
    }

    /**
     * Obtiene la instancia única de MeseroDAO utilizando el patrón Singleton.
     *
     * * @return La instancia global de esta clase.
     */
    public static MeseroDAO getInstance() {
        if (instance == null) {
            instance = new MeseroDAO();
        }
        return instance;
    }

    /**
     * Busca un mesero en la base de datos utilizando su nombre de usuario. Este
     * método es fundamental para los procesos de inicio de sesión.
     *
     * * @param usuario Nombre de usuario del mesero a consultar.
     * @return El objeto {@link Mesero} encontrado o null si no existe.
     * @throws PersistenciaException Si ocurre un error durante la ejecución de
     * la consulta.
     */
    @Override
    public Mesero consultarMeseroPorUsuario(String usuario) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String consultaJPQL = "SELECT m FROM Mesero m WHERE m.usuario = :usuarioConsulta";
            TypedQuery<Mesero> query = em.createQuery(consultaJPQL, Mesero.class);
            query.setParameter("usuarioConsulta", usuario);

            List<Mesero> meseros = query.getResultList();

            if (meseros.isEmpty()) {
                return null;
            }

            return meseros.get(0);

        } catch (Exception e) {
            throw new PersistenciaException(e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Registra un nuevo mesero en el sistema de persistencia.
     *
     * * @param mesero El objeto {@link Mesero} con la información a guardar.
     * @return El mesero registrado con su identificador generado.
     * @throws PersistenciaException Si ocurre un error en la transacción o el
     * usuario ya existe.
     */
    @Override
    public Mesero registrarMesero(Mesero mesero) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            em.persist(mesero);

            em.getTransaction().commit();

            return mesero;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al registrar al mesero", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene una lista con todos los meseros registrados en la base de datos.
     *
     * * @return Lista de objetos {@link Mesero}.
     * @throws PersistenciaException Si falla la consulta JPQL.
     */
    @Override
    public List<Mesero> consultarTodos() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String comandoJPQL = "SELECT m FROM Mesero m";

            TypedQuery<Mesero> query = em.createQuery(comandoJPQL, Mesero.class);

            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar la lista de meseros", e);
        } finally {
            em.close();
        }
    }

}
