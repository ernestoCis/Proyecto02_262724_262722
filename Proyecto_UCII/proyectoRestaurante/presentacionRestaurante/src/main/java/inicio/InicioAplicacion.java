package inicio;

import controlador.Coordinador;

/**
 * Punto de entrada principal de la aplicación del sistema de restaurante.
 * <p>
 * Esta clase se encarga de configurar el entorno de ejecución inicial,
 * establecer la apariencia visual del sistema (Look and Feel) y ceder el
 * control al {@link Coordinador} para iniciar la lógica de navegación.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class InicioAplicacion {

    /**
     * Método de arranque del sistema.
     * <p>
     * Realiza las siguientes acciones secuenciales:</p>
     * <ol>
     * <li>Intenta establecer el <b>Look and Feel</b> nativo del sistema
     * operativo para una integración visual óptima.</li>
     * <li>Instancia el {@code Coordinador}, que actúa como el motor central de
     * la aplicación.</li>
     * <li>Ejecuta el método {@code iniciarSistema()} para desplegar la primera
     * pantalla.</li>
     * </ol>
     *
     * * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Configuramos el LookAndFeel para que se vea "bonita" (System Look)
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            /* Ignorar si falla */ }

        // Iniciamos el Coordinador
        Coordinador coordinador = new Coordinador();

        // Abrimos la pantalla inicial
        coordinador.iniciarSistema();
    }
}
