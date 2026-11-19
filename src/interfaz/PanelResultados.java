
package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mundo.Bicicleta;
import mundo.Camion;
import mundo.Carro;
import mundo.Moto;
import mundo.MotorElectrico;
import mundo.MotorGasolina;
import mundo.Vehiculo;

/**
 * Muestra los vehiculos, sus datos y su fotografia.
 */
public class PanelResultados extends JPanel implements ListSelectionListener
{
    // ATRIBUTOS

    private JLabel etiquetaSeccion;
    private DefaultListModel<String> modeloLista;
    private JList<String> lista;
    private JTextArea areaDetalles;
    private PanelFoto panelFoto;
    private ArrayList<Vehiculo> vehiculosMostrados;

    // CONSTRUCTOR

    public PanelResultados()
    {
        setLayout(new BorderLayout(12, 12));
        setBackground(new Color(245, 248, 252));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        vehiculosMostrados = new ArrayList<>();

        etiquetaSeccion = new JLabel("INVENTARIO");
        etiquetaSeccion.setFont(new Font("Arial", Font.BOLD, 18));
        etiquetaSeccion.setForeground(new Color(36, 58, 88));

        add(etiquetaSeccion, BorderLayout.NORTH);

        // LISTA DE VEHICULOS

        modeloLista = new DefaultListModel<>();
        lista = new JList<>(modeloLista);
        lista.setFont(new Font("Arial", Font.PLAIN, 14));
        lista.setFixedCellHeight(35);
        lista.addListSelectionListener(this);

        JScrollPane scrollLista = new JScrollPane(lista);
        scrollLista.setPreferredSize(new Dimension(290, 400));
        scrollLista.setBorder(BorderFactory.createTitledBorder("Selecciona un vehiculo"));

        add(scrollLista, BorderLayout.WEST);

        // PANEL DERECHO

        JPanel panelDerecho = new JPanel(new GridLayout(2, 1, 0, 10));
        panelDerecho.setOpaque(false);

        panelFoto = new PanelFoto();
        panelDerecho.add(panelFoto);

        areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);
        areaDetalles.setFont(new Font("Arial", Font.PLAIN, 15));
        areaDetalles.setBackground(Color.WHITE);
        areaDetalles.setLineWrap(true);
        areaDetalles.setWrapStyleWord(true);

        JScrollPane scrollDetalle = new JScrollPane(areaDetalles);
        scrollDetalle.setBorder(BorderFactory.createTitledBorder("Informacion del vehiculo"));
        panelDerecho.add(scrollDetalle);

        add(panelDerecho, BorderLayout.CENTER);
    }

    // MOSTRAR VEHICULOS EN LA LISTA

    public void mostrarVehiculos(ArrayList<Vehiculo> vehiculos, String titulo)
    {
        vehiculosMostrados = vehiculos;
        etiquetaSeccion.setText(titulo);
        modeloLista.clear();

        for (Vehiculo vehiculo : vehiculosMostrados)
        {
            modeloLista.addElement(
                    vehiculo.getMarca() + " " + vehiculo.getModelo()
                    + "  |  " + vehiculo.obtenerTipoDeVehiculo());
        }

        if (vehiculosMostrados.isEmpty())
        {
            areaDetalles.setText("No hay vehiculos para mostrar.");
            panelFoto.mostrarImagen(null);
        }
        else
        {
            lista.setSelectedIndex(0);
            actualizarSeleccionado();
        }
    }

    // OBTENER VEHICULO SELECCIONADO

    public Vehiculo getVehiculoSeleccionado()
    {
        int posicion = lista.getSelectedIndex();

        if (posicion < 0 || posicion >= vehiculosMostrados.size())
        {
            return null;
        }

        return vehiculosMostrados.get(posicion);
    }

    // MOSTRAR DETALLES DEL VEHICULO

    public void actualizarSeleccionado()
    {
        Vehiculo vehiculo = getVehiculoSeleccionado();

        if (vehiculo == null)
        {
            areaDetalles.setText("Selecciona un vehiculo de la lista.");
            panelFoto.mostrarImagen(null);
            return;
        }

        String texto = "Tipo: " + vehiculo.obtenerTipoDeVehiculo()
                + "\nMarca: " + vehiculo.getMarca()
                + "\nModelo: " + vehiculo.getModelo()
                + "\nPrecio: " + vehiculo.getPrecio();

        if (vehiculo instanceof Carro)
        {
            Carro carro = (Carro) vehiculo;
            texto = texto + "\nPuertas: " + carro.getNumeroPuertas();
        }
        else if (vehiculo instanceof Moto)
        {
            Moto moto = (Moto) vehiculo;
            texto = texto + "\nCilindraje: " + moto.getCilindraje()
                    + "\nUso: " + moto.getTipoDeUso();
        }
        else if (vehiculo instanceof Camion)
        {
            Camion camion = (Camion) vehiculo;
            texto = texto + "\nCapacidad de carga: " + camion.getCapacidadCarga()
                    + "\nEjes: " + camion.getNumeroEjes();
        }
        else if (vehiculo instanceof Bicicleta)
        {
            Bicicleta bicicleta = (Bicicleta) vehiculo;
            texto = texto + "\nCambios: " + bicicleta.getNumeroCambios();
        }

        // MOSTRAR MOTOR

        texto = texto + "\n\n--- MOTOR ---";

        if (vehiculo.getMotor() instanceof MotorElectrico)
        {
            MotorElectrico motor = (MotorElectrico) vehiculo.getMotor();

            texto = texto + "\nTipo: Electrico"
                    + "\nPotencia: " + motor.getPotencia() + " HP"
                    + "\nBateria: " + motor.getCapacidadBateria() + " kWh";

            if (motor.getConsumoCada100Km() > 0)
            {
                texto = texto + "\nConsumo: " + motor.getConsumoCada100Km() + " kWh/100 km"
                        + "\nAutonomia estimada: " + motor.calcularAutonomia() + " km";
            }
            else
            {
                texto = texto + "\nConsumo: No configurado";
            }
        }
        else if (vehiculo.getMotor() instanceof MotorGasolina)
        {
            MotorGasolina motor = (MotorGasolina) vehiculo.getMotor();

            texto = texto + "\nTipo: Gasolina"
                    + "\nPotencia: " + motor.getPotencia() + " HP"
                    + "\nTanque: " + motor.getCapacidadTanque() + " L";
        }
        else
        {
            texto = texto + "\nSin motor asignado";
        }

        // MOSTRAR MANTENIMIENTO

        texto = texto + "\n\n--- MANTENIMIENTO ---";

        if (vehiculo.isMantenimientoRealizado())
        {
            texto = texto + "\nEstado: Realizado";
        }
        else
        {
            texto = texto + "\nEstado: Pendiente o sin registrar";
        }

        texto = texto + "\nMano de obra: " + vehiculo.getCostoManoObra()
                + "\nRepuestos: " + vehiculo.getCostoRepuestos()
                + "\nCosto total: " + vehiculo.calcularCostoMantenimiento();

        areaDetalles.setText(texto);
        areaDetalles.setCaretPosition(0);
        panelFoto.mostrarImagen(vehiculo.getRutaImagen());
    }

    // EVENTO AL SELECCIONAR OTRO VEHICULO

    @Override
    public void valueChanged(ListSelectionEvent evento)
    {
        if (!evento.getValueIsAdjusting())
        {
            actualizarSeleccionado();
        }
    }
}
