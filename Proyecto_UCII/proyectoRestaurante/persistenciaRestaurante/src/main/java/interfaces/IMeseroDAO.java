/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Mesero;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Mesero.
 * Proporciona los métodos necesarios para la administración del personal de
 * servicio, permitiendo el registro de nuevos empleados y la recuperación de
 * perfiles para procesos de autenticación en el sistema.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IMeseroDAO {

    /**
     * Busca un mesero específico utilizando su nombre de usuario único. Este
     * método es el componente principal para validar las credenciales de acceso
     * durante el inicio de sesión.
     *
     * * @param usuario El nombre de usuario único del mesero.
     * @return El objeto {@link Mesero} asociado al usuario, o null si no se
     * encuentra.
     * @throws PersistenciaException Si ocurre un error durante la ejecución de
     * la consulta.
     */
    public Mesero consultarMeseroPorUsuario(String usuario) throws PersistenciaException;

    /**
     * Registra un nuevo colaborador en la base de datos del restaurante.
     *
     * * @param mesero El objeto {@link Mesero} con la información personal y
     * credenciales a persistir.
     * @return El mesero registrado con su identificador generado.
     * @throws PersistenciaException Si ocurre un error al intentar guardar el
     * registro.
     */
    public Mesero registrarMesero(Mesero mesero) throws PersistenciaException;

    /**
     * Recupera una lista completa de todos los meseros registrados en el
     * sistema. Útil para la administración de personal y asignación de turnos.
     *
     * * @return Lista de todos los objetos {@link Mesero} existentes.
     * @throws PersistenciaException Si ocurre un error al consultar el catálogo
     * de personal.
     */
    public List<Mesero> consultarTodos() throws PersistenciaException;
}
