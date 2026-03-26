/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import daos.ClienteFrecuenteDAO;
import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class PruebaClientesFrecuentes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClienteFrecuenteDAO dao = new ClienteFrecuenteDAO();

        try {
            ClienteFrecuente cliente = new ClienteFrecuente();
            cliente.setNombres("A");
            cliente.setTelefono("6441112233");
            cliente.setEmail("A@AAA.com");

            dao.guardar(cliente);
            System.out.println("Cliente guardado con ID: " + cliente.getIdCliente());

            List<ClienteFrecuente> lista = dao.obtenerFrecuentes();
            System.out.println("\nLista de clientes:");
            for (ClienteFrecuente c : lista) {
                System.out.println(c.getIdCliente() + " - " + c.getNombres());
            }

            ClienteFrecuente encontrado = dao.buscarPorId(cliente.getIdCliente());
            System.out.println("\nBuscar por ID:");
            System.out.println(encontrado.getNombres());

            List<ClienteFrecuente> filtrados = dao.buscarPorFiltro("A");
            System.out.println("\nBuscar por filtro:");
            for (ClienteFrecuente c : filtrados) {
                System.out.println(c.getNombres());
            }

            cliente.setNombres("A Modificado");
            dao.actualizar(cliente);

            ClienteFrecuente actualizado = dao.buscarPorId(cliente.getIdCliente());
            System.out.println("\nCliente actualizado:");
            System.out.println(actualizado.getNombres());

        } catch (PersistenciaException e) {
            System.out.println("Error en pruebas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}