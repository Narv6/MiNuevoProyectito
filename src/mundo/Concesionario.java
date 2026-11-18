
package mundo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import excepciones.DatosVehiculoInvalidosException;
import excepciones.VehiculoNoEncontradoException;

/**
 * Administra el inventario, la cola de taller y la pila de ventas.
 */
public class Concesionario
{
    // INVENTARIO CON LISTA DOBLEMENTE ENLAZADA

    private ListaDobleVehiculos inventarioDoble;

    // COLA DE TALLER (FIFO)

    private Queue<Vehiculo> colaTaller;

    // PILA DE VENTAS (LIFO)

    private Stack<Vehiculo> pilaVentas;

    // CONSTRUCTOR

    public Concesionario()
    {
        this.inventarioDoble = new ListaDobleVehiculos();
        this.colaTaller = new LinkedList<>();
        this.pilaVentas = new Stack<>();
    }

    // INVENTARIO

    public void agregarVehiculoInventario(Vehiculo vehiculo) throws DatosVehiculoInvalidosException
    {
        if (vehiculo == null)
        {
            throw new DatosVehiculoInvalidosException("No se puede agregar un vehiculo nulo.");
        }

        inventarioDoble.agregarAlFinal(vehiculo);
    }

    public ListaDobleVehiculos getInventarioDoble()
    {
        return inventarioDoble;
    }

    // BUSCAR VEHICULO POR POSICION

    public Vehiculo obtenerVehiculoPorPosicion(int posicion) throws VehiculoNoEncontradoException
    {
        NodoDobleVehiculo actual = inventarioDoble.getCabeza();
        int contador = 1;

        while (actual != null)
        {
            if (contador == posicion)
            {
                return actual.getDato();
            }

            contador++;
            actual = actual.getSiguiente();
        }

        throw new VehiculoNoEncontradoException("No existe un vehiculo en esa posicion.");
    }

    // COLA DE TALLER

    public void registrarVehiculoEnTaller(Vehiculo vehiculo)
            throws DatosVehiculoInvalidosException, VehiculoNoEncontradoException
    {
        if (vehiculo == null || !contieneEnInventario(vehiculo))
        {
            throw new VehiculoNoEncontradoException("El vehiculo no esta en el inventario.");
        }

        if (colaTaller.contains(vehiculo))
        {
            throw new DatosVehiculoInvalidosException("El vehiculo ya esta en la cola de taller.");
        }

        colaTaller.offer(vehiculo);
    }

    public Vehiculo atenderSiguienteVehiculoTaller()
    {
        if (colaTaller.isEmpty())
        {
            return null;
        }

        return colaTaller.poll();
    }

    public Queue<Vehiculo> getColaTaller()
    {
        return new LinkedList<>(colaTaller);
    }

    // PILA DE VENTAS

    public void registrarVenta(Vehiculo vehiculo) throws VehiculoNoEncontradoException
    {
        if (vehiculo == null || !contieneEnInventario(vehiculo))
        {
            throw new VehiculoNoEncontradoException("El vehiculo no esta en el inventario.");
        }

        // Si el vehiculo estaba esperando taller, se retira de esa cola.

        colaTaller.remove(vehiculo);
        inventarioDoble.eliminar(vehiculo);
        pilaVentas.push(vehiculo);
    }

    public Vehiculo cancelarUltimaVenta()
    {
        if (pilaVentas.isEmpty())
        {
            return null;
        }

        Vehiculo vehiculo = pilaVentas.pop();
        inventarioDoble.agregarAlFinal(vehiculo);

        return vehiculo;
    }

    public Stack<Vehiculo> getPilaVentas()
    {
        Stack<Vehiculo> copia = new Stack<>();

        for (Vehiculo vehiculo : pilaVentas)
        {
            copia.push(vehiculo);
        }

        return copia;
    }

    // COMPROBAR SI UN VEHICULO ESTA EN EL INVENTARIO

