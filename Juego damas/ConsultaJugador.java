import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

public class ConsultaJugador extends JDialog {

    public ConsultaJugador() {
        init();
    }

    private void init() {
        setModal(true);
        setSize(1280, 720);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Consulta de jugadores");
        lbCantidad = new JLabel("Cantidad de jugadores");
        lbCantidad.setBounds(60, 400, 200, 30);
        add(lbCantidad);
        tffiltro = new JTextField(20);
        tffiltro.setBounds(200, 50, 200, 30);
        coBoBusqueda = new JComboBox<>();

        for (int i = 0; i < BUSQUEDA.length; ++i) {
            coBoBusqueda.addItem(BUSQUEDA[i]);
        }

        coBoBusqueda.setBounds(50, 50, 100, 30);
        add(coBoBusqueda);
        add(tffiltro);

        barra = new JScrollPane();
        barra.setBounds(60, 100, 1100, 200);
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int colum) {
                return false;
            }
        };
        tabla = new JTable();
        tabla.setModel(model);

        for (int i = 0; i < columnas.length; ++i) {
            model.addColumn(columnas[i]);
        }

        barra.setViewportView(tabla);
        tabla.setDragEnabled(false);
        add(barra);

        try {
            for (Jugador item : VentanaPrincipal.listaJugadores) {

                Jugador j = item;
                ;

                String[] jugador = new String[5];
                jugador[0] = ClaseEstatica.convertirString(j.getId());
                jugador[1] = j.getNombre();
                jugador[2] = ClaseEstatica.convertirString(j.getGanadas());
                jugador[3] = ClaseEstatica.convertirString(j.getPerdidas());
                jugador[4] = ClaseEstatica.convertirString(j.getEmpates());
                model.addRow(jugador);

            }
            lbCantidad.setText("Cantidad Total: " + tabla.getRowCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tffiltro.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (tffiltro.getText());
                tffiltro.setText(cadena);
                repaint();
                filtro();
                lbCantidad.setText("Cantidad Total: " + tabla.getRowCount());
            }
        });
        trsfiltro = new TableRowSorter(tabla.getModel());
        tabla.setRowSorter(trsfiltro);
        setVisible(true);
    }

    public void filtro() {
        int busqueda = 0;
        if (coBoBusqueda.getSelectedItem().equals(BUSQUEDA[0]))
            busqueda = 0;
        else if (coBoBusqueda.getSelectedItem().equals(BUSQUEDA[1]))
            busqueda = 1;

        trsfiltro.setRowFilter(RowFilter.regexFilter(tffiltro.getText(), busqueda));
    }

    private DefaultTableModel model;
    private JTable tabla;
    private JScrollPane barra;
    private String[] columnas = { "Id", "Nombre", "Ganadas", "Perdidas", "Empate" };
    public TableRowSorter trsfiltro;
    private JTextField tffiltro;
    private JComboBox coBoBusqueda;
    private static final String[] BUSQUEDA = { "Codigo", "Nombre" };
    private JLabel lbCantidad;

}
