/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.MeseroDTO;
import entidades.Mesero;

/**
 * Clase adaptadora encargada de la transformación de datos para la entidad
 * Mesero. Permite la conversión entre los objetos de persistencia del personal
 * y los objetos de transferencia de datos utilizados en los módulos de login y
 * administración de empleados.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class MeseroAdapter {

    /**
     * Convierte una entidad {@link Mesero} a un {@link MeseroDTO}. Gestiona la
     * normalización del apellido materno para evitar valores nulos en la capa
     * de presentación.
     *
     * * @param entidad La entidad del empleado recuperada de la base de datos.
     * @return Un objeto DTO con la información del mesero, o null si la entidad
     * es nula.
     */
    public static MeseroDTO entidadADto(Mesero entidad) {
        if (entidad == null) {
            return null;
        }
        MeseroDTO dto = new MeseroDTO();
        dto.setUsuario(entidad.getUsuario());
        dto.setIdMesero(entidad.getIdEmpleado());
        dto.setRfc(entidad.getRfc());
        dto.setNombre(entidad.getNombres());
        dto.setApellidoPaterno(entidad.getApellidoPaterno());

        if (entidad.getApellidoMaterno() == null || entidad.getApellidoMaterno().trim().isEmpty()) {
            dto.setApellidoMaterno("");
        } else {
            dto.setApellidoMaterno(dto.getApellidoMaterno());
        }

        return dto;
    }

    /**
     * Transforma un {@link MeseroDTO} en una entidad {@link Mesero}.
     * Reconstruye el objeto de dominio necesario para realizar operaciones de
     * persistencia, manteniendo la integridad de las credenciales y el RFC.
     *
     * * @param dto El objeto de transferencia de datos con la información del
     * mesero.
     * @return Una entidad de persistencia lista para JPA.
     */
    public static Mesero dtoAEntidad(MeseroDTO dto) {
        if (dto == null) {
            return null;
        }

        Mesero entidad = new Mesero();
        entidad.setIdEmpleado(dto.getIdMesero());
        entidad.setUsuario(dto.getUsuario());
        entidad.setRfc(dto.getRfc());
        entidad.setNombres(dto.getNombre());
        entidad.setApellidoPaterno(dto.getApellidoPaterno());
        if (dto.getApellidoMaterno() == null || dto.getApellidoMaterno().trim().isEmpty()) {
            entidad.setApellidoMaterno("");
        } else {
            entidad.setApellidoMaterno(dto.getApellidoMaterno());
        }

        return entidad;
    }
}
