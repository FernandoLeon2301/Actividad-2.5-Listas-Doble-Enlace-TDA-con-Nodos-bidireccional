/*
 LEON GAMEZ FERNANDO
 CAMACHO GUERRA JACOBO
 GONZALEZ ALVARADO ALEXA MICHELLE
*/

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AplicacionGUI extends JFrame {
    private ListaBidireccional<Cancion> lista;
    
    // Componentes de Entrada
    private JTextField txtId, txtTitulo, txtArtista, txtDuracion, txtPosicion, txtBusqueda;
    
    // Componentes de Visualización
    private JTable tablaLista;
    private DefaultTableModel modeloTabla;
    private JTextArea areaRecorrido;

    public AplicacionGUI() {
        lista = new ListaBidireccional<>();
        configurarVentana();
        configurarPanelEntrada();
        configurarPanelAcciones();
        configurarPanelVisualizacion();
    }

    private void configurarVentana() {
        setTitle("Gestor de Lista Bidireccional de Canciones");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void configurarPanelEntrada() {
        JPanel panelEntrada = new JPanel(new GridLayout(2, 4, 5, 5));
        panelEntrada.setBorder(BorderFactory.createTitledBorder("Datos de la Canción (Record)"));
        
        panelEntrada.add(new JLabel("ID:")); txtId = new JTextField(); panelEntrada.add(txtId);
        panelEntrada.add(new JLabel("Título:")); txtTitulo = new JTextField(); panelEntrada.add(txtTitulo);
        panelEntrada.add(new JLabel("Artista:")); txtArtista = new JTextField(); panelEntrada.add(txtArtista);
        panelEntrada.add(new JLabel("Duración (seg):")); txtDuracion = new JTextField(); panelEntrada.add(txtDuracion);
        
        add(panelEntrada, BorderLayout.NORTH);
    }

    private void configurarPanelAcciones() {
        JPanel panelAcciones = new JPanel(new GridLayout(4, 4, 5, 5));
        panelAcciones.setBorder(BorderFactory.createTitledBorder("Operaciones"));

        // Posición y Búsqueda
        panelAcciones.add(new JLabel("Índice/Pos:")); txtPosicion = new JTextField(); panelAcciones.add(txtPosicion);
        panelAcciones.add(new JLabel("Buscar Título:")); txtBusqueda = new JTextField(); panelAcciones.add(txtBusqueda);

        // Botones de Inserción
        JButton btnInsIni = new JButton("Insertar Inicio");
        JButton btnInsFin = new JButton("Insertar Final");
        JButton btnInsPos = new JButton("Insertar Posición");
        JButton btnRemIni = new JButton("Remover Inicio");
        
        // Botones de Remoción
        JButton btnRemFin = new JButton("Remover Final");
        JButton btnRemPos = new JButton("Remover Posición");
        JButton btnVaciar = new JButton("Vaciar Lista");
        JButton btnBuscarVal = new JButton("Buscar por Valor");

        // Botones de Muestra y Recorrido
        JButton btnMosIni = new JButton("Mostrar Inicio");
        JButton btnMosFin = new JButton("Mostrar Final");
        JButton btnMosPos = new JButton("Mostrar Posición");
        JButton btnBuscarPos = new JButton("Buscar por Posición");
        
        JButton btnRecAdel = new JButton("Recorrer Adelante");
        JButton btnRecAtras = new JButton("Recorrer Atrás");
        JButton btnActualizar = new JButton("Actualizar Tabla");
        JButton btnSalir = new JButton("Salir");

        // Asignación de Eventos (ActionListeners)
        ActionListener insertarListener = e -> procesarInsercion(e.getSource());
        btnInsIni.addActionListener(insertarListener);
        btnInsFin.addActionListener(insertarListener);
        btnInsPos.addActionListener(insertarListener);

        ActionListener removerListener = e -> procesarRemocion(e.getSource());
        btnRemIni.addActionListener(removerListener);
        btnRemFin.addActionListener(removerListener);
        btnRemPos.addActionListener(removerListener);

        btnVaciar.addActionListener(e -> { lista.vaciar(); actualizarTabla(); JOptionPane.showMessageDialog(this, "Lista vaciada."); });
        btnBuscarVal.addActionListener(e -> procesarBusquedaValor());
        
        ActionListener mostrarListener = e -> procesarMuestra(e.getSource());
        btnMosIni.addActionListener(mostrarListener);
        btnMosFin.addActionListener(mostrarListener);
        btnMosPos.addActionListener(mostrarListener);
        btnBuscarPos.addActionListener(mostrarListener);

        btnRecAdel.addActionListener(e -> areaRecorrido.setText(lista.recorrerAdelante()));
        btnRecAtras.addActionListener(e -> areaRecorrido.setText(lista.recorrerAtras()));
        btnActualizar.addActionListener(e -> actualizarTabla());
        btnSalir.addActionListener(e -> System.exit(0));

        // Agregar botones al panel
        panelAcciones.add(btnInsIni); panelAcciones.add(btnInsFin); panelAcciones.add(btnInsPos); panelAcciones.add(btnRemIni);
        panelAcciones.add(btnRemFin); panelAcciones.add(btnRemPos); panelAcciones.add(btnVaciar); panelAcciones.add(btnBuscarVal);
        panelAcciones.add(btnMosIni); panelAcciones.add(btnMosFin); panelAcciones.add(btnMosPos); panelAcciones.add(btnBuscarPos);
        panelAcciones.add(btnRecAdel); panelAcciones.add(btnRecAtras); panelAcciones.add(btnActualizar); panelAcciones.add(btnSalir);

        add(panelAcciones, BorderLayout.CENTER);
    }

    private void configurarPanelVisualizacion() {
        JPanel panelVisualizacion = new JPanel(new BorderLayout());
        panelVisualizacion.setBorder(BorderFactory.createTitledBorder("Estado de la Lista y Recorridos"));

        // Tabla para mostrar la lista
        String[] columnas = {"ID", "Título", "Artista", "Duración (s)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Solo lectura
        };
        tablaLista = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaLista);
        scrollTabla.setPreferredSize(new Dimension(0, 200));

        // Área de texto para recorridos
        areaRecorrido = new JTextArea(5, 20);
        areaRecorrido.setEditable(false);
        areaRecorrido.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollArea = new JScrollPane(areaRecorrido);

        panelVisualizacion.add(scrollTabla, BorderLayout.CENTER);
        panelVisualizacion.add(scrollArea, BorderLayout.SOUTH);

        add(panelVisualizacion, BorderLayout.SOUTH);
    }

    // --- MÉTODOS DE PROCESAMIENTO Y VALIDACIÓN ---

    private void procesarInsercion(Object source) {
        try {
            // Validación de formatos
            int id = Integer.parseInt(txtId.getText());
            String titulo = txtTitulo.getText().trim();
            String artista = txtArtista.getText().trim();
            int duracion = Integer.parseInt(txtDuracion.getText());

            // Instanciación del record (lanza IllegalArgumentException si los datos son inválidos)
            Cancion nuevaCancion = new Cancion(id, titulo, artista, duracion);

            String btnText = ((JButton) source).getText();
            if (btnText.contains("Inicio")) {
                lista.insertarInicio(nuevaCancion);
            } else if (btnText.contains("Final")) {
                lista.insertarFinal(nuevaCancion);
            } else if (btnText.contains("Posición")) {
                int pos = Integer.parseInt(txtPosicion.getText());
                lista.insertarPosicion(nuevaCancion, pos);
            }
            
            limpiarCamposEntrada();
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: ID y Duración deben ser números enteros.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void procesarRemocion(Object source) {
        try {
            String btnText = ((JButton) source).getText();
            Cancion removida;
            if (btnText.contains("Inicio")) {
                removida = lista.removerInicio();
            } else if (btnText.contains("Final")) {
                removida = lista.removerFinal();
            } else {
                int pos = Integer.parseInt(txtPosicion.getText());
                removida = lista.removerPosicion(pos);
            }
            JOptionPane.showMessageDialog(this, "Removido: " + removida.toString());
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una posición numérica válida.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException | IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Operación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void procesarMuestra(Object source) {
        try {
            String btnText = ((JButton) source).getText();
            Cancion resultado;
            if (btnText.contains("Inicio")) {
                resultado = lista.mostrarInicio();
            } else if (btnText.contains("Final")) {
                resultado = lista.mostrarFinal();
            } else { // Posición o Buscar por Posición
                int pos = Integer.parseInt(txtPosicion.getText());
                resultado = lista.buscarPorPosicion(pos);
            }
            JOptionPane.showMessageDialog(this, "Resultado:\n" + resultado.toString(), "Consulta Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una posición numérica válida.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException | IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Operación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void procesarBusquedaValor() {
        try {
            String valor = txtBusqueda.getText().trim();
            if (valor.isEmpty()) throw new IllegalArgumentException("El campo de búsqueda no puede estar vacío.");
            String resultado = lista.buscarPorValor(valor);
            JOptionPane.showMessageDialog(this, resultado, "Búsqueda por Valor", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        if (!lista.estaVacia()) {
            // Recorremos hacia adelante para llenar la tabla
            String[] lineas = lista.recorrerAdelante().split("\n");
            for (String linea : lineas) {
                // Parseo simple de la salida del toString del record para la tabla
                // Formato: [ID] Titulo - Artista (M:S)
                String[] partes = linea.split(" - ");
                String idTitulo = partes[0];
                String artistaDur = partes[1];
                
                String id = idTitulo.substring(1, idTitulo.indexOf("]"));
                String titulo = idTitulo.substring(idTitulo.indexOf("] ") + 2).trim();
                String artista = artistaDur.substring(0, artistaDur.lastIndexOf("(")).trim();
                String duracion = artistaDur.substring(artistaDur.lastIndexOf("(") + 1, artistaDur.length() - 1).replace(":", "");
                
                // Convertir M:S a segundos totales para la tabla
                String[] minSeg = duracion.split(":");
                int segTotal = Integer.parseInt(minSeg[0]) * 60 + Integer.parseInt(minSeg[1]);

                modeloTabla.addRow(new Object[]{id, titulo, artista, segTotal});
            }
        }
        areaRecorrido.setText(""); // Limpiar área de recorrido al actualizar
    }

    private void limpiarCamposEntrada() {
        txtId.setText(""); txtTitulo.setText(""); txtArtista.setText(""); txtDuracion.setText("");
        txtPosicion.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AplicacionGUI gui = new AplicacionGUI();
            gui.setVisible(true);
        });
    }
}