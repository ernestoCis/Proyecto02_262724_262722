package inicio;

//import controlador.Coordinador;

import controlador.Coordinador;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class InicioAplicacion{
    public static void main(String[] args) {
        // Configuramos el LookAndFeel para que se vea "bonita" (System Look)
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { /* Ignorar si falla */ }

        // Iniciamos el Coordinador
        Coordinador coordinador = new Coordinador();
        
        // Abrimos la pantalla inicial
        coordinador.iniciarSistema();
    }
}