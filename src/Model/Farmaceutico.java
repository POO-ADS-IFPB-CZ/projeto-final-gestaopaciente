package Model;

import java.io.Serializable;
import java.util.Objects;

public class Farmaceutico implements Serializable {
    private String nome;
    private String crf;

    public Farmaceutico(String nome, String crf) {
        this.nome = nome;
        this.crf = crf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getcrf() {
        return crf;
    }

    public void setRegistro(String crf) {
        this.crf = crf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Farmaceutico)) return false;
        Farmaceutico that = (Farmaceutico) o;
        return Objects.equals(nome, that.nome) && Objects.equals(crf, that.crf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, crf);
    }

    @Override
    public String toString() {
        return "Model.Farmaceutico{" +
                "nome='" + nome + '\'' +
                ", registro='" + crf + '\'' +
                '}';
    }
}
