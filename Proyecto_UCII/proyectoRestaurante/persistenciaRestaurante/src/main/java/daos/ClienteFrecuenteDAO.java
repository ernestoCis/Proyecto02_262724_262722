/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import interfaces.IClienteFrecuenteDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase de Objeto de Acceso a Datos (DAO) para la entidad ClienteFrecuente.
 * Implementa el patrón Singleton para asegurar una única instancia de acceso a
 * datos y proporciona métodos para realizar operaciones CRUD en la base de
 * datos.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteFrecuenteDAO implements IClienteFrecuenteDAO {

    private static ClienteFrecuenteDAO instance;

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private ClienteFrecuenteDAO() {
    }

    /**
     * Obtiene la instancia única de ClienteFrecuenteDAO.
     *
     * * @return La instancia global de esta clase.
     */
    public static ClienteFrecuenteDAO getInstance() {
        if (instance == null) {
            instance = new ClienteFrecuenteDAO();
        }
        return instance;
    }

    /**
     * Recupera una lista con todos los clientes frecuentes registrados.
     *
     * * @return Lista de objetos {@link ClienteFrecuente}.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    @Override
    public List<ClienteFrecuente> obtenerFrecuentes() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.createQuery(
                    "SELECT c FROM ClienteFrecuente c", ClienteFrecuente.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Busca clientes frecuentes que coincidan con un filtro de texto en nombre,
     * teléfono o email.
     *
     * * @param filtro Cadena de texto a buscar.
     * @return Lista de clientes que coinciden con el criterio.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
    @Override
    public List<ClienteFrecuente> buscarPorFiltro(String filtro) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.createQuery(
                    "SELECT c FROM ClienteFrecuente c "
                    + "WHERE LOWER(c.nombres) LIKE :filtro "
                    + "OR LOWER(c.telefono) LIKE :filtro "
                    + "OR LOWER(c.email) LIKE :filtro",
                    ClienteFrecuente.class)
                    .setParameter("filtro", "%" + filtro.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Persiste un nuevo cliente frecuente en la base de datos.
     *
     * * @param cliente El objeto {@link ClienteFrecuente} a guardar.
     * @return El cliente guardado con su ID generado.
     * @throws PersistenciaException Si la transacción falla.
     */
    @Override
    public ClienteFrecuente guardar(ClienteFrecuente cliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return cliente;

        } finally {
            em.close();
        }
    }

    /**
     * Busca un cliente frecuente por su identificador único.
     *
     * * @param id Identificador del cliente.
     * @return El objeto {@link ClienteFrecuente} encontrado o null si no
     * existe.
     * @throws PersistenciaException Si ocurre un error inesperado.
     */
    @Override
    public ClienteFrecuente buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.find(ClienteFrecuente.class, id);
        } catch (Exception e) {
            throw new PersistenciaException(e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza la información de un cliente frecuente existente.
     *
     * * @param cliente Objeto con los datos actualizados.
     * @throws PersistenciaException Si falla la actualización o se requiere
     * rollback.
     */
    public void actualizar(ClienteFrecuente cliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al actualizar cliente", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca un cliente específico utilizando su número de teléfono.
     *
     * * @param telefono Número telefónico del cliente.
     * @return {@link ClienteFrecuente} asociado al teléfono o null si no se
     * encuentra.
     * @throws PersistenciaException Si ocurre un error en la consulta JPQL.
     */
    @Override
    public ClienteFrecuente buscarPorTelefono(String telefono) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {

            String comandoJPQL = "SELECT c FROM ClienteFrecuente c WHERE telefono = :tel";
            TypedQuery<ClienteFrecuente> query = em.createQuery(comandoJPQL, ClienteFrecuente.class);
            query.setParameter("tel", telefono);

            List<ClienteFrecuente> clientes = query.getResultList();

            if (clientes.isEmpty()) {
                return null;
            }

            return clientes.get(0);

        } catch (Exception e) {
            throw new PersistenciaException("Error al consular clientes por telefono");
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un cliente de la base de datos mediante su ID.
     *
     * * @param idCliente ID del cliente a eliminar.
     * @throws PersistenciaException Si el cliente no existe o tiene
     * restricciones de integridad.
     */
    @Override
    public void eliminarCliente(Long idCliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            ClienteFrecuente cliente = em.find(ClienteFrecuente.class, idCliente);
            if (cliente == null) {
                throw new PersistenciaException("No se encontró el cliente a eliminar.");
            }
//            if(cliente.getComandas() != null || !cliente.getComandas().isEmpty()){
//                throw new PersistenciaException("No se pueden eliminar clientes asociados a una comanda");
//            }

            em.remove(cliente);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al eliminar cliente.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca el registro predefinido como "Cliente general".
     *
     * * @return El objeto {@link ClienteFrecuente} general o null si no está
     * registrado.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public ClienteFrecuente buscarClienteFrecuenteGeneral() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String comandoJPQL = "SELECT c FROM ClienteFrecuente c WHERE nombres = :nom";
            TypedQuery<ClienteFrecuente> query = em.createQuery(comandoJPQL, ClienteFrecuente.class);
            query.setParameter("nom", "Cliente general");

            List<ClienteFrecuente> clientes = query.getResultList();

            if (clientes != null && !clientes.isEmpty()) {
                return clientes.get(0);
            }

            return null;

        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar al cliente general", e);
        } finally {
            em.close();
        }
    }

    /**
     * Verifica si un cliente específico tiene comandas asociadas.
     *
     * * @param idCliente ID del cliente a consultar.
     * @return true si tiene al menos una comanda, false de lo contrario.
     * @throws PersistenciaException Si ocurre un error al consultar las
     * comandas.
     */
    @Override
    public boolean tieneComandas(Long idCliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String comandoJPQL = "SELECT c FROM Comanda c WHERE c.cliente.idCliente = :cliente";
            TypedQuery<ClienteFrecuente> query = em.createQuery(comandoJPQL, ClienteFrecuente.class);
            query.setParameter("cliente", idCliente);

            if (query.getResultList().isEmpty() || query.getResultList() == null) {
                return false;
            }

            return true;

        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar las comandas de los clientes", e);
        } finally {
            em.close();
        }
    }

    /**
     * Realiza una consulta filtrada de clientes frecuentes para la generación
     * de reportes. Utiliza consultas dinámicas (JPQL) para permitir búsquedas
     * por nombre parcial, ignorando mayúsculas y minúsculas para mejorar la
     * experiencia de usuario.
     * * <p>
     * Si el parámetro nombre es nulo o está vacío, el método retorna la lista
     * completa de clientes registrados.</p>
     *
     * * @param nombre Cadena de texto para filtrar por el nombre del cliente
     * (opcional).
     * @return Una lista de objetos {@link ClienteFrecuente} que coinciden con
     * el criterio.
     * @throws PersistenciaException Si ocurre un error crítico al conectar con
     * la base de datos o ejecutar la consulta.
     */
    public List<ClienteFrecuente> consultarReporte(String nombre) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT c FROM ClienteFrecuente c WHERE 1=1";

            if (nombre != null && !nombre.isBlank()) {
                jpql += " AND LOWER(c.nombres) LIKE :nombre";
            }

            TypedQuery<ClienteFrecuente> query = em.createQuery(jpql, ClienteFrecuente.class);

            if (nombre != null && !nombre.isBlank()) {
                query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
