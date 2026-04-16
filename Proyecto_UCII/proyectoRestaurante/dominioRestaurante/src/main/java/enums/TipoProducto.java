package enums;

/**
 * Define las categorías principales a las que puede pertenecer un {@link entidades.Producto}.
 * <p>Este enumerado se utiliza para clasificar los artículos del menú, facilitando 
 * la organización en la interfaz de usuario y la agrupación en reportes de ventas.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public enum TipoProducto {
    
    /** * Representa alimentos preparados que constituyen el cuerpo principal del menú. */
    PLATILLO,
    
    /** * Representa alimentos dulces servidos generalmente al finalizar el consumo de platillos. */
    POSTRE,
    
    /** * Representa cualquier tipo de líquido consumible, ya sea frío, caliente, con o sin alcohol. */
    BEBIDA
}