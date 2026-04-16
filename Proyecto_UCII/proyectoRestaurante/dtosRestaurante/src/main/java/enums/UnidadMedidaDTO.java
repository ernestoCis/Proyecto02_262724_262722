/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

/**
 * Define las unidades de medida estandarizadas para el control de insumos e ingredientes.
 * <p>Este enumerado se utiliza en la gestión de inventarios y recetas para cuantificar 
 * la cantidad de productos de manera precisa según su naturaleza física.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public enum UnidadMedidaDTO {
    
    /** * Representa unidades enteras y contables de un producto o ingrediente. 
     */
    PIEZAS,
    
    /** * Unidad de masa utilizada para ingredientes sólidos o en polvo. 
     */
    GRAMOS,
    
    /** * Unidad de volumen utilizada para ingredientes líquidos o fluidos. 
     */
    MILILITROS
}