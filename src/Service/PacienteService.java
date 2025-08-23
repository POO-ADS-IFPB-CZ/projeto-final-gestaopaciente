package Service;

import Dao.GenericDao;
import exceptions.PacienteExisteException;
import Model.Paciente;
import java.io.IOException;
import java.util.Set;


public class PacienteService {

    private GenericDao<Paciente> pacienteDao;

    public PacienteService() throws IOException {
        pacienteDao = new GenericDao<>("dados/pacientes.dat");
    }

    public boolean salvar(Paciente paciente) throws IOException,
            ClassNotFoundException, PacienteExisteException {
        Set<Paciente> pacientes = pacienteDao.getAll();
        if (pacientes.contains(paciente)) {
            throw new PacienteExisteException(
                    "Já existe um paciente com este CPF.");
        }

        return pacienteDao.salvar(paciente);
    }

    public boolean remover(Paciente paciente) throws IOException, ClassNotFoundException {
        return pacienteDao.remover(paciente);
    }

    public boolean atualizar(Paciente paciente) throws IOException, ClassNotFoundException {
        return pacienteDao.atualizar(paciente);
    }

    public Set<Paciente> getAll() throws IOException, ClassNotFoundException {
        return pacienteDao.getAll();
    }
}