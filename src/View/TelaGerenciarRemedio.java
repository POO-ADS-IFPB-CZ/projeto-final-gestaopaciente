package View;

import Model.Remedio;
import Service.RemedioService;
import exceptions.RemedioExisteException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TelaGerenciarRemedio extends JFrame {

    private JPanel contentPane;
    private JTextField campoNome;
    private JTextField campoDosagem;
    private JButton salvarButton;
    private JButton removerButton;
    private JButton atualizarButton;
    private JButton voltarButton;

    private RemedioService remedioService;

    public TelaGerenciarRemedio() {
        setTitle("Gerenciar Remédios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new GridLayout(4, 2, 10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        campoNome = new JTextField();
        campoDosagem = new JTextField();
        salvarButton = new JButton("Salvar");
        removerButton = new JButton("Remover");
        atualizarButton = new JButton("Atualizar");
        voltarButton = new JButton("Voltar para Tela Principal");

        contentPane.add(new JLabel("Nome:"));
        contentPane.add(campoNome);
        contentPane.add(new JLabel("Dosagem:"));
        contentPane.add(campoDosagem);
        contentPane.add(salvarButton);
        contentPane.add(removerButton);
        contentPane.add(atualizarButton);
        contentPane.add(voltarButton);

        try {
            remedioService = new RemedioService();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Falha ao conectar com o arquivo de dados dos remédios.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }

        salvarButton.addActionListener(e -> salvarRemedio());
        removerButton.addActionListener(e -> removerRemedio());
        atualizarButton.addActionListener(e -> atualizarRemedio());

        voltarButton.addActionListener(e -> {
            this.dispose();
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
        });
    }

    private void salvarRemedio() {
        try {
            String nome = campoNome.getText();
            String dosagem = campoDosagem.getText();
            Remedio novoRemedio = new Remedio(nome, dosagem);

            if (remedioService.salvar(novoRemedio)) {
                JOptionPane.showMessageDialog(this, "Remédio salvo com sucesso!");
                limparCampos();
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        } catch (RemedioExisteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerRemedio() {
        String nome = campoNome.getText();
        String dosagem = campoDosagem.getText();

        if (nome.isEmpty() || dosagem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o Nome e a Dosagem para remover o remédio.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Remedio remedioParaRemover = new Remedio(nome, dosagem);

            if (remedioService.remover(remedioParaRemover)) {
                JOptionPane.showMessageDialog(this, "Remédio removido com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Remédio não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarRemedio() {
        String nomeParaAtualizar = JOptionPane.showInputDialog(this, "Digite o NOME do remédio que deseja atualizar:");
        if (nomeParaAtualizar == null || nomeParaAtualizar.trim().isEmpty()) {
            return;
        }

        String novoNome = campoNome.getText();
        String novaDosagem = campoDosagem.getText();

        if (novoNome.isEmpty() || novaDosagem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o Nome e a Dosagem com os NOVOS dados.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Remedio remedioAntigo = new Remedio(nomeParaAtualizar, "");
            Remedio remedioAtualizado = new Remedio(novoNome, novaDosagem);

            if (remedioService.remover(remedioAntigo)) {
                if (remedioService.salvar(remedioAtualizado)) {
                    JOptionPane.showMessageDialog(this, "Remédio atualizado com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Remédio antigo removido, mas falha ao salvar o novo. Tente salvar novamente.", "Erro Grave", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Remédio com nome '" + nomeParaAtualizar + "' não encontrado para atualização.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        } catch (RemedioExisteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoDosagem.setText("");
    }
}