package Service;


import Dao.GenericDao;
import Model.Remedio;
import java.io.IOException;
import java.util.Set;

public class RemedioService {

    private GenericDao<Remedio> remedioDao;

    public RemedioService() throws IOException {
        remedioDao = new GenericDao<>("dados/remedios.dat");
    }

    public boolean salvar(Remedio remedio) throws IOException,
            ClassNotFoundException {
        return remedioDao.salvar(remedio);
    }

    public boolean remover(Remedio remedio) throws IOException, ClassNotFoundException {
        return remedioDao.remover(remedio);
    }

    public boolean atualizar(Remedio remedio) throws IOException, ClassNotFoundException {
        return remedioDao.atualizar(remedio);
    }

    public Set<Remedio> getAll() throws IOException, ClassNotFoundException {
        return remedioDao.getAll();
    }
}