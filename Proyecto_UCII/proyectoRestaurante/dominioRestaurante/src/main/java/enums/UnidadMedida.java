/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

/**
 * Define las unidades de medida estandarizadas para el control de inventario en el sistema.
 * <p>Este enumerado se utiliza principalmente en la entidad {@link entidades.Ingrediente} 
 * para cuantificar las existencias y en las {@link entidades.Receta} para especificar 
 * las porciones necesarias de cada insumo.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public enum UnidadMedida {
    
    /** * Indica que el insumo se contabiliza por unidades enteras e individuales. */
    PIEZAS,
    
    /** * Unidad de masa utilizada para ingredientes sólidos o polvos. */
    GRAMOS,
    
    /** * Unidad de volumen utilizada para ingredientes líquidos. */
    MILILITROS
}