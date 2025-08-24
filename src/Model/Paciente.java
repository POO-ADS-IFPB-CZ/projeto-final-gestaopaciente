package Model;

import java.io.Serializable;
import java.util.Objects;
import java.lang.*;

public class Paciente implements Serializable {
    private String nome;
    private int idade;
    private int altura;
    private String endereco;
    private String cpf;
    private Farmaceutico farmaceutico;
    private DescricaoDoenca doenca;
    private Remedio remedio;

    public Paciente(String nome, String cpf, int idade, String endereco, int altura,
                    Farmaceutico farmaceutico, DescricaoDoenca doenca, Remedio remedio) {
        this.nome = nome;
        this.idade = idade;
        this.altura = altura;
        this.endereco = endereco;
        this.cpf = cpf;
        this.farmaceutico = farmaceutico;
        this.doenca = doenca;
        this.remedio = remedio;
    }

    public Paciente(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public int getAltura() { return altura; }
    public void setAltura(int altura) { this.altura = altura; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public Farmaceutico getFarmaceutico() { return farmaceutico; }
    public void setFarmaceutico(Farmaceutico farmaceutico) { this.farmaceutico = farmaceutico; }

    public DescricaoDoenca getDoenca() { return doenca; }
    public void setDoenca(DescricaoDoenca doenca) { this.doenca = doenca; }

    public Remedio getRemedio() { return remedio; }
    public void setRemedio(Remedio remedio) { this.remedio = remedio; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paciente)) return false;
        Paciente paciente = (Paciente) o;
        // O equals do Paciente Service se baseia apenas no CPF, então corrigimos o equals aqui.
        return Objects.equals(cpf, paciente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}

