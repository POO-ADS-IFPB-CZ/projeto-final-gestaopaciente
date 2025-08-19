package View;

import Model.Paciente;
import Service.PacienteService;
import exceptions.PacienteExisteException;
import Dao.GenericDao;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TelaCadastroPaciente extends JFrame {

    private JPanel contentPane;
    private JTextField campoNome;
    private JTextField campoCpf; // Adicione este campo
    private JTextField campoidade;
    private JTextField campoendereço;
    private JButton RemedioButton;
    private JButton DescriçãoDoençaButton;
    private JButton salvarButton;
    private JButton removerButton; // Adicionado
    private JButton atualizarButton; // Adicionado
    private JTable tabelaPacientes;
    private JScrollPane scrollPane;

    private PacienteService pacienteService;

    public TelaCadastroPaciente() {
        setTitle("Gerenciar Pacientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        try {
            pacienteService = new PacienteService();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao conectar com o arquivo de dados dos pacientes.",
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Ação para o botão SALVAR
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarPaciente();
            }
        });

        // Ação para o botão REMOVER
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerPaciente();
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarPaciente();
            }
        });

    }

    private void salvarPaciente() {
        try {
            String nome = campoNome.getText();
            String cpf = campoCpf.getText();

            // Cria um novo objeto Paciente
            Paciente novoPaciente = new Paciente(nome, cpf);

            if(pacienteService.salvar(novoPaciente)) {
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

    private void removerPaciente() {
        // Obtenha o paciente selecionado na tabela
        int linhaSelecionada = tabelaPacientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o paciente?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                // Obtenha o objeto Paciente da linha selecionada
                Paciente pacienteParaRemover = obterPacienteDaTabela(linhaSelecionada);

                if (pacienteService.remover(pacienteParaRemover)) {
                    JOptionPane.showMessageDialog(this, "Paciente removido com sucesso!");
                    // recarregarTabela(); // Lógica para atualizar a tabela
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Erro ao remover o paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void atualizarPaciente() {
        int linhaSelecionada = tabelaPacientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Paciente pacienteParaAtualizar = obterPacienteDaTabela(linhaSelecionada);

            pacienteParaAtualizar.setNome(campoNome.getText());
            pacienteParaAtualizar.setCpf(campoCpf.getText());

            if (pacienteService.atualizar(pacienteParaAtualizar)) {
                JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");


        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar o paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
    }


    private Paciente obterPacienteDaTabela(int linhaSelecionada) {
        return null;
    }
}


