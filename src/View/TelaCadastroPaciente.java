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
import java.awt.*;
import java.io.IOException;
import java.util.Set;

public class TelaCadastroPaciente extends JFrame {

    private JPanel contentPane;
    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoIdade;
    private JTextField campoEndereco;
    private JTextField campoAltura; // Adicione este campo, se a altura for obrigatória
    private JComboBox<Farmaceutico> comboFarmaceutico;
    private JComboBox<DescricaoDoenca> comboDoenca;
    private JComboBox<Remedio> comboRemedio;
    private JButton salvarButton;
    private JButton removerButton;
    private JButton atualizarButton;
    private JButton voltarButton;

    private PacienteService pacienteService;
    private FarmaceuticoService farmaceuticoService;
    private DescricaoDoencaService doencaService;
    private RemedioService remedioService;

    public TelaCadastroPaciente() {
        setTitle("Gerenciar Pacientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 480);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new GridLayout(10, 2, 10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        campoNome = new JTextField();
        campoCpf = new JTextField();
        campoIdade = new JTextField();
        campoEndereco = new JTextField();
        campoAltura = new JTextField(); // Novo campo de Altura
        comboFarmaceutico = new JComboBox<>();
        comboDoenca = new JComboBox<>();
        comboRemedio = new JComboBox<>();

        salvarButton = new JButton("Salvar");
        removerButton = new JButton("Remover (Usar CPF)");
        atualizarButton = new JButton("Atualizar (Usar CPF)");
        voltarButton = new JButton("Voltar");

        contentPane.add(new JLabel("Nome:"));
        contentPane.add(campoNome);
        contentPane.add(new JLabel("CPF:"));
        contentPane.add(campoCpf);
        contentPane.add(new JLabel("Idade:"));
        contentPane.add(campoIdade);
        contentPane.add(new JLabel("Endereço:"));
        contentPane.add(campoEndereco);
        contentPane.add(new JLabel("Altura (cm):")); // Novo campo
        contentPane.add(campoAltura);               // Novo campo
        contentPane.add(new JLabel("Farmacêutico:"));
        contentPane.add(comboFarmaceutico);
        contentPane.add(new JLabel("Doença:"));
        contentPane.add(comboDoenca);
        contentPane.add(new JLabel("Remédio:"));
        contentPane.add(comboRemedio);

        contentPane.add(salvarButton);
        contentPane.add(removerButton);
        contentPane.add(atualizarButton);
        contentPane.add(voltarButton);

        try {
            pacienteService = new PacienteService();
            farmaceuticoService = new FarmaceuticoService();
            doencaService = new DescricaoDoencaService();
            remedioService = new RemedioService();
            carregarCombos();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Falha ao conectar com os arquivos de dados.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }

        salvarButton.addActionListener(e -> salvarPaciente());
        removerButton.addActionListener(e -> removerPaciente());
        atualizarButton.addActionListener(e -> atualizarPaciente());
        voltarButton.addActionListener(e -> {
            this.dispose();
        });
    }

    private void carregarCombos() {
        try {
            Set<Farmaceutico> farmaceuticos = farmaceuticoService.getAll();
            comboFarmaceutico.removeAllItems();
            for (Farmaceutico f : farmaceuticos) { comboFarmaceutico.addItem(f); }

            Set<DescricaoDoenca> doencas = doencaService.getAll();
            comboDoenca.removeAllItems();
            for (DescricaoDoenca d : doencas) { comboDoenca.addItem(d); }

            Set<Remedio> remedios = remedioService.getAll();
            comboRemedio.removeAllItems();
            for (Remedio r : remedios) { comboRemedio.addItem(r); }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao carregar dados de Farmacêuticos, Doenças ou Remédios.", "Erro de Carregamento", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarPaciente() {
        try {
            String nome = campoNome.getText();
            String cpf = campoCpf.getText();
            String endereco = campoEndereco.getText();
            Farmaceutico farmaceuticoSelecionado = (Farmaceutico) comboFarmaceutico.getSelectedItem();
            DescricaoDoenca doencaSelecionada = (DescricaoDoenca) comboDoenca.getSelectedItem();
            Remedio remedioSelecionado = (Remedio) comboRemedio.getSelectedItem();

            if (farmaceuticoSelecionado == null || doencaSelecionada == null || remedioSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um Farmacêutico, Doença e Remédio.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idade, altura;
            try {
                idade = Integer.parseInt(campoIdade.getText());
                altura = Integer.parseInt(campoAltura.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "A idade e a altura devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Paciente novoPaciente = new Paciente(
                    nome,
                    cpf,
                    idade,
                    endereco,
                    altura,
                    farmaceuticoSelecionado,
                    doencaSelecionada,
                    remedioSelecionado
            );

            if (pacienteService.salvar(novoPaciente)) {
                JOptionPane.showMessageDialog(this, "Paciente salvo com sucesso!");
                limparCampos();
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados: " + e.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        } catch (PacienteExisteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerPaciente() {
        String cpf = campoCpf.getText();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o CPF do paciente para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Paciente pacienteParaRemover = new Paciente(cpf);

            if (pacienteService.remover(pacienteParaRemover)) {
                JOptionPane.showMessageDialog(this, "Paciente removido com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Paciente com CPF " + cpf + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarPaciente() {
        String cpfAntigo = campoCpf.getText();

        if (cpfAntigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o CPF do paciente para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String novoNome = campoNome.getText();
            String novoEndereco = campoEndereco.getText();
            int novaIdade, novaAltura;
            try {
                novaIdade = Integer.parseInt(campoIdade.getText());
                novaAltura = Integer.parseInt(campoAltura.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "A idade e a altura devem ser números válidos (Novos Dados).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Farmaceutico novoFarmaceutico = (Farmaceutico) comboFarmaceutico.getSelectedItem();
            DescricaoDoenca novaDoenca = (DescricaoDoenca) comboDoenca.getSelectedItem();
            Remedio novoRemedio = (Remedio) comboRemedio.getSelectedItem();
            if (novoFarmaceutico == null || novaDoenca == null || novoRemedio == null) {
                JOptionPane.showMessageDialog(this, "Selecione o Farmacêutico, Doença e Remédio (Novos Dados).", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Paciente pacienteAtualizado = new Paciente(
                    novoNome,
                    cpfAntigo,
                    novaIdade,
                    novoEndereco,
                    novaAltura,
                    novoFarmaceutico,
                    novaDoenca,
                    novoRemedio
            );

            if (pacienteService.atualizar(pacienteAtualizado)) {
                JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Paciente com CPF " + cpfAntigo + " não encontrado para atualização.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoIdade.setText("");
        campoEndereco.setText("");
        campoAltura.setText("");
        comboFarmaceutico.setSelectedIndex(-1);
        comboDoenca.setSelectedIndex(-1);
        comboRemedio.setSelectedIndex(-1);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaCadastroPaciente tela = new TelaCadastroPaciente();
            tela.setVisible(true);
        });
    }
}