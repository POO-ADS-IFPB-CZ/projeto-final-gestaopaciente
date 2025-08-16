package Model;

import java.io.Serializable;
import java.util.Objects;

public class Remedio implements Serializable {
    private String nome;
    private String dosagem;

    public Remedio(String nome, String dosagem) {
        this.nome = nome;
        this.dosagem = dosagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Remedio)) return false;
        Remedio remedio = (Remedio) o;
        return Objects.equals(nome, remedio.nome) && Objects.equals(dosagem, remedio.dosagem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dosagem);
    }

    @Override
    public String toString() {
        return "Model.Remedio{" +
                "nome='" + nome + '\'' +
                ", dosagem='" + dosagem + '\'' +
                '}';
    }
}
