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
import java.awt.*;
import java.io.IOException;
import java.util.Set;

public class TelaVisualizacaoPaciente extends JFrame {

    private JPanel contentPane;
    private JTable tabelaDados;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JButton voltarButton; // Botão de Voltar

    private PacienteService pacienteService;

    public TelaVisualizacaoPaciente() {
        setTitle("Dados do Paciente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            pacienteService = new PacienteService();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao conectar com os arquivos de dados.",
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Configuração da Tabela
        String[] colunas = {"Farmacêutico", "Paciente", "Descrição da Doença", "Remédio"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaDados = new JTable(tableModel);
        scrollPane = new JScrollPane(tabelaDados);
        add(scrollPane, BorderLayout.CENTER);
        voltarButton = new JButton("Voltar para Tela Principal");
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.add(voltarButton);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarDadosNaTabela();

        voltarButton.addActionListener(e -> {
            this.dispose();
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
        });
    }

    private void carregarDadosNaTabela() {
        tableModel.setRowCount(0);

        try {
            Set<Paciente> pacientes = pacienteService.getAll();

            for (Paciente paciente : pacientes) {
                Object[] rowData = {
                        paciente.getFarmaceutico().getNome(),
                        paciente.getNome(),
                        paciente.getDoenca().getNome(),
                        paciente.getRemedio().getNome()
                };
                tableModel.addRow(rowData);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao carregar os dados. Verifique os arquivos.",
                    "Erro de Carregamento",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}