package mundo;

//Lista doblemente enlazada para la gestión del inventario de vehículos.

public class ListaDobleVehiculos {

    private NodoDobleVehiculo cabeza;
    private NodoDobleVehiculo cola;
    private int tamanio;

    public ListaDobleVehiculos() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

    /**
     * Verifica si la lista se encuentra vacía.
     */
    public boolean estaVacia() {
        return cabeza == null;
    }

    /**
     * Retorna la cantidad de vehículos en la lista.
     */
    public int getTamanio() {
        return tamanio;
    }

    public NodoDobleVehiculo getCabeza() {
        return cabeza;
    }

    public NodoDobleVehiculo getCola() {
        return cola;
    }

    /**
     * Agrega un nuevo vehículo al final (en la cola) de la lista.
     */
    public void agregarAlFinal(Vehiculo vehiculo) {
        NodoDobleVehiculo nuevoNodo = new NodoDobleVehiculo(vehiculo);
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(cola);
            cola = nuevoNodo;
        }
        tamanio++;
    }

    /**
     * Agrega un nuevo vehículo al inicio (en la cabeza) de la lista.
     */
    public void agregarAlInicio(Vehiculo vehiculo) {
        NodoDobleVehiculo nuevoNodo = new NodoDobleVehiculo(vehiculo);
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevoNodo);
            cabeza = nuevoNodo;
        }
        tamanio++;
    }

    /**
     * Elimina un vehículo de la lista según su coincidencia.
     * @return true si se encontró y eliminó, false de lo contrario.
     */
    public boolean eliminar(Vehiculo vehiculo) {
        if (estaVacia()) {
            return false;
        }

        NodoDobleVehiculo actual = cabeza;

        while (actual != null) {
            if (actual.getDato().equals(vehiculo)) {
                if (actual == cabeza && actual == cola) {
                    cabeza = null;
                    cola = null;
                } else if (actual == cabeza) {
                    cabeza = cabeza.getSiguiente();
                    cabeza.setAnterior(null);
                } else if (actual == cola) {
                    cola = cola.getAnterior();
                    cola.setSiguiente(null);
                } else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                }
                tamanio--;
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
    }
}