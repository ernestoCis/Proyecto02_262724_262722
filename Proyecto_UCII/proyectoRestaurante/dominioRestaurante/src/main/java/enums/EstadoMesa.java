/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

/**
 * Define los estados de disponibilidad física y operativa de una {@link entidades.Mesa}.
 * <p>Este enumerado se utiliza para la gestión del flujo de clientes en el establecimiento,
 * permitiendo al personal identificar qué mesas pueden ser asignadas a nuevas comandas.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public enum EstadoMesa {
    
    /** * Indica que la mesa se encuentra libre y lista para recibir a nuevos comensales. */
    DISPONIBLE,
    
    /** * Indica que la mesa no puede ser utilizada, ya sea porque está ocupada por clientes 
     * o porque ha sido reservada o inhabilitada temporalmente. 
     */
    NO_DISPONIBLE
}