/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

/**
 * Define los estados de disponibilidad de una mesa en la capa de transporte de datos (DTO).
 * <p>Este enumerado se utiliza para comunicar a la interfaz de usuario si una mesa 
 * puede ser seleccionada para abrir una nueva comanda o si ya se encuentra ocupada/reservada.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public enum EstadoMesaDTO {
    
    /** * Indica que la mesa está libre y lista para ser asignada a una nueva comanda. 
     */
    DISPONIBLE,
    
    /** * Indica que la mesa no está disponible para su uso inmediato. 
     */
    NO_DISPONIBLE
}