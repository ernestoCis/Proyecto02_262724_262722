/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.MesaDTO;
import entidades.Mesa;
import enums.EstadoMesa;
import enums.EstadoMesaDTO;

/**
 * Clase adaptadora encargada de la transformación de datos para la entidad
 * Mesa. Gestiona la conversión entre el modelo de persistencia y los objetos de
 * transferencia de datos, permitiendo el control de la disponibilidad y la
 * asignación de espacios físicos dentro del sistema del restaurante.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class MesaAdapter {

    /**
     * Convierte un objeto {@link MesaDTO} a su correspondiente entidad
     * {@link Mesa}. Realiza el mapeo manual de los estados de disponibilidad
     * para asegurar que el motor de persistencia reciba los valores correctos
     * definidos en el dominio.
     *
     * * @param dto El objeto de transferencia de datos con la información de
     * la mesa.
     * @return Una entidad de persistencia JPA, o null si el DTO proporcionado
     * es nulo.
     */
    public static Mesa dtoAEntidad(MesaDTO dto) {
        if (dto == null) {
            return null;
        }

        Mesa entidad = new Mesa();
        entidad.setIdMesa(dto.getIdMesa());
        entidad.setNumero(dto.getNumero());

        //enums
        if (dto.getDisponibilidad() == EstadoMesaDTO.DISPONIBLE) {
            entidad.setDisponibilidad(EstadoMesa.DISPONIBLE);
        } else if (dto.getDisponibilidad() == EstadoMesaDTO.NO_DISPONIBLE) {
            entidad.setDisponibilidad(EstadoMesa.NO_DISPONIBLE);
        }

        return entidad;
    }

    /**
     * Transforma una entidad {@link Mesa} de la base de datos a un
     * {@link MesaDTO}. Este proceso es esencial para enviar el estado actual de
     * las mesas a la interfaz de usuario, permitiendo la visualización de mesas
     * libres u ocupadas.
     *
     * * @param entidad La entidad recuperada por la capa de persistencia.
     * @return Un objeto DTO listo para ser consumido por la capa de
     * presentación.
     */
    public static MesaDTO entidadADto(Mesa entidad) {
        if (entidad == null) {
            return null;
        }

        MesaDTO dto = new MesaDTO();
        dto.setIdMesa(entidad.getIdMesa());
        dto.setNumero(entidad.getNumero());
        if (entidad.getDisponibilidad() == EstadoMesa.DISPONIBLE) {
            dto.setDisponibilidad(EstadoMesaDTO.DISPONIBLE);
        } else if (entidad.getDisponibilidad() == EstadoMesa.NO_DISPONIBLE) {
            dto.setDisponibilidad(EstadoMesaDTO.NO_DISPONIBLE);
        }

        return dto;
    }

}