    private boolean contieneEnInventario(Vehiculo vehiculo)
    {
        NodoDobleVehiculo actual = inventarioDoble.getCabeza();

        while (actual != null)
        {
            if (actual.getDato() == vehiculo)
            {
                return true;
            }

            actual = actual.getSiguiente();
        }

        return false;
    }

    // ORDENAMIENTO POR BURBUJA

    public void ordenarInventarioPorPrecio()
    {
        int n = inventarioDoble.getTamanio();

        if (n <= 1)
        {
            return;
        }

        boolean intercambiado;

        for (int i = 0; i < n - 1; i++)
        {
            intercambiado = false;
            NodoDobleVehiculo actual = inventarioDoble.getCabeza();

            for (int j = 0; j < n - i - 1; j++)
            {
                if (actual != null && actual.getSiguiente() != null)
                {
                    Vehiculo v1 = actual.getDato();
                    Vehiculo v2 = actual.getSiguiente().getDato();

                    if (v1.getPrecio() > v2.getPrecio())
                    {
                        actual.setDato(v2);
                        actual.getSiguiente().setDato(v1);
                        intercambiado = true;
                    }
                }

                if (actual != null)
                {
                    actual = actual.getSiguiente();
                }
            }

            if (!intercambiado)
            {
                break;
            }
        }
    }

    // BUSQUEDA BINARIA POR PRECIO

    public Vehiculo buscarVehiculoPorPrecio(double precioBuscado)
    {
        // Primero ordenamos porque la busqueda binaria lo requiere.

        ordenarInventarioPorPrecio();

        ArrayList<Vehiculo> listaTemporal = new ArrayList<>();
        NodoDobleVehiculo actual = inventarioDoble.getCabeza();

        while (actual != null)
        {
            listaTemporal.add(actual.getDato());
            actual = actual.getSiguiente();
        }

        int inicio = 0;
        int fin = listaTemporal.size() - 1;

        while (inicio <= fin)
        {
            int medio = inicio + (fin - inicio) / 2;
            Vehiculo vehiculoMedio = listaTemporal.get(medio);

            if (vehiculoMedio.getPrecio() == precioBuscado)
            {
                return vehiculoMedio;
            }

            if (vehiculoMedio.getPrecio() < precioBuscado)
            {
                inicio = medio + 1;
            }
            else
            {
                fin = medio - 1;
            }
        }

        return null;
    }

    // FILTRADO POR TIPO DE VEHICULO

    public ArrayList<Carro> obtenerCarros()
    {
        ArrayList<Carro> carros = new ArrayList<>();
        NodoDobleVehiculo actual = inventarioDoble.getCabeza();

        while (actual != null)
        {
            if (actual.getDato() instanceof Carro)
            {
                carros.add((Carro) actual.getDato());
            }

            actual = actual.getSiguiente();
        }

        return carros;
    }

    public ArrayList<Moto> obtenerMotos()
    {
        ArrayList<Moto> motos = new ArrayList<>();
        NodoDobleVehiculo actual = inventarioDoble.getCabeza();

        while (actual != null)
        {
            if (actual.getDato() instanceof Moto)
            {
                motos.add((Moto) actual.getDato());
            }

            actual = actual.getSiguiente();
        }

        return motos;
    }

    public ArrayList<Camion> obtenerCamiones()
    {
        ArrayList<Camion> camiones = new ArrayList<>();
        NodoDobleVehiculo actual = inventarioDoble.getCabeza();

        while (actual != null)
        {
            if (actual.getDato() instanceof Camion)
            {
                camiones.add((Camion) actual.getDato());
            }

            actual = actual.getSiguiente();
        }

        return camiones;
    }

    public ArrayList<Bicicleta> obtenerBicicletas()
    {
        ArrayList<Bicicleta> bicicletas = new ArrayList<>();
        NodoDobleVehiculo actual = inventarioDoble.getCabeza();

        while (actual != null)
        {
            if (actual.getDato() instanceof Bicicleta)
            {
                bicicletas.add((Bicicleta) actual.getDato());
            }

            actual = actual.getSiguiente();
        }

        return bicicletas;
    }
}
