package View;

import Model.DescricaoDoenca;
import Service.DescricaoDoencaService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.Set;

public class TelaGerenciarDescricaoDoenca extends JFrame {

    private JPanel contentPane;
    private JTextField campoNome;
    private JTextField campoSintomas;
    private JButton salvarButton;
    private JButton removerButton;
    private JButton atualizarButton;
    private JTable tabelaDoencas;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    private DescricaoDoencaService doencaService;
    private JTextField nomeDoRemédioTextField;
    private JTextField dosagemRemédioTextField;
    private JTable table1;

    public TelaGerenciarDescricaoDoenca() {
        setTitle("Gerenciar Descrições de Doença");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(contentPane);

        try {
            doencaService = new DescricaoDoencaService();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Falha ao conectar com o arquivo de dados das doenças.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }

        String[] colunas = {"Nome", "Sintomas"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaDoencas.setModel(tableModel);

        salvarButton.addActionListener(e -> salvarDoenca());
        removerButton.addActionListener(e -> removerDoenca());
        atualizarButton.addActionListener(e -> atualizarDoenca());

        carregarDadosNaTabela();
    }

    private void carregarDadosNaTabela() {
        tableModel.setRowCount(0);
        try {
            Set<DescricaoDoenca> doencas = doencaService.getAll();
            for (DescricaoDoenca d : doencas) {
                Object[] rowData = {d.getNome(), d.getSintomas()};
                tableModel.addRow(rowData);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao carregar os dados. Verifique os arquivos.", "Erro de Carregamento", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarDoenca() {
        try {
            String nome = campoNome.getText();
            String sintomas = campoSintomas.getText();

            if (nome.isEmpty() || sintomas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DescricaoDoenca novaDoenca = new DescricaoDoenca(nome, sintomas);
            if (doencaService.salvar(novaDoenca)) {
                JOptionPane.showMessageDialog(this, "Doença salva com sucesso!");
                limparCampos();
                carregarDadosNaTabela();
            } else {
                JOptionPane.showMessageDialog(this, "A doença já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerDoenca() {
        int linhaSelecionada = tabelaDoencas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma doença para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nome = (String) tableModel.getValueAt(linhaSelecionada, 0);
            String sintomas = (String) tableModel.getValueAt(linhaSelecionada, 1);
            DescricaoDoenca doencaParaRemover = new DescricaoDoenca(nome, sintomas);

            if (doencaService.remover(doencaParaRemover)) {
                JOptionPane.showMessageDialog(this, "Doença removida com sucesso!");
                carregarDadosNaTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao remover a doença.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarDoenca() {
        int linhaSelecionada = tabelaDoencas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma doença para atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nomeAntigo = (String) tableModel.getValueAt(linhaSelecionada, 0);
            String sintomasAntigo = (String) tableModel.getValueAt(linhaSelecionada, 1);
            DescricaoDoenca doencaAntiga = new DescricaoDoenca(nomeAntigo, sintomasAntigo);

            String novoNome = campoNome.getText();
            String novosSintomas = campoSintomas.getText();
            DescricaoDoenca doencaAtualizada = new DescricaoDoenca(novoNome, novosSintomas);

            if (doencaService.remover(doencaAntiga) && doencaService.salvar(doencaAtualizada)) {
                JOptionPane.showMessageDialog(this, "Doença atualizada com sucesso!");
                limparCampos();
                carregarDadosNaTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao atualizar a doença.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoSintomas.setText("");
    }
}