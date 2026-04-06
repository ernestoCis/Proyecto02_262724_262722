/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Mesero;
import excepciones.PersistenciaException;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IMeseroDAO {
    public Mesero consultarMeseroPorUsuario(String usuario) throws PersistenciaException;
}
