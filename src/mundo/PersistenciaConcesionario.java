
package mundo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.Stack;

import excepciones.CapacidadCargaInvalidaException;
import excepciones.DatosVehiculoInvalidosException;
import excepciones.PrecioInvalidoException;
import excepciones.VehiculoNoEncontradoException;

/**
 * Guarda y lee el inventario, la cola del taller y la pila de ventas.
 * El archivo se crea en la carpeta desde donde se ejecuta el programa.
 */
public class PersistenciaConcesionario
{
    private static final String ARCHIVO = "concesionario.txt";

    // COMPROBAR SI EXISTE EL ARCHIVO

    public static boolean existeArchivo()
    {
        File archivo = new File(ARCHIVO);

        return archivo.exists();
    }

    // GUARDAR LOS DATOS CON BUFFEREDWRITER

    public static void guardar(Concesionario concesionario) throws IOException
    {
        BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO));

        try
        {
            // GUARDAR INVENTARIO

            NodoDobleVehiculo actual = concesionario.getInventarioDoble().getCabeza();

            while (actual != null)
            {
                escritor.write("I;" + convertirVehiculoATexto(actual.getDato()));
                escritor.newLine();

                actual = actual.getSiguiente();
            }

            // GUARDAR COLA DE TALLER

            Queue<Vehiculo> taller = concesionario.getColaTaller();

            for (Vehiculo vehiculo : taller)
            {
                int posicion = buscarPosicion(concesionario, vehiculo);

                if (posicion > 0)
                {
                    escritor.write("T;" + posicion);
                    escritor.newLine();
                }
            }

            // GUARDAR PILA DE VENTAS

            Stack<Vehiculo> ventas = concesionario.getPilaVentas();

            for (Vehiculo vehiculo : ventas)
            {
                escritor.write("V;" + convertirVehiculoATexto(vehiculo));
                escritor.newLine();
            }
        }
        finally
        {
            escritor.close();
        }
    }

    // LEER LOS DATOS CON BUFFEREDREADER

    public static Concesionario cargar()
            throws IOException, DatosVehiculoInvalidosException, PrecioInvalidoException,
                   CapacidadCargaInvalidaException, VehiculoNoEncontradoException
    {
        Concesionario concesionario = new Concesionario();

        BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO));

        try
        {
            String linea = lector.readLine();

            while (linea != null)
            {
                String[] partes = linea.split(";", -1);

                if (partes[0].equals("I"))
                {
                    Vehiculo vehiculo = convertirTextoAVehiculo(partes);

                    concesionario.agregarVehiculoInventario(vehiculo);
                }
                else if (partes[0].equals("T"))
                {
                    int posicion = Integer.parseInt(partes[1]);

                    Vehiculo vehiculo = concesionario.obtenerVehiculoPorPosicion(posicion);

                    concesionario.registrarVehiculoEnTaller(vehiculo);
                }
                else if (partes[0].equals("V"))
                {
                    Vehiculo vehiculo = convertirTextoAVehiculo(partes);

                    // Agregar temporalmente para reconstruir la venta.

                    concesionario.agregarVehiculoInventario(vehiculo);
                    concesionario.registrarVenta(vehiculo);
                }
                else
                {
                    throw new IOException("Tipo de registro desconocido en el archivo.");
                }

                linea = lector.readLine();
            }
        }
        catch (NumberFormatException e)
        {
            throw new IOException("Hay un numero incorrecto en el archivo de datos.", e);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new IOException("Hay una linea incompleta en el archivo de datos.", e);
        }
        finally
        {
            lector.close();
        }

        return concesionario;
    }

    // BUSCAR POSICION DE UN VEHICULO EN EL INVENTARIO

    private static int buscarPosicion(Concesionario concesionario, Vehiculo vehiculo)
    {
        NodoDobleVehiculo actual = concesionario.getInventarioDoble().getCabeza();

        int posicion = 1;

        while (actual != null)
        {
            if (actual.getDato() == vehiculo)
            {
                return posicion;
            }

            actual = actual.getSiguiente();
            posicion++;
        }

        return -1;
    }

    // CONVERTIR UN VEHICULO A TEXTO

    private static String convertirVehiculoATexto(Vehiculo vehiculo)
    {
        String texto = vehiculo.obtenerTipoDeVehiculo() + ";" + vehiculo.getMarca() + ";"
                + vehiculo.getModelo() + ";" + vehiculo.getPrecio();

        if (vehiculo instanceof Carro)
        {
            Carro carro = (Carro) vehiculo;

            texto = texto + ";" + carro.getNumeroPuertas();
        }
        else if (vehiculo instanceof Moto)
        {
            Moto moto = (Moto) vehiculo;

            texto = texto + ";" + moto.getCilindraje() + ";" + moto.getTipoDeUso();
        }
        else if (vehiculo instanceof Camion)
        {
            Camion camion = (Camion) vehiculo;

            texto = texto + ";" + camion.getCapacidadCarga() + ";" + camion.getNumeroEjes();
        }
        else if (vehiculo instanceof Bicicleta)
        {
            Bicicleta bicicleta = (Bicicleta) vehiculo;

            texto = texto + ";" + bicicleta.getNumeroCambios();
        }

        return texto;
    }

    // CONVERTIR EL TEXTO A UN VEHICULO

    private static Vehiculo convertirTextoAVehiculo(String[] partes)
            throws DatosVehiculoInvalidosException, PrecioInvalidoException,
                   CapacidadCargaInvalidaException, IOException
    {
        String tipo = partes[1];
        String marca = partes[2];
        String modelo = partes[3];
        double precio = Double.parseDouble(partes[4]);

        if (tipo.equals("Carro"))
        {
            int puertas = Integer.parseInt(partes[5]);

            return new Carro(marca, modelo, precio, puertas);
        }
        else if (tipo.equals("Motocicleta"))
        {
            int cilindraje = Integer.parseInt(partes[5]);
            String uso = partes[6];

            return new Moto(marca, modelo, precio, cilindraje, uso);
        }
        else if (tipo.equals("Camion"))
        {
            double carga = Double.parseDouble(partes[5]);
            int ejes = Integer.parseInt(partes[6]);

            return new Camion(marca, modelo, precio, carga, ejes);
        }
        else if (tipo.equals("Bicicleta"))
        {
            int cambios = Integer.parseInt(partes[5]);

            return new Bicicleta(marca, modelo, precio, cambios);
        }

        throw new IOException("Tipo de vehiculo desconocido: " + tipo);
    }
}
