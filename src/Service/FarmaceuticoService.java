package Service;

import java.io.IOException;
import java.util.Set;


import Dao.GenericDao;
import exceptions.FarmaceuticoExisteException;
import Model.Farmaceutico;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FarmaceuticoService {

    private final GenericDao<Farmaceutico> farmaceuticoDao;

    public FarmaceuticoService() throws IOException {
        farmaceuticoDao = new GenericDao<>("dados/farmaceuticos.dat");
    }

    public boolean salvar(Farmaceutico farmaceutico) throws IOException,
            ClassNotFoundException, FarmaceuticoExisteException {

        Set<Farmaceutico> farmaceuticos = farmaceuticoDao.getAll();

        if (farmaceuticos.contains(farmaceutico)) {
            throw new FarmaceuticoExisteException(
                    "Já existe um farmacêutico com este registro (CRF).");
        }

        if (!validarCRF(farmaceutico.getcrf())) {
            throw new FarmaceuticoExisteException("O CRF fornecido é inválido.");
        }

        return farmaceuticoDao.salvar(farmaceutico);
    }

    public Set<Farmaceutico> getAll() throws IOException, ClassNotFoundException {
        return farmaceuticoDao.getAll();
    }

    public boolean remover(Farmaceutico farmaceutico) throws IOException, ClassNotFoundException {
        return farmaceuticoDao.remover(farmaceutico);
    }

    public boolean atualizar(Farmaceutico farmaceutico) throws IOException, ClassNotFoundException {
        return farmaceuticoDao.atualizar(farmaceutico);
    }

    private boolean validarCRF(String crf) {
        Pattern pattern = Pattern.compile("^\\d{3}-\\d{4}$");
        Matcher matcher = pattern.matcher(crf);
        return matcher.matches();
    }
}