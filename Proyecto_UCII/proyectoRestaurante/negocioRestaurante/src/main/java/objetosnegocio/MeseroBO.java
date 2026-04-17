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
 * Implementación de la lógica de negocio para la gestión de Meseros. Esta clase
 * se encarga de validar las credenciales de usuario, evitar registros
 * duplicados y coordinar la persistencia de los datos del personal.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class MeseroBO implements IMeseroBO {

    /**
     * Instancia única de la clase (Patrón Singleton).
     */
    private static MeseroBO instance;

    /**
     * Objeto de acceso a datos para la persistencia de meseros.
     */
    private MeseroDAO meseroDAO;

    /**
     * Constructor privado para inicializar el DAO de meseros.
     */
    private MeseroBO() {
        meseroDAO = MeseroDAO.getInstance();
    }

    /**
     * Obtiene la instancia única de MeseroBO.
     *
     * @return La instancia Singleton de esta clase.
     */
    public static MeseroBO getInstance() {
        if (instance == null) {
            instance = new MeseroBO();
        }
        return instance;
    }

    /**
     * Busca un mesero en el sistema utilizando su nombre de usuario único.
     *
     * * @param usuario El nombre de usuario del mesero a buscar.
     * @return El {@link MeseroDTO} correspondiente al usuario encontrado.
     * @throws NegocioException Si el usuario no existe o si ocurre un error en
     * la persistencia.
     */
    @Override
    public MeseroDTO buscarMeseroPorUsuario(String usuario) throws NegocioException {
        try {
            Mesero entidadMesero = meseroDAO.consultarMeseroPorUsuario(usuario);

            if (entidadMesero == null) {
                throw new NegocioException("Usuario no encotrado");
            }

            MeseroDTO meseroDTO = MeseroAdapter.entidadADto(entidadMesero);

            return meseroDTO;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar al mesero");
        }
    }

    /**
     * Registra un nuevo mesero en el sistema previa validación. Verifica que el
     * campo de usuario no sea nulo o vacío y que no exista otro mesero con el
     * mismo nombre de usuario.
     *
     * * @param meseroDTO El objeto con los datos del mesero a registrar.
     * @return El DTO del mesero registrado con su información persistida.
     * @throws NegocioException Si el usuario es obligatorio, ya está registrado
     * o hay error en la base de datos.
     */
    @Override
    public MeseroDTO registrarMesero(MeseroDTO meseroDTO) throws NegocioException {
        if (meseroDTO.getUsuario() == null || meseroDTO.getUsuario().trim().isEmpty()) {
            throw new NegocioException("El usuario es oblihatorio");
        }

        try {
            //validar si el usuario ya esta en la BD
            Mesero existente = meseroDAO.consultarMeseroPorUsuario(meseroDTO.getUsuario());
            if (existente != null) {
                throw new NegocioException("El usuario '" + meseroDTO.getUsuario() + "' ya esta registrado");
            }

            Mesero meseroEntidad = MeseroAdapter.dtoAEntidad(meseroDTO);

            Mesero registrado = meseroDAO.registrarMesero(meseroEntidad);

            return MeseroAdapter.entidadADto(registrado);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar al mesero", e);
        }

    }

    /**
     * Consulta la lista completa de todos los meseros registrados en el
     * sistema.
     *
     * * @return Una lista de {@link MeseroDTO} con todo el personal
     * registrado.
     * @throws NegocioException Si ocurre un error al obtener los datos de la
     * persistencia.
     */
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
