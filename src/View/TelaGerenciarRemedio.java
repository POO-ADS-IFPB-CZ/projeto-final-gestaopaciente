package View;

import Model.Remedio;
import Service.RemedioService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.Set;

public class TelaGerenciarRemedio extends JFrame {

    private JPanel contentPane;
    private JTextField campoNome;
    private JTextField campoDosagem;
    private JButton salvarButton;
    private JButton removerButton;
    private JButton atualizarButton;
    private JTable tabelaRemedios;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    private RemedioService remedioService;

    public TelaGerenciarRemedio() {
        setTitle("Gerenciar Remédios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(contentPane);

        try {
            remedioService = new RemedioService();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Falha ao conectar com o arquivo de dados dos remédios.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }

        String[] colunas = {"Nome", "Dosagem"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaRemedios.setModel(tableModel);

        salvarButton.addActionListener(e -> salvarRemedio());
        removerButton.addActionListener(e -> removerRemedio());
        atualizarButton.addActionListener(e -> atualizarRemedio());

        carregarDadosNaTabela();
    }

    private void carregarDadosNaTabela() {
        tableModel.setRowCount(0);
        try {
            Set<Remedio> remedios = remedioService.getAll();
            for (Remedio r : remedios) {
                Object[] rowData = {r.getNome(), r.getDosagem()};
                tableModel.addRow(rowData);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao carregar os dados. Verifique os arquivos.", "Erro de Carregamento", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarRemedio() {
        try {
            String nome = campoNome.getText();
            String dosagem = campoDosagem.getText();

            if (nome.isEmpty() || dosagem.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Remedio novoRemedio = new Remedio(nome, dosagem);
            if (remedioService.salvar(novoRemedio)) {
                JOptionPane.showMessageDialog(this, "Remédio salvo com sucesso!");
                limparCampos();
                carregarDadosNaTabela();
            } else {
                JOptionPane.showMessageDialog(this, "O remédio já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerRemedio() {
        int linhaSelecionada = tabelaRemedios.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um remédio para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nome = (String) tableModel.getValueAt(linhaSelecionada, 0);
            String dosagem = (String) tableModel.getValueAt(linhaSelecionada, 1);
            Remedio remedioParaRemover = new Remedio(nome, dosagem);

            if (remedioService.remover(remedioParaRemover)) {
                JOptionPane.showMessageDialog(this, "Remédio removido com sucesso!");
                carregarDadosNaTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao remover o remédio.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarRemedio() {
        int linhaSelecionada = tabelaRemedios.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um remédio para atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nomeAntigo = (String) tableModel.getValueAt(linhaSelecionada, 0);
            String dosagemAntiga = (String) tableModel.getValueAt(linhaSelecionada, 1);
            Remedio remedioAntigo = new Remedio(nomeAntigo, dosagemAntiga);

            String novoNome = campoNome.getText();
            String novaDosagem = campoDosagem.getText();
            Remedio remedioAtualizado = new Remedio(novoNome, novaDosagem);

            if (remedioService.remover(remedioAntigo) && remedioService.salvar(remedioAtualizado)) {
                JOptionPane.showMessageDialog(this, "Remédio atualizado com sucesso!");
                limparCampos();
                carregarDadosNaTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao atualizar o remédio.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoDosagem.setText("");
    }
}