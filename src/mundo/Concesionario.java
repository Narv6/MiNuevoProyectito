package mundo;

import java.util.ArrayList;

import excepciones.VehiculoNoEncontradoException;

public class Concesionario {

    // ATRIBUTOS

    private String nombre;
    private ArrayList<Vehiculo> vehiculos;

    // CONSTRUCTOR

    public Concesionario(String nombre) 
    {

        if (nombre == null || nombre.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El nombre del concesionario no puede estar vacío.");
        }

        this.nombre = nombre;
        this.vehiculos = new ArrayList<>();
    }

    // GETTERS Y SETTERS

    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {

        if (nombre == null || nombre.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El nombre del concesionario no puede estar vacío.");
        }

        this.nombre = nombre;
    }

    public ArrayList<Vehiculo> getVehiculos() 
    {
        return vehiculos;
    }

    // MÉTODOS

    public void agregarVehiculo(Vehiculo vehiculo) 
    {

        if (vehiculo == null) 
        {
            throw new IllegalArgumentException("No se puede agregar un vehículo nulo.");
        }

        vehiculos.add(vehiculo);
    }

    public void eliminarVehiculo(Vehiculo vehiculo)
            throws VehiculoNoEncontradoException 
    {

        if (!vehiculos.remove(vehiculo)) 
        {

            throw new VehiculoNoEncontradoException("El vehículo no se encuentra en el concesionario.");
        }
    }

    public Vehiculo buscarPorMarca(String marca)
            throws VehiculoNoEncontradoException {

        for (Vehiculo vehiculo : vehiculos) 
        {

            if (vehiculo.getMarca().equalsIgnoreCase(marca)) 
            {
                return vehiculo;
            }
        }

        throw new VehiculoNoEncontradoException("No se encontró un vehículo de la marca: " + marca);
    }

    public double calcularValorTotal() 
    {

        double total = 0;

        for (Vehiculo vehiculo : vehiculos) 
        {
            total += vehiculo.getPrecio();
        }

        return total;
    }

    public int obtenerCantidadVehiculos() 
    {
        return vehiculos.size();
    }

    public void mostrarVehiculos() 
    {

        for (Vehiculo vehiculo : vehiculos) 
        {
        	System.out.println(vehiculo);
        }
    }

    // TO STRING

    @Override
    public String toString() {

        return "Concesionario [nombre=" + nombre
                + ", cantidadVehiculos=" + vehiculos.size() + "]";
    }
}