package View;

import Model.DescricaoDoenca;
import Service.DescricaoDoencaService;
import javax.swing.*;
        import java.awt.*;
        import java.io.IOException;

public class TelaGerenciarDescricaoDoenca extends JFrame {

    private JPanel contentPane;
    private JTextField campoNome;
    private JTextField campoSintomas;
    private JButton salvarButton;
    private JButton removerButton;
    private JButton atualizarButton;
    private JButton voltarButton;

    private DescricaoDoencaService doencaService;

    public TelaGerenciarDescricaoDoenca() {
        setTitle("Gerenciar Descrições de Doença");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300); // Tamanho ajustado
        setLocationRelativeTo(null);

        contentPane = new JPanel(new GridLayout(4, 2, 10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        campoNome = new JTextField();
        campoSintomas = new JTextField();
        salvarButton = new JButton("Salvar");
        removerButton = new JButton("Remover");
        atualizarButton = new JButton("Atualizar");
        voltarButton = new JButton("Voltar para Tela Principal"); // Adicionando o botão Voltar

        contentPane.add(new JLabel("Nome:"));
        contentPane.add(campoNome);
        contentPane.add(new JLabel("Sintomas:"));
        contentPane.add(campoSintomas);
        contentPane.add(salvarButton);
        contentPane.add(removerButton);
        contentPane.add(atualizarButton);
        contentPane.add(voltarButton);

        try {
            doencaService = new DescricaoDoencaService();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao conectar com o arquivo de dados das doenças.",
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE);
        }

        salvarButton.addActionListener(e -> salvarDoenca());
        removerButton.addActionListener(e -> removerDoenca());
        atualizarButton.addActionListener(e -> atualizarDoenca());

        voltarButton.addActionListener(e -> {
            this.dispose();
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
        });
    }

    private void salvarDoenca() {
        try {
            String nome = campoNome.getText();
            String sintomas = campoSintomas.getText();
            DescricaoDoenca novaDoenca = new DescricaoDoenca(nome, sintomas);

            if (doencaService.salvar(novaDoenca)) {
                JOptionPane.showMessageDialog(this, "Descrição de Doença salva com sucesso!");
                limparCampos();
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao manipular o arquivo de dados.",
                    "Erro de Arquivo",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void removerDoenca() {
        String nome = campoNome.getText();
        String sintomas = campoSintomas.getText();

        if (nome.isEmpty() || sintomas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o Nome e os Sintomas para remover a descrição da doença.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            DescricaoDoenca doencaParaRemover = new DescricaoDoenca(nome, sintomas);

            if (doencaService.remover(doencaParaRemover)) {
                JOptionPane.showMessageDialog(this, "Descrição de Doença removida com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Descrição de Doença não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha ao manipular o arquivo de dados.",
                    "Erro de Arquivo",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarDoenca() {
        String nomeParaAtualizar = JOptionPane.showInputDialog(this, "Digite o NOME da doença que deseja atualizar:");
        if (nomeParaAtualizar == null || nomeParaAtualizar.trim().isEmpty()) {
            return;
        }
        String novoNome = campoNome.getText();
        String novosSintomas = campoSintomas.getText();

        if (novoNome.isEmpty() || novosSintomas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o Nome e os Sintomas com os NOVOS dados.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            DescricaoDoenca doencaAntiga = new DescricaoDoenca(nomeParaAtualizar, "");

            DescricaoDoenca doencaAtualizada = new DescricaoDoenca(novoNome, novosSintomas);
            if (doencaService.remover(doencaAntiga)) {
                if (doencaService.salvar(doencaAtualizada)) {
                    JOptionPane.showMessageDialog(this, "Descrição de Doença atualizada com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Doença antiga removida, mas falha ao salvar a nova. Tente salvar novamente.", "Erro Grave", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Doença com nome '" + nomeParaAtualizar + "' não encontrada para atualização.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Falha ao manipular o arquivo de dados.", "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoSintomas.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaGerenciarDescricaoDoenca tela = new TelaGerenciarDescricaoDoenca();
            tela.setVisible(true);
        });
    }
}