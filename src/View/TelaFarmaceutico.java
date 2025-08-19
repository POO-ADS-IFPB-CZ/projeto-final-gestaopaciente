package View;

import Service.FarmaceuticoService;
import Model.Farmaceutico;
import exceptions.FarmaceuticoExisteException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TelaFarmaceutico extends JFrame {

        // Componentes da interface
        private JPanel contentPane;
        private JTextField campoNome;
        private JTextField campoCRF;
        private JButton salvarButton;
        private JButton removerButton;
        private JButton PacienteButton;
        private JButton listarButton;
        private JButton atualizarButton;
        private JTable tabelaFarmaceuticos;
        private JScrollPane scrollPane;

        private FarmaceuticoService farmaceuticoService;

        public TelaFarmaceutico() {

                setTitle("Gerenciar Farmacêuticos");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setSize(800, 600);
                setLocationRelativeTo(null);


                try {
                        farmaceuticoService = new FarmaceuticoService();
                } catch (IOException e) {
                        JOptionPane.showMessageDialog(this,
                                "Falha ao conectar com o arquivo de dados dos farmacêuticos.",
                                "Erro de Conexão",
                                JOptionPane.ERROR_MESSAGE);
                }

                salvarButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                salvarFarmaceutico();
                        }
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