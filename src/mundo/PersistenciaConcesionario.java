
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
 * Guarda y recupera los datos del concesionario en un archivo de texto.
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

    // GUARDAR LOS DATOS

    public static void guardar(Concesionario concesionario) throws IOException
    {
        BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO));

        try
        {
            // INVENTARIO

            NodoDobleVehiculo actual = concesionario.getInventarioDoble().getCabeza();

            while (actual != null)
            {
                escritor.write("I;" + convertirVehiculoATexto(actual.getDato()));
                escritor.newLine();
                actual = actual.getSiguiente();
            }

            // TALLER

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

            // VENTAS

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

    // CARGAR LOS DATOS

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
                    concesionario.agregarVehiculoInventario(vehiculo);
                    concesionario.registrarVenta(vehiculo);
                }
                else
                {
                    throw new IOException("Tipo de registro desconocido.");
                }

                linea = lector.readLine();
            }
        }
        catch (NumberFormatException e)
        {
            throw new IOException("Hay un numero incorrecto en el archivo.", e);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new IOException("Hay una linea incompleta en el archivo.", e);
        }
        finally
        {
            lector.close();
        }

        return concesionario;
    }

    // POSICION DEL VEHICULO EN EL INVENTARIO

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

    // CONVERTIR OBJETO A TEXTO

    private static String convertirVehiculoATexto(Vehiculo vehiculo)
    {
        String texto = vehiculo.obtenerTipoDeVehiculo() + ";"
                + vehiculo.getMarca() + ";"
                + vehiculo.getModelo() + ";"
                + vehiculo.getPrecio();

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

        // DATOS DEL MOTOR

        String tipoMotor = "Ninguno";
        int potencia = 0;
        double capacidad = 0;
        double consumo = 0;

        if (vehiculo.getMotor() instanceof MotorElectrico)
        {
            MotorElectrico motor = (MotorElectrico) vehiculo.getMotor();
            tipoMotor = "Electrico";
            potencia = motor.getPotencia();
            capacidad = motor.getCapacidadBateria();
            consumo = motor.getConsumoCada100Km();
        }
        else if (vehiculo.getMotor() instanceof MotorGasolina)
        {
            MotorGasolina motor = (MotorGasolina) vehiculo.getMotor();
            tipoMotor = "Gasolina";
            potencia = motor.getPotencia();
            capacidad = motor.getCapacidadTanque();
        }

        // IMAGEN, MOTOR Y MANTENIMIENTO

        texto = texto + ";" + vehiculo.getRutaImagen()
                + ";" + tipoMotor
                + ";" + potencia
                + ";" + capacidad
                + ";" + consumo
                + ";" + vehiculo.isMantenimientoRealizado()
                + ";" + vehiculo.getCostoManoObra()
                + ";" + vehiculo.getCostoRepuestos();

        return texto;
    }

    // CONVERTIR TEXTO A OBJETO

    private static Vehiculo convertirTextoAVehiculo(String[] partes)
            throws DatosVehiculoInvalidosException, PrecioInvalidoException,
                   CapacidadCargaInvalidaException, IOException
    {
        String tipo = partes[1];
        String marca = partes[2];
        String modelo = partes[3];
        double precio = Double.parseDouble(partes[4]);

        Vehiculo vehiculo;
        int posicionImagen;

        if (tipo.equals("Carro"))
        {
            int puertas = Integer.parseInt(partes[5]);
            vehiculo = new Carro(marca, modelo, precio, puertas);
            posicionImagen = 6;
        }
        else if (tipo.equals("Motocicleta"))
        {
            int cilindraje = Integer.parseInt(partes[5]);
            String uso = partes[6];
            vehiculo = new Moto(marca, modelo, precio, cilindraje, uso);
            posicionImagen = 7;
        }
        else if (tipo.equals("Camion"))
        {
            double carga = Double.parseDouble(partes[5]);
            int ejes = Integer.parseInt(partes[6]);
            vehiculo = new Camion(marca, modelo, precio, carga, ejes);
            posicionImagen = 7;
        }
        else if (tipo.equals("Bicicleta"))
        {
            int cambios = Integer.parseInt(partes[5]);
            vehiculo = new Bicicleta(marca, modelo, precio, cambios);
            posicionImagen = 6;
        }
        else
        {
            throw new IOException("Tipo de vehiculo desconocido: " + tipo);
        }

        // LEER LA IMAGEN, TAMBIEN EN ARCHIVOS ANTIGUOS

        if (partes.length > posicionImagen)
        {
            vehiculo.setRutaImagen(partes[posicionImagen]);
        }

        // LEER LOS DATOS DEL MOTOR, SI EXISTEN

        int posicionMotor = posicionImagen + 1;

        if (partes.length > posicionMotor)
        {
            String tipoMotor = partes[posicionMotor];

            if (tipoMotor.equals("Electrico"))
            {
                int potencia = Integer.parseInt(partes[posicionMotor + 1]);
                double bateria = Double.parseDouble(partes[posicionMotor + 2]);
                double consumo = Double.parseDouble(partes[posicionMotor + 3]);

                MotorElectrico motor = new MotorElectrico(potencia, bateria);

                if (consumo > 0)
                {
                    motor.setConsumoCada100Km(consumo);
                }

                vehiculo.setMotor(motor);
            }
            else if (tipoMotor.equals("Gasolina"))
            {
                int potencia = Integer.parseInt(partes[posicionMotor + 1]);
                double tanque = Double.parseDouble(partes[posicionMotor + 2]);

                MotorGasolina motor = new MotorGasolina(potencia, tanque);
                vehiculo.setMotor(motor);
            }
            else if (!tipoMotor.equals("Ninguno") && !tipoMotor.isEmpty())
            {
                throw new IOException("Tipo de motor desconocido: " + tipoMotor);
            }

            // LEER MANTENIMIENTO

            if (partes.length > posicionMotor + 4)
            {
                vehiculo.setMantenimientoRealizado(
                        Boolean.parseBoolean(partes[posicionMotor + 4]));
            }

            if (partes.length > posicionMotor + 5)
            {
                vehiculo.setCostoManoObra(
                        Double.parseDouble(partes[posicionMotor + 5]));
            }

            if (partes.length > posicionMotor + 6)
            {
                vehiculo.setCostoRepuestos(
                        Double.parseDouble(partes[posicionMotor + 6]));
            }
        }

        return vehiculo;
    }
}
