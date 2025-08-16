package Model;

import java.io.Serializable;
import java.util.Objects;

public class DescricaoDoenca implements Serializable {
    private String nome;
    private String sintomas;

    public DescricaoDoenca(String nome, String sintomas) {
        this.nome = nome;
        this.sintomas = sintomas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DescricaoDoenca)) return false;
        DescricaoDoenca that = (DescricaoDoenca) o;
        return Objects.equals(nome, that.nome) && Objects.equals(sintomas, that.sintomas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, sintomas);
    }

    @Override
    public String toString() {
        return "Model.DescricaoDoenca{" +
                "nome='" + nome + '\'' +
                ", sintomas='" + sintomas + '\'' +
                '}';
    }
}

