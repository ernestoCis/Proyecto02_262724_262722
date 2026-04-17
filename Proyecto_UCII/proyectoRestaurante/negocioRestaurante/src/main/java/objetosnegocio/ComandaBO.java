/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocio;

import adaptadores.ComandaAdapter;
import daos.ComandaDAO;
import daos.IngredienteDAO;
import daos.ProductoDAO;
import dtos.ComandaDTO;
import dtos.DetallePedidoDTO;
import dtos.IngredienteDTO;
import dtos.ProductoDTO;
import dtos.RecetaDTO;
import dtos.ReporteComandaDTO;
import entidades.Comanda;
import entidades.DetallePedido;
import entidades.Ingrediente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IComandaBO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Implementación de la lógica de negocio para la gestión de Comandas. Esta
 * clase centraliza las reglas para registrar, actualizar y consultar pedidos,
 * incluyendo la gestión automática de inventario (descuento de ingredientes) y
 * la generación de folios únicos.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ComandaBO implements IComandaBO {

    /**
     * Instancia única de la clase (Patrón Singleton).
     */
    private static ComandaBO instance;
    /**
     * Acceso a datos para persistencia de comandas.
     */
    private ComandaDAO comandaDAO;
    /**
     * Acceso a datos para la gestión de stock de ingredientes.
     */

    private IngredienteDAO ingredienteDAO;
    /**
     * Acceso a datos para actualizar la disponibilidad de productos.
     */
    private ProductoDAO productoDAO;

    /**
     * Constructor privado que inicializa los DAOs necesarios.
     */
    private ComandaBO() {
        comandaDAO = ComandaDAO.getInstance();
        ingredienteDAO = IngredienteDAO.getInstance();
        productoDAO = ProductoDAO.getInstance();
    }

    /**
     * Obtiene la instancia única de ComandaBO.
     *
     * @return La instancia de la clase.
     */
    public static ComandaBO getInstance() {
        if (instance == null) {
            instance = new ComandaBO();
        }
        return instance;
    }

    /**
     * Registra una nueva comanda en el sistema. Realiza el cálculo automático
     * del descuento de ingredientes en el inventario basándose en las recetas
     * de los productos incluidos.
     *
     * * @param comanda El DTO con la información de la comanda y sus detalles.
     * @return El DTO de la comanda registrada con ID y folio asignado.
     * @throws NegocioException Si la comanda no tiene productos o si hay error
     * en persistencia.
     */
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

            //restar ingredientes
            for (DetallePedidoDTO detalle : comanda.getDetalles()) {
                ProductoDTO producto = detalle.getProductoDTO();
                int cantidadComanda = detalle.getCantidad();

                if (producto.getRecetas() != null && !producto.getRecetas().isEmpty()) {
                    for (RecetaDTO receta : producto.getRecetas()) {
                        IngredienteDTO ingrediente = receta.getIngrediente();

                        //cant a restar
                        double cantidadARestar = receta.getCantidad() * cantidadComanda;

                        //restar
                        Ingrediente ingredienteActualizado = ingredienteDAO.buscarPorId(ingrediente.getIdIngrediente());
                        ingredienteActualizado.setCantidadActual(ingredienteActualizado.getCantidadActual() - cantidadARestar);
                        ingredienteDAO.actualizar(ingredienteActualizado);

                        productoDAO.actualizarDisponibilidadPorStock(producto.getIdProducto());
                    }
                }

            }

            return comanda;
        } catch (PersistenciaException e) {
            e.printStackTrace();
            throw new NegocioException("Error al registrar la comanda", e);
        }
    }

    /**
     * Actualiza una comanda existente y ajusta el inventario. La lógica de
     * inventario solo resta la diferencia de ingredientes si se agregaron más
     * unidades de un producto a una comanda ya abierta.
     *
     * * @param comandaDTO El DTO con los datos actualizados.
     * @return El DTO de la comanda tras ser persistida.
     * @throws NegocioException Si la comanda no existe o el ID es nulo.
     */
    @Override
    public ComandaDTO actualizarComanda(ComandaDTO comandaDTO) throws NegocioException {
        try {
            if (comandaDTO.getIdComanda() == null) {
                throw new NegocioException("No se puede actualizar una comanda sin ID");
            }

            // 1. Obtener el estado previo de la comanda para saber qué ya se había restado
            Comanda comandaAnterior = comandaDAO.buscarComandaPorId(comandaDTO.getIdComanda());
            if (comandaAnterior == null) {
                throw new NegocioException("La comanda no existe.");
            }

            // 2. Actualizar la comanda en la base de datos
            Comanda entidadActualizada = comandaDAO.actualizarComanda(ComandaAdapter.dtoAEntidad(comandaDTO));
            ComandaDTO comandaActualizadaDTO = ComandaAdapter.entidadADto(entidadActualizada);

            // 3. Lógica de resta de ingredientes (Solo consumos nuevos)
            for (DetallePedidoDTO detalleNuevo : comandaActualizadaDTO.getDetalles()) {
                ProductoDTO producto = detalleNuevo.getProductoDTO();
                int cantidadNueva = detalleNuevo.getCantidad();

                // Buscar si este producto ya existía en la versión anterior
                int cantidadAnterior = 0;
                for (DetallePedido detalleAntiguo : comandaAnterior.getDetalles()) {
                    if (detalleAntiguo.getProducto().getIdProducto().equals(producto.getIdProducto())) {
                        cantidadAnterior = detalleAntiguo.getCantidad();
                        break;
                    }
                }

                // Solo restamos si la cantidad actual es mayor a la que ya se había procesado
                if (cantidadNueva > cantidadAnterior) {
                    int cantidadARestar = cantidadNueva - cantidadAnterior;

                    if (producto.getRecetas() != null && !producto.getRecetas().isEmpty()) {
                        for (RecetaDTO receta : producto.getRecetas()) {
                            IngredienteDTO ingredienteDTO = receta.getIngrediente();

                            // Calculamos el gasto adicional
                            double gastoAdicional = receta.getCantidad() * cantidadARestar;

                            // Actualizar el inventario
                            Ingrediente ingredienteBD = ingredienteDAO.buscarPorId(ingredienteDTO.getIdIngrediente());
                            ingredienteBD.setCantidadActual(ingredienteBD.getCantidadActual() - gastoAdicional);
                            ingredienteDAO.actualizar(ingredienteBD);
                            productoDAO.actualizarDisponibilidadPorStock(producto.getIdProducto());
                        }
                    }
                }
            }

            return comandaActualizadaDTO;

        } catch (PersistenciaException e) {
            e.printStackTrace();
            throw new NegocioException("Error al intentar actualizar la comanda", e);
        }
    }

    /**
     * Elimina una comanda del sistema.
     *
     * * @param comanda DTO de la comanda a eliminar (requiere ID).
     * @return El DTO de la comanda que ha sido eliminada.
     * @throws NegocioException Si el ID es nulo o hay falla en la base de
     * datos.
     */
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

    /**
     * Busca una comanda específica por su identificador único.
     *
     * * @param comanda DTO que contiene el ID a buscar.
     * @return El DTO con toda la información de la comanda encontrada.
     * @throws NegocioException Si no se encuentra la comanda.
     */
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

    /**
     * Genera un folio único para la comanda siguiendo el patrón OB-YYYYMMDD-ID.
     *
     * * @return Una cadena de texto que representa el folio único.
     * @throws NegocioException Si hay errores al consultar el último ID.
     */
    @Override
    public String generarFolio() throws NegocioException {
        LocalDate hoy = LocalDate.now();
        String fechaParte = hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Long siguienteId = comandaDAO.obtenerUltimoId() + 1;

        // Retorna OB-20260408-XXX donde XXX es el ID global
        return String.format("OB-%s-%d", fechaParte, siguienteId);
    }

    /**
     * Recupera una comanda que aún no ha sido cerrada para una mesa específica.
     *
     * * @param numeroMesa El número de la mesa a consultar.
     * @return DTO de la comanda abierta o {@code null} si no hay ninguna.
     * @throws NegocioException Si el número de mesa es nulo o hay error de
     * persistencia.
     */
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

    /**
     * Obtiene una lista de comandas para fines de reporteo dentro de un rango
     * de fechas.
     *
     * * @param inicio Fecha inicial del rango.
     * @param fin Fecha final del rango.
     * @return Lista de {@link ReporteComandaDTO} dentro del periodo.
     * @throws NegocioException Si las fechas son nulas o el rango es inválido.
     */
    @Override
    public List<ReporteComandaDTO> obtenerComandasPorRango(LocalDate inicio, LocalDate fin) throws NegocioException {
        if (inicio == null || fin == null) {
            throw new NegocioException("Las fechas de inicio y fin son obligatorias.");
        }
        if (inicio.isAfter(fin)) {
            throw new NegocioException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        try {
            List<Comanda> entidades = comandaDAO.consultarPorRangoFechas(inicio, fin);

            List<ReporteComandaDTO> dtos = new ArrayList<>();
            for (Comanda entidad : entidades) {
                dtos.add(ComandaAdapter.entidadAReporteDto(entidad));
            }

            return dtos;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener el reporte de comandas", e);
        }
    }

}
