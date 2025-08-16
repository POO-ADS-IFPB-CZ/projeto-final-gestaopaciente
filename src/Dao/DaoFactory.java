package Dao;

import Model.DescricaoDoenca;
import Model.Farmaceutico;
import Model.Paciente;
import Model.Remedio;

public class DaoFactory {

    public static GenericDao<Paciente> getPaciente() throws Exception {
        return new GenericDao<>("dados/pacientes.dat");
    }

    public static GenericDao<Remedio> getRemedio() throws Exception {
        return new GenericDao<>("dados/remedios.dat");
    }

    public static GenericDao<Farmaceutico> getFarmaceutico() throws Exception {
        return new GenericDao<>("dados/farmaceuticos.dat");
    }

    public static GenericDao<DescricaoDoenca> getDoenca() throws Exception {
        return new GenericDao<>("dados/descricaoDoenca.dat");
    }
}
