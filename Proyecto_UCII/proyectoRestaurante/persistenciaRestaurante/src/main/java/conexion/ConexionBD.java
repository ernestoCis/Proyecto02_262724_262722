/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase encargada de gestionar la conexión con la base de datos mediante JPA.
 * Utiliza la unidad de persistencia "ConexionPU" para centralizar la creación 
 * de gestores de entidades en el proyecto.
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ConexionBD {

    /**
     * Fábrica de gestores de entidades (EntityManagerFactory) configurada para el proyecto.
     */
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ConexionPU");

    /**
     * Constructor privado para evitar la instanciación de esta clase de utilidad.
     */
    private ConexionBD(){
        
    }

    /**
     * Genera y devuelve un objeto EntityManager para interactuar con el contexto de persistencia.
     * * @return Una instancia de {@link EntityManager} configurada.
     */
    public static EntityManager crearConexion(){
        return entityManagerFactory.createEntityManager();
    }

}