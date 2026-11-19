
package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel que muestra los mensajes de las operaciones.
 */
public class PanelEstado extends JPanel
{
    // ATRIBUTO

    private JLabel etiquetaEstado;

    // CONSTRUCTOR

    public PanelEstado()
    {
        setLayout(new BorderLayout());
        setBackground(new Color(234, 241, 250));
        setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        etiquetaEstado = new JLabel(
                "Selecciona un vehiculo para consultar sus datos e imagen.");

        etiquetaEstado.setFont(new Font("Arial", Font.PLAIN, 13));
        etiquetaEstado.setForeground(new Color(35, 61, 90));

        add(etiquetaEstado, BorderLayout.CENTER);
    }

    // CAMBIAR EL MENSAJE DE ESTADO

    public void mostrarMensaje(String mensaje)
    {
        etiquetaEstado.setText(mensaje);
    }
}
