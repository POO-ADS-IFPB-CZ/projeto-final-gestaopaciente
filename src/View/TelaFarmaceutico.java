package View;

import Service.FarmaceuticoService;
import Model.Farmaceutico;
import exceptions.FarmaceuticoExisteException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TelaFarmaceutico extends JFrame {

        private JPanel contentPane;
        private JTextField campoNome;
        private JTextField campoCRF;
        private JButton salvarButton;
        private JButton removerButton;
        private JButton atualizarButton;
        private JButton voltarButton; // Botão de Voltar

        private FarmaceuticoService farmaceuticoService;

        public TelaFarmaceutico() {
                setTitle("Gerenciar Farmacêuticos");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setSize(400, 300); // Tamanho reduzido sem a tabela
                setLocationRelativeTo(null);

                contentPane = new JPanel(new GridLayout(5, 2, 10, 10));
                contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                setContentPane(contentPane);

                campoNome = new JTextField();
                campoCRF = new JTextField();
                salvarButton = new JButton("Salvar");
                removerButton = new JButton("Remover");
                atualizarButton = new JButton("Atualizar");
                voltarButton = new JButton("Voltar para Tela Principal");

                contentPane.add(new JLabel("Nome:"));
                contentPane.add(campoNome);
                contentPane.add(new JLabel("CRF:"));
                contentPane.add(campoCRF);
                contentPane.add(salvarButton);
                contentPane.add(removerButton);
                contentPane.add(atualizarButton);
                contentPane.add(voltarButton);

                try {
                        farmaceuticoService = new FarmaceuticoService();
                } catch (IOException e) {
                        JOptionPane.showMessageDialog(this,
                                "Falha ao conectar com o arquivo de dados dos farmacêuticos.",
                                "Erro de Conexão",
                                JOptionPane.ERROR_MESSAGE);
                }

                // Adiciona Ações
                salvarButton.addActionListener(e -> salvarFarmaceutico());
                removerButton.addActionListener(e -> removerFarmaceutico());
                atualizarButton.addActionListener(e -> atualizarFarmaceutico());

                // Ação do Botão Voltar
                voltarButton.addActionListener(e -> {
                        this.dispose();
                        TelaPrincipal telaPrincipal = new TelaPrincipal();
                        telaPrincipal.setVisible(true);
                });


        }
        private void salvarFarmaceutico() {
                try {
                        String nome = campoNome.getText();
                        String crf = campoCRF.getText();

                        Farmaceutico novoFarmaceutico = new Farmaceutico(nome, crf);

                        if(farmaceuticoService.salvar(novoFarmaceutico)) {
                                JOptionPane.showMessageDialog(this, "Farmacêutico salvo com sucesso!");
                                limparCampos();
                        }

                } catch (IOException | ClassNotFoundException e) {
                        JOptionPane.showMessageDialog(this,
                                "Falha ao manipular o arquivo de dados.",
                                "Erro de Arquivo",
                                JOptionPane.ERROR_MESSAGE);
                } catch (FarmaceuticoExisteException e) {
                        JOptionPane.showMessageDialog(this,
                                e.getMessage(),
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                }
        }
        private void removerFarmaceutico() {
                String crf = campoCRF.getText();
                if (crf.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Digite o CRF do farmacêutico para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                }

                try {
                        Farmaceutico farmaceuticoParaRemover = new Farmaceutico("", crf);
                        if (farmaceuticoService.remover(farmaceuticoParaRemover)) {
                                JOptionPane.showMessageDialog(this, "Farmacêutico removido com sucesso!");
                                limparCampos();
                        } else {
                                JOptionPane.showMessageDialog(this, "Farmacêutico não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                } catch (IOException | ClassNotFoundException e) {
                        JOptionPane.showMessageDialog(this,
                                "Falha ao manipular o arquivo de dados.",
                                "Erro de Arquivo",
                                JOptionPane.ERROR_MESSAGE);
                }
        }
        private void atualizarFarmaceutico() {
                String crf = campoCRF.getText();
                String nome = campoNome.getText();

                if (crf.isEmpty() || nome.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Para atualizar, preencha o CRF e o novo Nome.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                }
                try {
                        Farmaceutico farmaceuticoParaAtualizar = new Farmaceutico(nome, crf);
                        if (farmaceuticoService.atualizar(farmaceuticoParaAtualizar)) {
                                JOptionPane.showMessageDialog(this, "Farmacêutico atualizado com sucesso!");
                                limparCampos();
                        } else {
                                JOptionPane.showMessageDialog(this, "Farmacêutico com o CRF " + crf + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                } catch (IOException | ClassNotFoundException e) {
                        JOptionPane.showMessageDialog(this,
                                "Falha ao manipular o arquivo de dados.",
                                "Erro de Arquivo",
                                JOptionPane.ERROR_MESSAGE);
                }
        }
        private void limparCampos() {
                campoNome.setText("");
                campoCRF.setText("");
        }
        public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                        TelaFarmaceutico tela = new TelaFarmaceutico();
                        tela.setVisible(true);
                });
        }

}
