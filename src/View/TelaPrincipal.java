package View;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private JPanel contentPane;
    private JButton gerenciarFarmaceuticosButton;
    private JButton gerenciarPacientesButton;
    private JButton visualizarPacientesButton;
    private JButton gerenciarRemediosButton;
    private JButton gerenciarDoencasButton;
    private JButton cadastroFarmacêuticoButton;
    private JButton remédioButton;
    private JButton telaPrincipalButton;
    private JButton listarButton;

    public TelaPrincipal() {
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(5, 1, 10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        gerenciarFarmaceuticosButton = new JButton("Gerenciar Farmacêuticos");
        gerenciarPacientesButton = new JButton("Cadastrar Paciente");
        visualizarPacientesButton = new JButton("Visualizar Pacientes");
        gerenciarRemediosButton = new JButton("Gerenciar Remédios");
        gerenciarDoencasButton = new JButton("Gerenciar Descrições de Doença");

        contentPane.add(gerenciarFarmaceuticosButton);
        contentPane.add(gerenciarPacientesButton);
        contentPane.add(visualizarPacientesButton);
        contentPane.add(gerenciarRemediosButton);
        contentPane.add(gerenciarDoencasButton);

        gerenciarFarmaceuticosButton.addActionListener(e -> {
            TelaFarmaceutico telaFarmaceutico = new TelaFarmaceutico();
            telaFarmaceutico.setVisible(true);
        });

        gerenciarPacientesButton.addActionListener(e -> {
            TelaCadastroPaciente telaCadastroPaciente = new TelaCadastroPaciente();
            telaCadastroPaciente.setVisible(true);
        });

        visualizarPacientesButton.addActionListener(e -> {
            TelaVisualizacaoPaciente telaVisualizacaoPaciente = new TelaVisualizacaoPaciente();
            telaVisualizacaoPaciente.setVisible(true);
        });

        gerenciarRemediosButton.addActionListener(e -> {
            TelaGerenciarRemedio telaGerenciarRemedio = new TelaGerenciarRemedio();
            telaGerenciarRemedio.setVisible(true);
        });

        gerenciarDoencasButton.addActionListener(e -> {
            TelaGerenciarDescricaoDoenca telaGerenciarDoenca = new TelaGerenciarDescricaoDoenca();
            telaGerenciarDoenca.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
        });
    }
}