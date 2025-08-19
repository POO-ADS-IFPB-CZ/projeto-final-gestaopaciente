package View;


import Model.DescricaoDoenca;
import Model.Farmaceutico;
import Model.Paciente;
import Model.Remedio;
import Service.DescricaoDoencaService;
import Service.FarmaceuticoService;
import Service.PacienteService;
import Service.RemedioService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

public class TelaVisualizacaoPaciente extends JFrame {

    private JPanel contentPane;
    private JTable tabelaDados;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    private PacienteService pacienteService;
    private FarmaceuticoService farmaceuticoService;
    private DescricaoDoencaService doencaService;
    private RemedioService remedioService;

    public TelaVisualizacaoPaciente() {
        setTitle("Dados do Paciente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        try {
            pacienteService = new PacienteService();
            farmaceuticoService = new FarmaceuticoService();
            doencaService = new DescricaoDoencaService();
            remedioService = new RemedioService();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao conectar com os arquivos de dados.",
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE);
        }

        String[] colunas = {"Farmacêutico", "Paciente", "Descrição da Doença", "Remédio"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaDados = new JTable(tableModel);
        scrollPane = new JScrollPane(tabelaDados);
        add(scrollPane);
        carregarDadosNaTabela();
    }

    private void carregarDadosNaTabela() {
        tableModel.setRowCount(0);

        try {
            Set<Paciente> pacientes = pacienteService.getAll();
            Set<Farmaceutico> farmaceuticos = farmaceuticoService.getAll();
            Set<DescricaoDoenca> doencas = doencaService.getAll();
            Set<Remedio> remedios = remedioService.getAll();

            for (Paciente paciente : pacientes) {
                for (Farmaceutico farmaceutico : farmaceuticos) { // Lógica simplificada
                    Object[] rowData = {
                            farmaceutico.getcrf(),
                            paciente.getNome(),
                            Remedio.getdosagem().getNome(),
                            paciente.getRemedio().getNome()
                    };
                    tableModel.addRow(rowData);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao carregar os dados. Verifique os arquivos.",
                    "Erro de Carregamento",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}