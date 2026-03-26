/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import interfaces.IComandaDAO;
import jakarta.persistence.EntityManager;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros 
 */
public class ComandaDAO implements IComandaDAO {

    @Override
    public int contarVisitas(Long idCliente) {
        EntityManager em = ConexionBD.crearConexion();

        Long count = em.createQuery(
                "SELECT COUNT(c) FROM Comanda c WHERE c.cliente.idCliente = :id",
                Long.class
        ).setParameter("id", idCliente)
                .getSingleResult();

        return count.intValue();
    }

    @Override
    public double totalGastado(Long idCliente) {
        EntityManager em = ConexionBD.crearConexion();

        Double total = em.createQuery(
                "SELECT COALESCE(SUM(c.total),0) FROM Comanda c WHERE c.cliente.idCliente = :id",
                Double.class
        ).setParameter("id", idCliente)
                .getSingleResult();

        return total;
    }
}