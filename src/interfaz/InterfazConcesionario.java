
package interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import mundo.Bicicleta;
import mundo.Camion;
import mundo.Carro;
import mundo.Concesionario;
import mundo.DatosIniciales;
import mundo.Moto;
import mundo.MotorElectrico;
import mundo.MotorGasolina;
import mundo.NodoDobleVehiculo;
import mundo.PersistenciaConcesionario;
import mundo.Vehiculo;

/**
 * Ventana principal del concesionario.
 */
public class InterfazConcesionario extends JFrame implements ActionListener
{
    // ATRIBUTOS

    private Concesionario concesionario;
    private PanelTitulo panelTitulo;
    private PanelBotones panelBotones;
    private PanelResultados panelResultados;
    private PanelEstado panelEstado;

    // CONSTRUCTOR

    public InterfazConcesionario(Concesionario concesionario)
    {
        this.concesionario = concesionario;

        setTitle("MiProyectito - Concesionario");
        setSize(1150, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        panelTitulo = new PanelTitulo();
        panelBotones = new PanelBotones(this);
        panelResultados = new PanelResultados();
        panelEstado = new PanelEstado();

        add(panelTitulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.WEST);
        add(panelResultados, BorderLayout.CENTER);
        add(panelEstado, BorderLayout.SOUTH);

        mostrarInventario();
        setVisible(true);
    }

    // EVENTOS DE LOS BOTONES

    @Override
    public void actionPerformed(ActionEvent evento)
    {
        try
        {
            if (evento.getSource() == panelBotones.getBotonInventario())
            {
                mostrarInventario();
            }
            else if (evento.getSource() == panelBotones.getBotonAgregar())
            {
                agregarVehiculo();
            }
            else if (evento.getSource() == panelBotones.getBotonElegirFoto())
            {
                asignarImagen();
            }
            else if (evento.getSource() == panelBotones.getBotonQuitarFoto())
            {
                quitarImagen();
            }
            else if (evento.getSource() == panelBotones.getBotonMotor())
            {
                asignarMotor();
            }
            else if (evento.getSource() == panelBotones.getBotonAutonomia())
            {
                consultarAutonomia();
            }
            else if (evento.getSource() == panelBotones.getBotonOrdenar())
            {
                concesionario.ordenarInventarioPorPrecio();
                guardarCambios();
                mostrarInventario();
                panelEstado.mostrarMensaje("Inventario ordenado por precio.");
            }
            else if (evento.getSource() == panelBotones.getBotonBuscar())
            {
                buscarPorPrecio();
            }
            else if (evento.getSource() == panelBotones.getBotonFiltrar())
            {
                filtrarPorTipo();
            }
            else if (evento.getSource() == panelBotones.getBotonVender())
            {
                registrarVenta();
            }
            else if (evento.getSource() == panelBotones.getBotonCancelarVenta())
            {
                cancelarUltimaVenta();
            }
            else if (evento.getSource() == panelBotones.getBotonEnviarTaller())
            {
                enviarAlTaller();
            }
            else if (evento.getSource() == panelBotones.getBotonAtenderTaller())
            {
                atenderTaller();
            }
            else if (evento.getSource() == panelBotones.getBotonVerTaller())
            {
                mostrarTaller();
            }
            else if (evento.getSource() == panelBotones.getBotonVerVentas())
            {
                mostrarVentas();
            }
            else if (evento.getSource() == panelBotones.getBotonGuardar())
            {
                guardarCambios();
                JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
            }
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Debes ingresar un numero valido.");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // GUARDAR LOS CAMBIOS

    private void guardarCambios() throws Exception
    {
        PersistenciaConcesionario.guardar(concesionario);
        panelEstado.mostrarMensaje("Ultimos cambios guardados en concesionario.txt");
    }

    // MOSTRAR EL INVENTARIO

    private void mostrarInventario()
    {
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();
        NodoDobleVehiculo actual = concesionario.getInventarioDoble().getCabeza();

        while (actual != null)
        {
            vehiculos.add(actual.getDato());
            actual = actual.getSiguiente();
        }

        panelResultados.mostrarVehiculos(vehiculos, "INVENTARIO DE VEHICULOS");
    }

    // AGREGAR VEHICULO

    private void agregarVehiculo() throws Exception
    {
        String tipoTexto = JOptionPane.showInputDialog(this,
                "Tipo de vehiculo:\n1. Carro\n2. Moto\n3. Camion\n4. Bicicleta");

        if (tipoTexto == null)
        {
            return;
        }

        int tipo = Integer.parseInt(tipoTexto);

        if (tipo < 1 || tipo > 4)
        {
            JOptionPane.showMessageDialog(this, "Tipo de vehiculo no valido.");
            return;
        }

        String marca = JOptionPane.showInputDialog(this, "Marca:");
        if (marca == null)
        {
            return;
        }

        String modelo = JOptionPane.showInputDialog(this, "Modelo:");
        if (modelo == null)
        {
            return;
        }

        String precioTexto = JOptionPane.showInputDialog(this, "Precio:");
        if (precioTexto == null)
        {
            return;
        }

        double precio = Double.parseDouble(precioTexto);
        Vehiculo nuevo;

        if (tipo == 1)
        {
            String dato = JOptionPane.showInputDialog(this, "Numero de puertas:");
            if (dato == null)
            {
                return;
            }

            nuevo = new Carro(marca, modelo, precio, Integer.parseInt(dato));
        }
        else if (tipo == 2)
        {
            String dato = JOptionPane.showInputDialog(this, "Cilindraje:");
            if (dato == null)
            {
                return;
            }

            String uso = JOptionPane.showInputDialog(this, "Tipo de uso:");
            if (uso == null)
            {
                return;
            }

            nuevo = new Moto(marca, modelo, precio, Integer.parseInt(dato), uso);
        }
        else if (tipo == 3)
        {
            String dato = JOptionPane.showInputDialog(this, "Capacidad de carga:");
            if (dato == null)
            {
                return;
            }

            String ejes = JOptionPane.showInputDialog(this, "Numero de ejes:");
            if (ejes == null)
            {
                return;
            }

            nuevo = new Camion(marca, modelo, precio,
                    Double.parseDouble(dato), Integer.parseInt(ejes));
        }
        else
        {
            String dato = JOptionPane.showInputDialog(this, "Numero de cambios:");
            if (dato == null)
            {
                return;
            }

            nuevo = new Bicicleta(marca, modelo, precio, Integer.parseInt(dato));
        }

        concesionario.agregarVehiculoInventario(nuevo);
        guardarCambios();
        mostrarInventario();
        panelEstado.mostrarMensaje("Vehiculo agregado correctamente.");
    }

    // BUSCAR TODAS LAS COINCIDENCIAS POR PRECIO

    private void buscarPorPrecio() throws Exception
    {
        String dato = JOptionPane.showInputDialog(this, "Precio que deseas buscar:");

        if (dato == null)
        {
            return;
        }

        double precio = Double.parseDouble(dato);
        ArrayList<Vehiculo> encontrados = concesionario.buscarVehiculoPorPrecio(precio);

        guardarCambios();
        panelResultados.mostrarVehiculos(encontrados, "RESULTADOS DE LA BUSQUEDA");
        panelEstado.mostrarMensaje("Se encontraron " + encontrados.size() + " vehiculos.");
    }

    // FILTRAR VEHICULOS

    private void filtrarPorTipo()
    {
        String dato = JOptionPane.showInputDialog(this,
                "Selecciona un tipo:\n1. Carros\n2. Motos\n3. Camiones\n4. Bicicletas");

        if (dato == null)
        {
            return;
        }

        int tipo = Integer.parseInt(dato);
        ArrayList<Vehiculo> filtrados = new ArrayList<>();

        if (tipo == 1)
        {
            for (Carro carro : concesionario.obtenerCarros())
            {
                filtrados.add(carro);
            }
        }
        else if (tipo == 2)
        {
            for (Moto moto : concesionario.obtenerMotos())
            {
                filtrados.add(moto);
            }
        }
        else if (tipo == 3)
        {
            for (Camion camion : concesionario.obtenerCamiones())
            {
                filtrados.add(camion);
            }
        }
        else if (tipo == 4)
        {
            for (Bicicleta bicicleta : concesionario.obtenerBicicletas())
            {
                filtrados.add(bicicleta);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Tipo no valido.");
            return;
        }

        panelResultados.mostrarVehiculos(filtrados, "VEHICULOS FILTRADOS");
    }

    // REGISTRAR VENTA

    private void registrarVenta() throws Exception
    {
        Vehiculo vehiculo = panelResultados.getVehiculoSeleccionado();

        if (vehiculo == null)
        {
            JOptionPane.showMessageDialog(this, "Selecciona primero un vehiculo del inventario.");
            return;
        }

        concesionario.registrarVenta(vehiculo);
        guardarCambios();
        mostrarInventario();
        panelEstado.mostrarMensaje("Venta registrada: " + vehiculo.getModelo());
    }

    // CANCELAR VENTA

    private void cancelarUltimaVenta() throws Exception
    {
        Vehiculo vehiculo = concesionario.cancelarUltimaVenta();

        if (vehiculo == null)
        {
            JOptionPane.showMessageDialog(this, "No hay ventas para cancelar.");
            return;
        }

        guardarCambios();
        mostrarInventario();
        panelEstado.mostrarMensaje("Venta cancelada: " + vehiculo.getModelo());
    }

    // ENVIAR AL TALLER

    private void enviarAlTaller() throws Exception
    {
        Vehiculo vehiculo = panelResultados.getVehiculoSeleccionado();

        if (vehiculo == null)
        {
            JOptionPane.showMessageDialog(this, "Selecciona primero un vehiculo del inventario.");
            return;
        }

        concesionario.registrarVehiculoEnTaller(vehiculo);
        guardarCambios();
        mostrarTaller();
        panelEstado.mostrarMensaje("Enviado al taller: " + vehiculo.getModelo());
    }

    // ATENDER VEHICULO Y CALCULAR MANTENIMIENTO

    private void atenderTaller() throws Exception
    {
        Queue<Vehiculo> taller = concesionario.getColaTaller();

        if (taller.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "La cola del taller esta vacia.");
            return;
        }

        Vehiculo siguiente = taller.peek();

        String manoObraTexto = JOptionPane.showInputDialog(this,
                "Costo de mano de obra para " + siguiente.getModelo() + ":");

        if (manoObraTexto == null)
        {
            return;
        }

        String repuestosTexto = JOptionPane.showInputDialog(this, "Costo de los repuestos:");

        if (repuestosTexto == null)
        {
            return;
        }

        double manoObra = Double.parseDouble(manoObraTexto);
        double repuestos = Double.parseDouble(repuestosTexto);

        if (manoObra < 0 || repuestos < 0)
        {
            JOptionPane.showMessageDialog(this, "Los costos no pueden ser negativos.");
            return;
        }

        siguiente.setCostoManoObra(manoObra);
        siguiente.setCostoRepuestos(repuestos);

        Vehiculo vehiculo = concesionario.atenderSiguienteVehiculoTaller();
        double costoTotal = vehiculo.calcularCostoMantenimiento();

        guardarCambios();
        mostrarTaller();

        JOptionPane.showMessageDialog(this,
                "Mantenimiento realizado a " + vehiculo.getModelo()
                + "\nCosto total: " + costoTotal);

        panelEstado.mostrarMensaje("Mantenimiento completado: " + vehiculo.getModelo());
    }

    // MOSTRAR TALLER

    private void mostrarTaller()
    {
        Queue<Vehiculo> taller = concesionario.getColaTaller();
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();

        for (Vehiculo vehiculo : taller)
        {
            vehiculos.add(vehiculo);
        }

        panelResultados.mostrarVehiculos(vehiculos, "COLA DEL TALLER - FIFO");
    }

    // MOSTRAR VENTAS

    private void mostrarVentas()
    {
        Stack<Vehiculo> ventas = concesionario.getPilaVentas();
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();

        for (int i = ventas.size() - 1; i >= 0; i--)
        {
            vehiculos.add(ventas.get(i));
        }

        panelResultados.mostrarVehiculos(vehiculos, "VENTAS RECIENTES - LIFO");
    }

    // ASIGNAR IMAGEN

    private void asignarImagen() throws Exception
    {
        Vehiculo vehiculo = panelResultados.getVehiculoSeleccionado();

        if (vehiculo == null)
        {
            JOptionPane.showMessageDialog(this, "Selecciona un vehiculo primero.");
            return;
        }

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar foto de " + vehiculo.getModelo());
        selector.setFileFilter(new FileNameExtensionFilter(
                "Imagenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif"));

        int resultado = selector.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION)
        {
            File archivo = selector.getSelectedFile();
            vehiculo.setRutaImagen(archivo.getAbsolutePath());
            guardarCambios();
            panelResultados.actualizarSeleccionado();
            panelEstado.mostrarMensaje("Imagen asignada a " + vehiculo.getModelo());
        }
    }

    // QUITAR IMAGEN

    private void quitarImagen() throws Exception
    {
        Vehiculo vehiculo = panelResultados.getVehiculoSeleccionado();

        if (vehiculo == null)
        {
            JOptionPane.showMessageDialog(this, "Selecciona un vehiculo primero.");
            return;
        }

        vehiculo.setRutaImagen("");
        guardarCambios();
        panelResultados.actualizarSeleccionado();
        panelEstado.mostrarMensaje("Imagen quitada de " + vehiculo.getModelo());
    }

    // ASIGNAR MOTOR

    private void asignarMotor() throws Exception
    {
        Vehiculo vehiculo = panelResultados.getVehiculoSeleccionado();

        if (vehiculo == null)
        {
            JOptionPane.showMessageDialog(this, "Selecciona primero un vehiculo.");
            return;
        }

        String tipoTexto = JOptionPane.showInputDialog(this,
                "Selecciona el tipo de motor:\n1. Gasolina\n2. Electrico");

        if (tipoTexto == null)
        {
            return;
        }

        int tipo = Integer.parseInt(tipoTexto);

        if (tipo != 1 && tipo != 2)
        {
            JOptionPane.showMessageDialog(this, "Tipo de motor no valido.");
            return;
        }

        String potenciaTexto = JOptionPane.showInputDialog(this, "Potencia del motor en HP:");

        if (potenciaTexto == null)
        {
            return;
        }

        int potencia = Integer.parseInt(potenciaTexto);

        if (tipo == 1)
        {
            String tanqueTexto = JOptionPane.showInputDialog(this,
                    "Capacidad del tanque en litros:");

            if (tanqueTexto == null)
            {
                return;
            }

            double tanque = Double.parseDouble(tanqueTexto);
            MotorGasolina motor = new MotorGasolina(potencia, tanque);
            vehiculo.setMotor(motor);
        }
        else
        {
            String bateriaTexto = JOptionPane.showInputDialog(this,
                    "Capacidad de la bateria en kWh:");

            if (bateriaTexto == null)
            {
                return;
            }

            String consumoTexto = JOptionPane.showInputDialog(this,
                    "Consumo en kWh cada 100 km:");

            if (consumoTexto == null)
            {
                return;
            }

            double bateria = Double.parseDouble(bateriaTexto);
            double consumo = Double.parseDouble(consumoTexto);

            MotorElectrico motor = new MotorElectrico(potencia, bateria);
            motor.setConsumoCada100Km(consumo);
            vehiculo.setMotor(motor);
        }

        guardarCambios();
        panelResultados.actualizarSeleccionado();
        panelEstado.mostrarMensaje("Motor asignado a " + vehiculo.getModelo());
    }

    // CONSULTAR AUTONOMIA

    private void consultarAutonomia()
    {
        Vehiculo vehiculo = panelResultados.getVehiculoSeleccionado();

        if (vehiculo == null)
        {
            JOptionPane.showMessageDialog(this, "Selecciona primero un vehiculo.");
            return;
        }

        if (vehiculo.getMotor() instanceof MotorElectrico)
        {
            MotorElectrico motor = (MotorElectrico) vehiculo.getMotor();

            if (motor.getConsumoCada100Km() <= 0)
            {
                JOptionPane.showMessageDialog(this, "El motor no tiene consumo configurado.");
                return;
            }

            double autonomia = motor.calcularAutonomia();
            JOptionPane.showMessageDialog(this,
                    "Autonomia estimada de " + vehiculo.getModelo() + ": " + autonomia + " km");
        }
        else
        {
            JOptionPane.showMessageDialog(this,
                    "El vehiculo seleccionado no tiene motor electrico.");
        }
    }

    // INICIAR VENTANA

    public static void main(String[] args)
    {
        try
        {
            Concesionario concesionario;

            if (PersistenciaConcesionario.existeArchivo())
            {
                concesionario = PersistenciaConcesionario.cargar();
            }
            else
            {
                concesionario = DatosIniciales.crearConcesionario();
                PersistenciaConcesionario.guardar(concesionario);
            }

            new InterfazConcesionario(concesionario);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,
                    "No se pudo abrir el concesionario: " + e.getMessage());
        }
    }
}
