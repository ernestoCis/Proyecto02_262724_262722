/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dtos.ClienteFrecuenteDTO;
import excepciones.NegocioException;
import interfaces.ICoordinador;
import java.util.List;
import javax.swing.JOptionPane;
import objetosnegocio.ClienteFrecuenteBO;
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
    
    public Coordinador(){
        this.clienteFrecuenteBO = ClienteFrecuenteBO.getInstance();
    }
    
    public void iniciarSistema() {
        if (frmInicio == null) {
            frmInicio = new FrmInicio(this);
        }
        frmInicio.setVisible(true);
    }
    
    public void mostrarAcciones(){
        if(frmAcciones == null){
            frmAcciones = new FrmAcciones(this);
        }
        frmAcciones.setVisible(true);
        frmAcciones.toFront();
    }
    
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
    
    public void setClienteSeleccionado(ClienteFrecuenteDTO cliente) {
        this.clienteSeleccionado = cliente;
    }
    
    public ClienteFrecuenteDTO getClienteSeleccionado() {
        return this.clienteSeleccionado;
    }
    
    public List<ClienteFrecuenteDTO> getListaClientesActual() {
        return this.listaClientesActual;
    }
    
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
    
}
