
package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Panel con los botones del concesionario.
 */
public class PanelBotones extends JPanel
{
    // ATRIBUTOS

    private JButton botonInventario;
    private JButton botonAgregar;
    private JButton botonElegirFoto;
    private JButton botonQuitarFoto;
    private JButton botonMotor;
    private JButton botonAutonomia;
    private JButton botonOrdenar;
    private JButton botonBuscar;
    private JButton botonFiltrar;
    private JButton botonVender;
    private JButton botonCancelarVenta;
    private JButton botonEnviarTaller;
    private JButton botonAtenderTaller;
    private JButton botonVerTaller;
    private JButton botonVerVentas;
    private JButton botonGuardar;

    // CONSTRUCTOR

    public PanelBotones(ActionListener oyente)
    {
        setLayout(new GridLayout(8, 2, 8, 8));
        setPreferredSize(new Dimension(340, 550));
        setBackground(new Color(234, 241, 250));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        botonInventario = crearBoton("Ver inventario", oyente);
        botonAgregar = crearBoton("Agregar vehiculo", oyente);
        botonElegirFoto = crearBoton("Asignar imagen", oyente);
        botonQuitarFoto = crearBoton("Quitar imagen", oyente);
        botonMotor = crearBoton("Asignar motor", oyente);
        botonAutonomia = crearBoton("Ver autonomia", oyente);
        botonOrdenar = crearBoton("Ordenar por precio", oyente);
        botonBuscar = crearBoton("Buscar por precio", oyente);
        botonFiltrar = crearBoton("Filtrar por tipo", oyente);
        botonVender = crearBoton("Registrar venta", oyente);
        botonCancelarVenta = crearBoton("Cancelar venta", oyente);
        botonEnviarTaller = crearBoton("Enviar al taller", oyente);
        botonAtenderTaller = crearBoton("Atender taller", oyente);
        botonVerTaller = crearBoton("Ver taller", oyente);
        botonVerVentas = crearBoton("Ver ventas", oyente);
        botonGuardar = crearBoton("Guardar datos", oyente);

        botonAgregar.setBackground(new Color(38, 97, 159));
        botonAgregar.setForeground(Color.WHITE);
        botonElegirFoto.setBackground(new Color(38, 97, 159));
        botonElegirFoto.setForeground(Color.WHITE);

        // AGREGAR BOTONES

        add(botonInventario);
        add(botonAgregar);
        add(botonElegirFoto);
        add(botonQuitarFoto);
        add(botonMotor);
        add(botonAutonomia);
        add(botonOrdenar);
        add(botonBuscar);
        add(botonFiltrar);
        add(botonVender);
        add(botonCancelarVenta);
        add(botonEnviarTaller);
        add(botonAtenderTaller);
        add(botonVerTaller);
        add(botonVerVentas);
        add(botonGuardar);
    }

    // CREAR BOTONES

    private JButton crearBoton(String texto, ActionListener oyente)
    {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.PLAIN, 12));
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.addActionListener(oyente);

        return boton;
    }

    // GETTERS

    public JButton getBotonInventario()
    {
        return botonInventario;
    }

    public JButton getBotonAgregar()
    {
        return botonAgregar;
    }

    public JButton getBotonElegirFoto()
    {
        return botonElegirFoto;
    }

    public JButton getBotonQuitarFoto()
    {
        return botonQuitarFoto;
    }

    public JButton getBotonMotor()
    {
        return botonMotor;
    }

    public JButton getBotonAutonomia()
    {
        return botonAutonomia;
    }

    public JButton getBotonOrdenar()
    {
        return botonOrdenar;
    }

    public JButton getBotonBuscar()
    {
        return botonBuscar;
    }

    public JButton getBotonFiltrar()
    {
        return botonFiltrar;
    }

    public JButton getBotonVender()
    {
        return botonVender;
    }

    public JButton getBotonCancelarVenta()
    {
        return botonCancelarVenta;
    }

    public JButton getBotonEnviarTaller()
    {
        return botonEnviarTaller;
    }

    public JButton getBotonAtenderTaller()
    {
        return botonAtenderTaller;
    }

    public JButton getBotonVerTaller()
    {
        return botonVerTaller;
    }

    public JButton getBotonVerVentas()
    {
        return botonVerVentas;
    }

    public JButton getBotonGuardar()
    {
        return botonGuardar;
    }
}
