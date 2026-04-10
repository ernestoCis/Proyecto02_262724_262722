/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocio;

import adaptadores.ComandaAdapter;
import daos.ComandaDAO;
import dtos.ComandaDTO;
import entidades.Comanda;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IComandaBO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ComandaBO implements IComandaBO{
    
    private static ComandaBO instance;
    
    private ComandaDAO comandaDAO;
    
    private ComandaBO(){
        comandaDAO = ComandaDAO.getInstance();
    }
    
    public static ComandaBO getInstance() {
        if (instance == null) {
            instance = new ComandaBO();
        }
        return instance;
    }

    @Override
    public ComandaDTO registrarComanda(ComandaDTO comanda) throws NegocioException {
        if (comanda.getDetalles() == null || comanda.getDetalles().isEmpty()) {
            throw new NegocioException("No se puede registrar una comanda sin productos.");
        }
        comanda.setIdComanda(null);
        
        comanda.setFecha(LocalDateTime.now());
        
        String folio = generarFolio();
        comanda.setFolio(folio);
        
        try {
            comanda = ComandaAdapter.entidadADto(comandaDAO.registrarComanda(ComandaAdapter.dtoAEntidad(comanda)));
            
            return comanda;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar la comanda", e);
        }
    }

    @Override
    public ComandaDTO actualizarComanda(ComandaDTO comanda) throws NegocioException {
        try {
            if (comanda.getIdComanda() == null) {
                throw new NegocioException("No se puede actualizar una comanda sin ID");
            }

            Comanda entidad = ComandaAdapter.dtoAEntidad(comanda);

            Comanda actualizada = comandaDAO.actualizarComanda(entidad);

            return ComandaAdapter.entidadADto(actualizada);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al intentar actualizar la comanda", e);
        }
    }

    @Override
    public ComandaDTO eliminarComanda(ComandaDTO comanda) throws NegocioException {
        try {
            if (comanda.getIdComanda() == null) {
                throw new NegocioException("Se requiere el ID para eliminar la comanda");
            }

            // Convertimos y mandamos eliminar
            Comanda entidad = ComandaAdapter.dtoAEntidad(comanda);
            Comanda eliminada = comandaDAO.eliminarComanda(entidad);

            return ComandaAdapter.entidadADto(eliminada);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al eliminar la comanda", e);
        }
    }

    @Override
    public ComandaDTO buscarComanda(ComandaDTO comanda) throws NegocioException {
        try {
            Comanda encontrada = comandaDAO.buscarComandaPorId(comanda.getIdComanda());

            if (encontrada == null) {
                throw new NegocioException("No se encontró ninguna comanda con el ID: " + comanda.getIdComanda());
            }

            return ComandaAdapter.entidadADto(encontrada);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar la comanda", e);
        }
    }

    @Override
    public String generarFolio() throws NegocioException {
        LocalDate hoy = LocalDate.now();
        String fechaParte = hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Long siguienteId = comandaDAO.obtenerUltimoId() + 1;

        // Retorna OB-20260408-XXX donde XXX es el ID global
        return String.format("OB-%s-%d", fechaParte, siguienteId);
    }

    @Override
    public ComandaDTO obtenerComandaAbiertaPorMesa(Integer numeroMesa) throws NegocioException {
        try {
            if (numeroMesa == null) {
                throw new NegocioException("El número de mesa es necesario.");
            }

            Comanda entidad = comandaDAO.buscarComandaAbiertaPorMesa(numeroMesa);

            if (entidad == null) {
                return null;
            }

            return ComandaAdapter.entidadADto(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al recuperar la comanda de la mesa " + numeroMesa, e);
        }
    }
    
}
