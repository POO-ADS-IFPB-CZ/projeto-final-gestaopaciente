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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

    private PacienteService pacienteService;
    private FarmaceuticoService farmaceuticoService;
    private DescricaoDoencaService doencaService;
    private RemedioService remedioService;

    public TelaCadastroPaciente() {
        setTitle("Gerenciar Pacientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setContentPane(contentPane);

        try {
            pacienteService = new PacienteService();
            farmaceuticoService = new FarmaceuticoService();
            doencaService = new DescricaoDoencaService();
            remedioService = new RemedioService();
            carregarCombos();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao conectar com os arquivos de dados.",
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE);
        }

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarPaciente();
            }
        });
    }

    private void carregarCombos() {
        try {
            comboFarmaceutico.removeAllItems();
            comboDoenca.removeAllItems();
            comboRemedio.removeAllItems();

            for (Farmaceutico f : farmaceuticoService.getAll()) {
                comboFarmaceutico.addItem(f);
            }

            for (DescricaoDoenca d : doencaService.getAll()) {
                comboDoenca.addItem(d);
            }

            for (Remedio r : remedioService.getAll()) {
                comboRemedio.addItem(r);
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao carregar os dados para os combos.",
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
            Farmaceutico farmaceutico = (Farmaceutico) comboFarmaceutico.getSelectedItem();
            DescricaoDoenca doenca = (DescricaoDoenca) comboDoenca.getSelectedItem();
            Remedio remedio = (Remedio) comboRemedio.getSelectedItem();

            Paciente novoPaciente = new Paciente(nome, cpf, idade, endereco, farmaceutico, doenca, remedio);

            if (pacienteService.salvar(novoPaciente)) {
                JOptionPane.showMessageDialog(this, "Paciente salvo com sucesso!");
                limparCampos();
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

    private void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoIdade.setText("");
        campoEndereco.setText("");
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


