/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.MeseroDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de lógica de negocio para la gestión de
 * Meseros. Proporciona los métodos necesarios para la administración del
 * personal, incluyendo la autenticación mediante búsqueda de usuario y el
 * control de registros nuevos.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IMeseroBO {

    /**
     * Busca un mesero específico utilizando su nombre de usuario único. Este
     * método es fundamental para procesos de inicio de sesión o validación de
     * credenciales.
     *
     * * @param usuario El nombre de usuario (login) del mesero.
     * @return El {@link MeseroDTO} que coincide con el usuario proporcionado.
     * @throws NegocioException Si el usuario no existe en el sistema o hay
     * errores de persistencia.
     */
    public MeseroDTO buscarMeseroPorUsuario(String usuario) throws NegocioException;

    /**
     * Registra un nuevo mesero en la plataforma. Debe verificar que el nombre
     * de usuario no esté duplicado antes de realizar el guardado.
     *
     * * @param meseroDTO El objeto con la información del nuevo mesero a
     * registrar.
     * @return El {@link MeseroDTO} con los datos persistidos y su identificador
     * asignado.
     * @throws NegocioException Si el usuario ya existe, los campos obligatorios
     * están vacíos o hay errores en la capa de datos.
     */
    public MeseroDTO registrarMesero(MeseroDTO meseroDTO) throws NegocioException;

    /**
     * Recupera una lista completa de todos los meseros registrados.
     *
     * * @return Una lista de {@link MeseroDTO} que representan a todo el
     * personal.
     * @throws NegocioException Si ocurre un error al cargar la información
     * desde la base de datos.
     */
    public List<MeseroDTO> consultarTodos() throws NegocioException;

}
