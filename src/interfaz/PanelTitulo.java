
package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel que muestra el titulo del concesionario.
 */
public class PanelTitulo extends JPanel
{
    // ATRIBUTOS

    private JLabel titulo;
    private JLabel subtitulo;

    // CONSTRUCTOR

    public PanelTitulo()
    {
        setLayout(new BorderLayout());
        setBackground(new Color(29, 50, 78));
        setBorder(BorderFactory.createEmptyBorder(14, 22, 14, 22));

        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setOpaque(false);

        titulo = new JLabel("MI CONCESIONARIO");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        subtitulo = new JLabel("Inventario, ventas y taller");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(202, 219, 239));

        panelTextos.add(titulo);
        panelTextos.add(subtitulo);

        add(panelTextos, BorderLayout.CENTER);
    }
}
