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
        private JButton atualizarButton;
        private JTable tabelaFarmaceuticos;
        private JScrollPane scrollPane;

        // Camada de serviço (Controller)
        private FarmaceuticoService farmaceuticoService;

        public TelaFarmaceutico() {
                // Inicializa a janela
                setTitle("Gerenciar Farmacêuticos");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setSize(800, 600);
                setLocationRelativeTo(null);

                // Inicializa a camada de serviço e trata o erro de conexão com o arquivo
                try {
                        farmaceuticoService = new FarmaceuticoService();
                } catch (IOException e) {
                        JOptionPane.showMessageDialog(this,
                                "Falha ao conectar com o arquivo de dados dos farmacêuticos.",
                                "Erro de Conexão",
                                JOptionPane.ERROR_MESSAGE);
                }

                // Adiciona a lógica aos botões
                salvarButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                salvarFarmaceutico();
                        }
                });

                // TODO: Adicionar lógica para os botões 'removerButton' e 'atualizarButton'
                // TODO: Adicionar lógica para popular a 'tabelaFarmaceuticos'
        }

        private void salvarFarmaceutico() {
                try {
                        // Pega os dados dos campos da tela
                        String nome = campoNome.getText();
                        String crf = campoCRF.getText();

                        // Cria um novo objeto Farmaceutico
                        Farmaceutico novoFarmaceutico = new Farmaceutico(nome, crf);

                        // Chama o método salvar do Service
                        if(farmaceuticoService.salvar(novoFarmaceutico)) {
                                JOptionPane.showMessageDialog(this, "Farmacêutico salvo com sucesso!");
                                // Opcional: Limpar os campos após salvar e recarregar a tabela
                                limparCampos();
                                // recarregarTabela();
                        }

                } catch (IOException | ClassNotFoundException e) {
                        JOptionPane.showMessageDialog(this,
                                "Falha ao manipular o arquivo de dados.",
                                "Erro de Arquivo",
                                JOptionPane.ERROR_MESSAGE);
                } catch (FarmaceuticoExisteException e) {
                        // Lida com a exceção específica
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

        // Método main para testar a tela individualmente
        public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                        TelaFarmaceutico tela = new TelaFarmaceutico();
                        tela.setVisible(true);
                });
        }
}