package mundo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Clase que administra el concesionario, su inventario con lista doble,
 * la cola de taller y la pila de ventas.
 */
public class Concesionario {

    // 1. Inventario manejado mediante Lista Doblemente Enlazada
    private ListaDobleVehiculos inventarioDoble;

    // 2. Cola para los vehículos en espera de mantenimiento (FIFO)
    private Queue<Vehiculo> colaTaller;

    // 3. Pila para el historial de ventas recientes (LIFO)
    private Stack<Vehiculo> pilaVentas;

    /**
     * Constructor del concesionario.
     */
    public Concesionario() {
        this.inventarioDoble = new ListaDobleVehiculos();
        this.colaTaller = new LinkedList<>();
        this.pilaVentas = new Stack<>();
    }

    public void agregarVehiculoInventario(Vehiculo vehiculo) {
        inventarioDoble.agregarAlFinal(vehiculo);
    }

    public ListaDobleVehiculos getInventarioDoble() {
        return inventarioDoble;
    }
    //Colas
    
    public void registrarVehiculoEnTaller(Vehiculo vehiculo) {
        colaTaller.offer(vehiculo); // Encola el vehículo (FIFO)
    }

    public Vehiculo atenderSiguienteVehiculoTaller() {
        if (colaTaller.isEmpty()) {
            return null;
        }
        return colaTaller.poll(); // Retira el primero que llegó
    }

    
    //Pilas 
    public void registrarVenta(Vehiculo vehiculo) {
        pilaVentas.push(vehiculo); // Apila la venta (LIFO)
    }

    public Vehiculo cancelarUltimaVenta() {
        if (pilaVentas.isEmpty()) {
            return null;
        }
        return pilaVentas.pop(); // Saca el último registrado
    }

    // ORDENAMIENTO POR BURBUJA
    
    public void ordenarInventarioPorPrecio() {
        int n = inventarioDoble.getTamanio();
        if (n <= 1) {
            return; // Si está vacía o tiene un solo elemento, ya está ordenada
        }

        boolean intercambiado;
        
        for (int i = 0; i < n - 1; i++) {
            intercambiado = false;
            NodoDobleVehiculo actual = inventarioDoble.getCabeza();
            
            // Recorremos la lista intercambiando datos si es necesario
            for (int j = 0; j < n - i - 1; j++) {
                if (actual != null && actual.getSiguiente() != null) {
                    Vehiculo v1 = actual.getDato();
                    Vehiculo v2 = actual.getSiguiente().getDato();

                    if (v1.getPrecio() > v2.getPrecio()) {
                        // Intercambiamos los datos de los nodos
                        actual.setDato(v2);
                        actual.getSiguiente().setDato(v1);
                        intercambiado = true;
                    }
                }
                if (actual != null) {
                    actual = actual.getSiguiente();
                }
            }
            
            // Optimización: si no hubo intercambios, la lista ya está ordenada
            if (!intercambiado) {
                break;
            }
        }
    }

    // BÚSQUEDA BINARIA
    
    public Vehiculo buscarVehiculoPorPrecio(double precioBuscado) {
        //Primero convertimos temporalmente los nodos a un ArrayList para aplicar índices de búsqueda binaria
        ArrayList<Vehiculo> listaTemporal = new ArrayList<>();
        NodoDobleVehiculo actual = inventarioDoble.getCabeza();
        
        while (actual != null) {
            listaTemporal.add(actual.getDato());
            actual = actual.getSiguiente();
        }

        int inicio = 0;
        int fin = listaTemporal.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Vehiculo vehiculoMedio = listaTemporal.get(medio);

            if (vehiculoMedio.getPrecio() == precioBuscado) {
                return vehiculoMedio; // ¡Encontrado!
            }

            if (vehiculoMedio.getPrecio() < precioBuscado) {
                inicio = medio + 1; // Descartar mitad izquierda
            } else {
                fin = medio - 1;    // Descartar mitad derecha
            }
        }

        return null; // No encontrado
    }
}