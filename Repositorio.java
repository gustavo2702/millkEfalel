import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Repositorio<T> {
    
    private List<T> elementos;

    public Repositorio() {
        this.elementos = new ArrayList<>();
    }

    public void adicionar(T elemento) {
        elementos.add(elemento);
    }

    public void remover(T elemento) {
        elementos.remove(elemento);
    }

    public List<T> listar() {
        return new ArrayList<>(elementos);
    }

    public List<T> buscar(Predicate<T> condicao) {
        List<T> resultados = new ArrayList<>();
        for (T elemento : elementos) {
            if (condicao.test(elemento)) {
                resultados.add(elemento);
            }
        }
        return resultados;
    }
}