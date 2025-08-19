package Service;

import Dao.GenericDao;
import Model.DescricaoDoenca;

import java.io.IOException;
import java.util.Set;

public class DescricaoDoencaService {

    private GenericDao<DescricaoDoenca> doencaDao;

    public DescricaoDoencaService() throws IOException {
        doencaDao = new GenericDao<>("dados/descricaodoenca.dat");
    }

    public boolean salvar(DescricaoDoenca doenca) throws IOException,
            ClassNotFoundException {
        return doencaDao.salvar(doenca);
    }

    public boolean remover(DescricaoDoenca doenca) throws IOException, ClassNotFoundException {
        return doencaDao.remover(doenca);
    }


    public boolean atualizar(DescricaoDoenca doenca) throws IOException, ClassNotFoundException {
        return doencaDao.atualizar(doenca);
    }

    public Set<DescricaoDoenca> getAll() throws IOException, ClassNotFoundException {
        return doencaDao.getAll();
    }
}
