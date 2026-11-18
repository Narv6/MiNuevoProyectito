
package interfaz;

import java.util.Scanner;
import java.util.Queue;
import java.util.Stack;

import mundo.Concesionario;
import mundo.Vehiculo;
import mundo.Carro;
import mundo.Moto;
import mundo.Camion;
import mundo.Bicicleta;
import mundo.NodoDobleVehiculo;
import mundo.DatosIniciales;
import mundo.PersistenciaConcesionario;

/**
 * Menu de consola del concesionario.
 * Las modificaciones se guardan en un archivo de texto.
 */
public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Concesionario concesionario;

        // CARGAR DATOS ANTERIORES O CREAR LOS INICIALES

        try
        {
            if (PersistenciaConcesionario.existeArchivo())
            {
                concesionario = PersistenciaConcesionario.cargar();

                System.out.println("Datos cargados desde concesionario.txt");
            }
            else
            {
                concesionario = DatosIniciales.crearConcesionario();

                PersistenciaConcesionario.guardar(concesionario);

                System.out.println("Se crearon los vehiculos iniciales.");
            }
        }
        catch (Exception e)
        {
            System.out.println("No fue posible cargar los datos: " + e.getMessage());

            scanner.close();
            return;
        }

        int opcion = -1;

        do
        {
            mostrarMenu();

            try
            {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion)
                {
                    case 1:
                        mostrarInventario(concesionario);
                        break;

                    case 2:
                        Vehiculo nuevo = crearVehiculo(scanner);

                        concesionario.agregarVehiculoInventario(nuevo);

                        PersistenciaConcesionario.guardar(concesionario);

                        System.out.println("Vehiculo agregado correctamente.");
                        break;

                    case 3:
                        concesionario.ordenarInventarioPorPrecio();

                        PersistenciaConcesionario.guardar(concesionario);

                        System.out.println("Inventario ordenado por precio.");

                        mostrarInventario(concesionario);
                        break;

                    case 4:
                        System.out.print("Precio que desea buscar: ");

                        double precio = Double.parseDouble(scanner.nextLine());

                        Vehiculo encontrado = concesionario.buscarVehiculoPorPrecio(precio);

                        PersistenciaConcesionario.guardar(concesionario);

                        if (encontrado == null)
                        {
                            System.out.println("No se encontro un vehiculo con ese precio.");
                        }
                        else
                        {
                            System.out.println("Encontrado: " + encontrado);
                        }

                        break;

                    case 5:
                        mostrarVehiculosPorTipo(concesionario, scanner);
                        break;

                    case 6:
                        mostrarInventario(concesionario);

                        System.out.print("Posicion del vehiculo para el taller: ");

                        int posicionTaller = Integer.parseInt(scanner.nextLine());

                        Vehiculo vehiculoTaller = concesionario.obtenerVehiculoPorPosicion(posicionTaller);

                        concesionario.registrarVehiculoEnTaller(vehiculoTaller);

                        PersistenciaConcesionario.guardar(concesionario);

                        System.out.println("Vehiculo agregado a la cola del taller.");
                        break;

                    case 7:
                        Vehiculo atendido = concesionario.atenderSiguienteVehiculoTaller();

                        if (atendido == null)
                        {
                            System.out.println("La cola del taller esta vacia.");
                        }
                        else
                        {
                            PersistenciaConcesionario.guardar(concesionario);

                            System.out.println("Vehiculo atendido: " + atendido);
                        }

                        break;

                    case 8:
                        mostrarInventario(concesionario);

                        System.out.print("Posicion del vehiculo vendido: ");

                        int posicionVenta = Integer.parseInt(scanner.nextLine());

                        Vehiculo vehiculoVenta = concesionario.obtenerVehiculoPorPosicion(posicionVenta);

                        concesionario.registrarVenta(vehiculoVenta);

                        PersistenciaConcesionario.guardar(concesionario);

                        System.out.println("Venta registrada: " + vehiculoVenta);
                        break;

                    case 9:
                        Vehiculo cancelado = concesionario.cancelarUltimaVenta();

                        if (cancelado == null)
                        {
                            System.out.println("No hay ventas para cancelar.");
                        }
                        else
                        {
                            PersistenciaConcesionario.guardar(concesionario);

                            System.out.println("Venta cancelada. Regreso al inventario: " + cancelado);
                        }

                        break;

                    case 10:
                        mostrarTaller(concesionario);
                        break;

                    case 11:
                        mostrarVentas(concesionario);
                        break;

                    case 0:
                        PersistenciaConcesionario.guardar(concesionario);

                        System.out.println("Datos guardados. Hasta luego.");
                        break;

                    default:
                        System.out.println("Opcion no valida.");
                        break;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Debe ingresar un numero valido.");
            }
            catch (Exception e)
            {
                System.out.println("Error: " + e.getMessage());
            }
        }
        while (opcion != 0);

        scanner.close();
    }

    // MOSTRAR OPCIONES DEL MENU

    public static void mostrarMenu()
    {
        System.out.println("\n===== CONCESIONARIO =====");
        System.out.println("1. Ver inventario");
        System.out.println("2. Agregar vehiculo");
        System.out.println("3. Ordenar por precio");
        System.out.println("4. Buscar por precio");
        System.out.println("5. Filtrar por tipo");
        System.out.println("6. Enviar vehiculo al taller");
        System.out.println("7. Atender siguiente vehiculo del taller");
        System.out.println("8. Registrar venta");
        System.out.println("9. Cancelar ultima venta");
        System.out.println("10. Ver cola del taller");
        System.out.println("11. Ver ventas recientes");
        System.out.println("0. Guardar y salir");
        System.out.print("Seleccione una opcion: ");
    }

    // MOSTRAR INVENTARIO

    public static void mostrarInventario(Concesionario concesionario)
    {
        NodoDobleVehiculo actual = concesionario.getInventarioDoble().getCabeza();

        int posicion = 1;

        if (actual == null)
        {
            System.out.println("El inventario esta vacio.");
        }

        while (actual != null)
        {
            System.out.println(posicion + ". " + actual.getDato());

            actual = actual.getSiguiente();
            posicion++;
        }
    }

    // CREAR UN VEHICULO

    public static Vehiculo crearVehiculo(Scanner scanner) throws Exception
    {
        System.out.println("1. Carro");
        System.out.println("2. Moto");
        System.out.println("3. Camion");
        System.out.println("4. Bicicleta");
        System.out.print("Tipo de vehiculo: ");

        int tipo = Integer.parseInt(scanner.nextLine());

        if (tipo < 1 || tipo > 4)
        {
            throw new IllegalArgumentException("Tipo de vehiculo no valido.");
        }

        System.out.print("Marca: ");
        String marca = scanner.nextLine();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());

        if (tipo == 1)
        {
            System.out.print("Numero de puertas: ");

            int puertas = Integer.parseInt(scanner.nextLine());

            return new Carro(marca, modelo, precio, puertas);
        }
        else if (tipo == 2)
        {
            System.out.print("Cilindraje: ");

            int cilindraje = Integer.parseInt(scanner.nextLine());

            System.out.print("Tipo de uso: ");

            String uso = scanner.nextLine();

            return new Moto(marca, modelo, precio, cilindraje, uso);
        }
        else if (tipo == 3)
        {
            System.out.print("Capacidad de carga: ");

            double capacidad = Double.parseDouble(scanner.nextLine());

            System.out.print("Numero de ejes: ");

            int ejes = Integer.parseInt(scanner.nextLine());

            return new Camion(marca, modelo, precio, capacidad, ejes);
        }
        else
        {
            System.out.print("Numero de cambios: ");

            int cambios = Integer.parseInt(scanner.nextLine());

            return new Bicicleta(marca, modelo, precio, cambios);
        }
    }

    // FILTRAR VEHICULOS POR TIPO

    public static void mostrarVehiculosPorTipo(Concesionario concesionario, Scanner scanner)
    {
        System.out.println("1. Carros");
        System.out.println("2. Motos");
        System.out.println("3. Camiones");
        System.out.println("4. Bicicletas");
        System.out.print("Seleccione el tipo: ");

        int tipo = Integer.parseInt(scanner.nextLine());

        if (tipo == 1)
        {
            for (Carro carro : concesionario.obtenerCarros())
            {
                System.out.println(carro);
            }
        }
        else if (tipo == 2)
        {
            for (Moto moto : concesionario.obtenerMotos())
            {
                System.out.println(moto);
            }
        }
        else if (tipo == 3)
        {
            for (Camion camion : concesionario.obtenerCamiones())
            {
                System.out.println(camion);
            }
        }
        else if (tipo == 4)
        {
            for (Bicicleta bicicleta : concesionario.obtenerBicicletas())
            {
                System.out.println(bicicleta);
            }
        }
        else
        {
            System.out.println("Tipo no valido.");
        }
    }

    // MOSTRAR COLA DE TALLER

    public static void mostrarTaller(Concesionario concesionario)
    {
        Queue<Vehiculo> taller = concesionario.getColaTaller();

        if (taller.isEmpty())
        {
            System.out.println("No hay vehiculos esperando en el taller.");
        }

        for (Vehiculo vehiculo : taller)
        {
            System.out.println(vehiculo);
        }
    }

    // MOSTRAR PILA DE VENTAS

    public static void mostrarVentas(Concesionario concesionario)
    {
        Stack<Vehiculo> ventas = concesionario.getPilaVentas();

        if (ventas.isEmpty())
        {
            System.out.println("Todavia no hay ventas registradas.");
        }

        for (Vehiculo vehiculo : ventas)
        {
            System.out.println(vehiculo);
        }
    }
}
