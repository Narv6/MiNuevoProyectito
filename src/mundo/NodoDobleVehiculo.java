package mundo;

//Nodo de una lista doblemente enlazada para almacenar objetos de tipo Vehiculo.

public class NodoDobleVehiculo {

    private Vehiculo dato;
    private NodoDobleVehiculo siguiente;
    private NodoDobleVehiculo anterior;

    public NodoDobleVehiculo(Vehiculo dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }

    public Vehiculo getDato() {
        return dato;
    }

    public void setDato(Vehiculo dato) {
        this.dato = dato;
    }

    public NodoDobleVehiculo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDobleVehiculo siguiente) {
        this.siguiente = siguiente;
    }

    public NodoDobleVehiculo getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDobleVehiculo anterior) {
        this.anterior = anterior;
    }
}