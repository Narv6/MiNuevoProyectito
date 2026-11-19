
package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Panel que muestra la fotografia del vehiculo seleccionado.
 */
public class PanelFoto extends JPanel
{
    // ATRIBUTO

    private JLabel etiquetaFoto;

    // CONSTRUCTOR

    public PanelFoto()
    {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Imagen del vehiculo"));

        etiquetaFoto = new JLabel("SIN IMAGEN", SwingConstants.CENTER);
        etiquetaFoto.setForeground(new Color(110, 127, 145));

        add(etiquetaFoto, BorderLayout.CENTER);
    }

    // MOSTRAR LA IMAGEN DEL VEHICULO

    public void mostrarImagen(String ruta)
    {
        etiquetaFoto.setIcon(null);

        if (ruta == null || ruta.isEmpty() || !new File(ruta).exists())
        {
            etiquetaFoto.setText("SIN IMAGEN");

            return;
        }

        ImageIcon imagenOriginal = new ImageIcon(ruta);

        int ancho = imagenOriginal.getIconWidth();
        int alto = imagenOriginal.getIconHeight();

        if (ancho <= 0 || alto <= 0)
        {
            etiquetaFoto.setText("No se pudo abrir la imagen");

            return;
        }

        // AJUSTAR EL TAMAÑO SIN DEFORMAR LA IMAGEN

        double escala = Math.min(320.0 / ancho, 230.0 / alto);

        int nuevoAncho = (int) (ancho * escala);
        int nuevoAlto = (int) (alto * escala);

        Image imagen = imagenOriginal.getImage().getScaledInstance(
                nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);

        etiquetaFoto.setText("");
        etiquetaFoto.setIcon(new ImageIcon(imagen));
    }
}
