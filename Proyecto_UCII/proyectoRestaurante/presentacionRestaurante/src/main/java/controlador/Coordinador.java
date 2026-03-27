/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dtos.ClienteFrecuenteDTO;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import objetosnegocio.ClienteFrecuenteBO;
import pantallas.*;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class Coordinador {
    private final ClienteFrecuenteBO clienteFrecuenteBO;
    
    private FrmInicio frmInicio;
    private FrmAcciones frmAcciones;
    private FrmClientes frmClientes;
    private FrmRegistrarCliente frmRegistrarCliente;
    private FrmEditarCliente frmEditarCliente;
    
    public Coordinador(){
        this.clienteFrecuenteBO = new ClienteFrecuenteBO();
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
            List<ClienteFrecuenteDTO> clientes = clienteFrecuenteBO.consultarTodos();
            if (frmClientes == null) {
                frmClientes = new FrmClientes(clientes, this);
            }
            frmClientes.setVisible(true);
            frmClientes.toFront();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public void mostrarRegistrarCliente(List<ClienteFrecuenteDTO> clientes){
        if(frmRegistrarCliente == null){
            frmRegistrarCliente = new FrmRegistrarCliente(frmClientes, clientes, this);
        }
        frmRegistrarCliente.setVisible(true);
        frmRegistrarCliente.toFront();
    }
    
    public void registrarCliente(ClienteFrecuenteDTO clienteDTO){
        try{
            clienteFrecuenteBO.registrarCliente(clienteDTO);
            JOptionPane.showMessageDialog(null, "Cliente registrado correctamente");
            
            if(frmRegistrarCliente != null){
                frmRegistrarCliente.dispose();
                frmRegistrarCliente = null;
            }
            
            //actualizar tabla
            actualizarTablaClientes();
            
            frmClientes.setVisible(true);
            
        }catch(NegocioException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
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
    
    public void mostrarEditarCliente(ClienteFrecuenteDTO clienteDTO){
        if(frmEditarCliente != null){
            frmEditarCliente.dispose();
        }
        frmClientes.dispose();
        frmEditarCliente = new FrmEditarCliente(clienteDTO, this);
        frmEditarCliente.setVisible(true);
        frmEditarCliente.toFront();
    }
    
    public void editarCliente(ClienteFrecuenteDTO clienteDTO){
        try{
            clienteFrecuenteBO.actualizarCliente(clienteDTO);
            JOptionPane.showMessageDialog(null, "Cliente editado correctamente");
            
            if(frmEditarCliente != null){
                frmEditarCliente.dispose();
                frmEditarCliente = null;
            }
            
            actualizarTablaClientes();
            
            frmClientes.setVisible(true);
            
        }catch(NegocioException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
}
