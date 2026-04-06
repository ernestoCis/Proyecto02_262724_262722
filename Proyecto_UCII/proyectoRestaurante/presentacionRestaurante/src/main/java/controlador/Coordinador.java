/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dtos.ClienteFrecuenteDTO;
import dtos.IngredienteDTO;
import excepciones.NegocioException;
import interfaces.ICoordinador;
import java.util.List;
import javax.swing.JOptionPane;
import objetosnegocio.ClienteFrecuenteBO;
import objetosnegocio.IngredienteBO;
import pantallas.*;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class Coordinador implements ICoordinador{
    
    private final ClienteFrecuenteBO clienteFrecuenteBO;
    
    private FrmInicio frmInicio;
    private FrmAcciones frmAcciones;
    private FrmClientes frmClientes;
    private FrmRegistrarCliente frmRegistrarCliente;
    private FrmEditarCliente frmEditarCliente;
    private FrmInicioSesionMesero frmInicioSesionMesero;
    
    private List<ClienteFrecuenteDTO> listaClientesActual;
    private ClienteFrecuenteDTO clienteSeleccionado;
    
    // INGREDIENTES
    private final IngredienteBO ingredienteBO;
    private FrmIngredientes frmIngredientes;
    private List<IngredienteDTO> listaIngredientesActual;
    private  IngredienteDTO ingredienteSeleccionado;
    
    public Coordinador(){
        this.clienteFrecuenteBO = ClienteFrecuenteBO.getInstance();
        this.ingredienteBO = IngredienteBO.getInstance();
    }
    
    @Override
    public void iniciarSistema() {
        if (frmInicio == null) {
            frmInicio = new FrmInicio(this);
        }
        frmInicio.setVisible(true);
    }
    
    @Override
    public void mostrarAcciones(){
        if(frmAcciones == null){
            frmAcciones = new FrmAcciones(this);
        }
        frmAcciones.setVisible(true);
        frmAcciones.toFront();
    }
    
    @Override
    public void mostrarClientes(){
        try{
            this.listaClientesActual = clienteFrecuenteBO.consultarTodos();
            if (frmClientes == null) {
                frmClientes = new FrmClientes(this);
            }
            frmClientes.setVisible(true);
            frmClientes.toFront();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    @Override
    public void mostrarRegistrarCliente(){
        try {
            this.listaClientesActual =  clienteFrecuenteBO.consultarTodos();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        if(frmRegistrarCliente == null){
            frmRegistrarCliente = new FrmRegistrarCliente(this);
        }
        frmRegistrarCliente.setVisible(true);
        frmRegistrarCliente.toFront();
    }
    
    @Override
    public void registrarCliente(ClienteFrecuenteDTO clienteDTO){
        try{
            clienteFrecuenteBO.registrarCliente(clienteDTO);
            JOptionPane.showMessageDialog(null, "Cliente registrado correctamente");
            
            //actualizar tabla
            actualizarTablaClientes();
            
        }catch(NegocioException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        if (frmRegistrarCliente != null) {
            frmRegistrarCliente.dispose();
            frmRegistrarCliente = null;
        }
        frmClientes.setVisible(true);
    }
    
    @Override
    public void actualizarTablaClientes(){
        try{
            List<ClienteFrecuenteDTO> clientes = clienteFrecuenteBO.consultarTodos();

            if (frmClientes != null) {
                frmClientes.actualizarTablaClientes(clientes);
            }
        }catch(NegocioException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    @Override
    public void mostrarEditarCliente(){
        if(clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente primero");
            return;
        }
        
        if(frmEditarCliente != null){
            frmEditarCliente.dispose();
        }
        frmClientes.dispose();
        frmEditarCliente = new FrmEditarCliente(this);
        frmEditarCliente.setVisible(true);
        frmEditarCliente.toFront();
    }
    
    public void mostrarInicioSesionMesero(){
        if(frmInicioSesionMesero == null){
            frmInicioSesionMesero = new FrmInicioSesionMesero(this);
        }
        frmInicioSesionMesero.setVisible(true);
    }
    
    @Override
    public void editarCliente(ClienteFrecuenteDTO clienteDTO){
        try{
            clienteFrecuenteBO.actualizarCliente(clienteDTO);
            JOptionPane.showMessageDialog(null, "Cliente editado correctamente");

            actualizarTablaClientes();

        }catch(NegocioException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        if (frmEditarCliente != null) {
            frmEditarCliente.dispose();
            frmEditarCliente = null;
        }
        frmClientes.setVisible(true);
    }
    
    @Override
    public void setClienteSeleccionado(ClienteFrecuenteDTO cliente) {
        this.clienteSeleccionado = cliente;
    }
    
    @Override
    public ClienteFrecuenteDTO getClienteSeleccionado() {
        return this.clienteSeleccionado;
    }
    
    @Override
    public List<ClienteFrecuenteDTO> getListaClientesActual() {
        return this.listaClientesActual;
    }
    
    @Override
    public void eliminarCliente(){
        try{
            clienteFrecuenteBO.eliminarCliente(clienteSeleccionado);
            JOptionPane.showMessageDialog(null, "Cliente eliminado con exito");
            clienteSeleccionado = null;
            listaClientesActual = clienteFrecuenteBO.consultarTodos();
            actualizarTablaClientes();
        }catch(NegocioException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    // INGREDIENTES 
    @Override
    public void mostrarIngredientes(){
        try{
            this.listaIngredientesActual = ingredienteBO.consultarTodos();
            if (frmIngredientes == null) {
                frmIngredientes = new FrmIngredientes(this);
            }
            frmIngredientes.setVisible(true);
            frmIngredientes.toFront();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    @Override
    public void setIngredienteSeleccionado(IngredienteDTO ingrediente) {
        this.ingredienteSeleccionado = ingrediente;
    }
    
    @Override
    public IngredienteDTO getIngredienteSeleccionado() {
        return this.ingredienteSeleccionado;
    }
    
    @Override
    public List<IngredienteDTO> getListaIngredientesActual() {
        return this.listaIngredientesActual;
    }
    
}
