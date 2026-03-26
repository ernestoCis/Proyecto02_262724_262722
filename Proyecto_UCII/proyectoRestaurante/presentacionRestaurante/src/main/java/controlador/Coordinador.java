/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import pantallas.*;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class Coordinador {
    private FrmInicio frmInicio;
    private FrmAcciones frmAcciones;
    private FrmClientes frmClientes;
    private FrmRegistrarCliente frmRegistrarCliente;
    private FrmEditarCliente frmEditarCliente;
    
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
    }
    
    public void mostrarClientes(){
        if(frmClientes == null){
            frmClientes = new FrmClientes();
        }
        frmClientes.setVisible(true);
    }
    
}
