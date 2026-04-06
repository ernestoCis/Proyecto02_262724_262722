/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dtos.ClienteFrecuenteDTO;
import dtos.MeseroDTO;
import dtos.IngredienteDTO;
import excepciones.NegocioException;
import interfaces.ICoordinador;
import java.util.List;
import javax.swing.JOptionPane;
import objetosnegocio.ClienteFrecuenteBO;
import objetosnegocio.MeseroBO;
import objetosnegocio.IngredienteBO;
import pantallas.*;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class Coordinador implements ICoordinador{
    
    private final ClienteFrecuenteBO clienteFrecuenteBO;
    private final MeseroBO meseroBO;
    
    private FrmInicio frmInicio;
    private FrmAcciones frmAcciones;
    private FrmClientes frmClientes;
    private FrmRegistrarCliente frmRegistrarCliente;
    private FrmEditarCliente frmEditarCliente;
    private FrmInicioSesionMesero frmInicioSesionMesero;
    
    private List<ClienteFrecuenteDTO> listaClientesActual;
    private ClienteFrecuenteDTO clienteSeleccionado;
    private MeseroDTO meseroActual;
    
    // INGREDIENTES
    private final IngredienteBO ingredienteBO;
    private FrmIngredientes frmIngredientes;
    private List<IngredienteDTO> listaIngredientesActual;
    private  IngredienteDTO ingredienteSeleccionado;
    
    public Coordinador(){
        this.clienteFrecuenteBO = ClienteFrecuenteBO.getInstance();
        this.meseroBO = MeseroBO.getInstance();
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
        precargarMeseros();
        frmInicioSesionMesero.setVisible(true);
    }
    
    public void mostrarMesas(String usuario){
        try{
            this.meseroActual = meseroBO.buscarMeseroPorUsuario(usuario);
            
            JOptionPane.showMessageDialog(null, "Pantalla de comandas");
            
        }catch(NegocioException e){
            JOptionPane.showMessageDialog(null, "Usuario invalido");
        }
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
    
    public MeseroDTO getMeseroActual(){
        return meseroActual;
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
    
    //DATOS PRECARGADOS
    public void precargarMeseros() {
        try {
            if (meseroBO.consultarTodos().isEmpty()) {
                JOptionPane.showMessageDialog(null, "pasa");
                MeseroDTO m = new MeseroDTO();
                m.setRfc("CIVJ061128V25");
                m.setApellidoPaterno("Cisneros");
                m.setApellidoMaterno("Valenzuela");
                m.setNombre("Ernesto");
                m.setUsuario("m1");
                meseroBO.registrarMesero(m);
            }
        }catch(NegocioException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "error al precargar los datos");
        }
    }
}
