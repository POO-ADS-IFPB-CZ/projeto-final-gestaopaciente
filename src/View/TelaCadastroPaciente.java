package View;

import Model.DescricaoDoenca;
import Model.Farmaceutico;
import Model.Paciente;
import Model.Remedio;
import Service.DescricaoDoencaService;
import Service.FarmaceuticoService;
import Service.PacienteService;
import Service.RemedioService;
import exceptions.PacienteExisteException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.Set;

public class TelaCadastroPaciente extends JFrame {

    private JPanel contentPane;
    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoIdade;
    private JTextField campoEndereco;
    private JComboBox<Farmaceutico> comboFarmaceutico;
    private JComboBox<DescricaoDoenca> comboDoenca;
    private JComboBox<Remedio> comboRemedio;
    private JButton salvarButton;
    private JButton removerButton;
    private JButton atualizarButton;
    private JButton TelaPrincipalButton;
    private JTable tabelaPacientes;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    private PacienteService pacienteService;
    private FarmaceuticoService farmaceuticoService;
    private DescricaoDoencaService doencaService;
    private RemedioService remedioService;

    public TelaCadastroPaciente() {
        setTitle("Gerenciar Pacientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setContentPane(contentPane);

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

        // Configuração da JTable
        String[] colunas = {"Nome", "CPF", "Idade", "Endereço", "Farmacêutico", "Doença", "Remédio"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaPacientes.setModel(tableModel);

        carregarDadosNaTabela();
        popularCombos();

        salvarButton.addActionListener(e -> salvarPaciente());
        removerButton.addActionListener(e -> removerPaciente());
        atualizarButton.addActionListener(e -> atualizarPaciente());

        tabelaPacientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaPacientes.getSelectedRow() != -1) {
                preencherCamposComDadosDaTabela();
            }
        });
    }

    private void carregarDadosNaTabela() {
        tableModel.setRowCount(0);
        try {
            Set<Paciente> pacientes = pacienteService.getAll();
            for (Paciente p : pacientes) {
                Object[] rowData = {
                        p.getNome(),
                        p.getCpf(),
                        p.getIdade(),
                        p.getEndereco(),
                        p.getFarmaceutico().getNome(), // Chama o método getNome() do objeto Farmaceutico
                        p.getDoenca().getNome(),
                        p.getRemedio().getNome()
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

    private void popularCombos() {
        try {
            comboFarmaceutico.removeAllItems();
            for (Farmaceutico f : farmaceuticoService.getAll()) {
                comboFarmaceutico.addItem(f);
            }

            comboDoenca.removeAllItems();
            for (DescricaoDoenca d : doencaService.getAll()) {
                comboDoenca.addItem(d);
            }

            comboRemedio.removeAllItems();
            for (Remedio r : remedioService.getAll()) {
                comboRemedio.addItem(r);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao popular os combos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarPaciente() {
        try {
            String nome = campoNome.getText();
            String cpf = campoCpf.getText();
            int idade = Integer.parseInt(campoIdade.getText());
            String endereco = campoEndereco.getText();

            // Acesso aos objetos selecionados nos JComboBox
            Farmaceutico farmaceutico = (Farmaceutico) comboFarmaceutico.getSelectedItem();
            DescricaoDoenca doenca = (DescricaoDoenca) comboDoenca.getSelectedItem();
            Remedio remedio = (Remedio) comboRemedio.getSelectedItem();

            if (farmaceutico == null || doenca == null || remedio == null) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, selecione um farmacêutico, uma doença e um remédio.",
                        "Erro de Validação",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Paciente novoPaciente = new Paciente(nome, cpf, idade, endereco, farmaceutico, doenca, remedio);

            if (pacienteService.salvar(novoPaciente)) {
                JOptionPane.showMessageDialog(this, "Paciente salvo com sucesso!");
                limparCampos();
                carregarDadosNaTabela();
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao manipular o arquivo de dados.",
                    "Erro de Arquivo",
                    JOptionPane.ERROR_MESSAGE);
        } catch (PacienteExisteException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerPaciente() {
        int linhaSelecionada = tabelaPacientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cpfParaRemover = (String) tableModel.getValueAt(linhaSelecionada, 1);
        try {
            // Cria um objeto Paciente apenas com o CPF para a busca e remoção
            Paciente pacienteParaRemover = new Paciente(null, cpfParaRemover, 0, null, null, null, null);
            if (pacienteService.remover(pacienteParaRemover)) {
                JOptionPane.showMessageDialog(this, "Paciente removido com sucesso!");
                carregarDadosNaTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao remover o paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarPaciente() {
        int linhaSelecionada = tabelaPacientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nomeAntigo = (String) tableModel.getValueAt(linhaSelecionada, 0);
            String cpfAntigo = (String) tableModel.getValueAt(linhaSelecionada, 1);
            // Remova o paciente antigo primeiro
            Paciente pacienteAntigo = new Paciente(nomeAntigo, cpfAntigo, 0, null, null, null, null);
            pacienteService.remover(pacienteAntigo);

            // Crie e salve o paciente atualizado com os dados do formulário
            String novoNome = campoNome.getText();
            String novoCpf = campoCpf.getText();
            int novaIdade = Integer.parseInt(campoIdade.getText());
            String novoEndereco = campoEndereco.getText();

            Farmaceutico farmaceutico = (Farmaceutico) comboFarmaceutico.getSelectedItem();
            DescricaoDoenca doenca = (DescricaoDoenca) comboDoenca.getSelectedItem();
            Remedio remedio = (Remedio) comboRemedio.getSelectedItem();

            if (farmaceutico == null || doenca == null || remedio == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um farmacêutico, uma doença e um remédio para a atualização.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Paciente pacienteAtualizado = new Paciente(novoNome, novoCpf, novaIdade, novoEndereco, farmaceutico, doenca, remedio);
            if (pacienteService.salvar(pacienteAtualizado)) {
                JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");
                limparCampos();
                carregarDadosNaTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao atualizar o paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (PacienteExisteException e) {
            throw new RuntimeException(e);
        }
    }

    private void preencherCamposComDadosDaTabela() {
        int linhaSelecionada = tabelaPacientes.getSelectedRow();
        if (linhaSelecionada != -1) {
            campoNome.setText((String) tableModel.getValueAt(linhaSelecionada, 0));
            campoCpf.setText((String) tableModel.getValueAt(linhaSelecionada, 1));
            campoIdade.setText(tableModel.getValueAt(linhaSelecionada, 2).toString());
            campoEndereco.setText((String) tableModel.getValueAt(linhaSelecionada, 3));
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoIdade.setText("");
        campoEndereco.setText("");
        comboFarmaceutico.setSelectedIndex(-1);
        comboDoenca.setSelectedIndex(-1);
        comboRemedio.setSelectedIndex(-1);
    }
}