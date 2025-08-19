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
    private String farmaceutico;
    private String doenca;
    private String remedio;

    public Paciente(String nome, String cpf, int idade, String endereco,int altura, Farmaceutico farmaceutico, DescricaoDoenca doenca, Remedio remedio) {
        this.nome = nome;
        this.idade = idade;
        this.altura = altura;
        this.endereco = endereco;
        this.farmaceutico = farmaceutico;
        this.doenca = doenca;
        this.remedio = remedio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFarmaceutico() {
        return farmaceutico;
    }

    public void setFarmaceutico(String farmaceutico) {
        this.farmaceutico = farmaceutico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paciente)) return false;
        Paciente paciente = (Paciente) o;
        return idade == paciente.idade && Objects.equals(nome, paciente.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, idade);
    }

    @Override
    public String toString() {
        return "Model.Paciente{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                '}';
    }
}

