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

    public Paciente(String nome, String cpf, int idade, String endereco,
                    Farmaceutico farmaceutico, DescricaoDoenca doenca, Remedio remedio) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
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

    public Farmaceutico getFarmaceutico() { // Tipo de retorno corrigido
        return farmaceutico;
    }

    public void setFarmaceutico(Farmaceutico farmaceutico) { // Tipo corrigido
        this.farmaceutico = farmaceutico;
    }

    public DescricaoDoenca getDoenca() {
        return doenca;
    }

    public void setDoenca(DescricaoDoenca doenca) {
        this.doenca = doenca;
    }

    public Remedio getRemedio() {
        return remedio;
    }

    public void setRemedio(Remedio remedio) {
        this.remedio = remedio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(cpf, paciente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", endereco='" + endereco + '\'' +
                ", cpf='" + cpf + '\'' +
                ", farmaceutico=" + (farmaceutico != null ? farmaceutico.getNome() : "N/A") +
                ", doenca=" + (doenca != null ? doenca.getNome() : "N/A") +
                ", remedio=" + (remedio != null ? remedio.getNome() : "N/A") +
                '}';
    }
}

