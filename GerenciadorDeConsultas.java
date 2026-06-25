import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class GerenciadorDeConsultas {

    private Repositorio<Consulta> repositorio;

    public GerenciadorDeConsultas() {
        this.repositorio = new Repositorio<>();
    }

    public void cadastrarConsulta(Consulta consulta) throws HorarioIndisponivelException {
        for (Consulta c : repositorio.listar()) {
            if (c.getDataConsulta().equals(consulta.getDataConsulta())) {
                throw new HorarioIndisponivelException("Horário indisponível.");
            }
        }
        repositorio.adicionar(consulta);
    }

    public void removerConsulta(int codigo) throws ConsultaNaoEncontradaException {
        Consulta consulta = buscarPorCodigo(codigo);
        repositorio.remover(consulta);
    }

    public void cancelarConsulta(int codigo) throws ConsultaNaoEncontradaException {
        removerConsulta(codigo);
    }

    public List<Consulta> filtrarPorEspecialidade(Predicate<Consulta> filtro) {
        return repositorio.buscar(filtro);
    }

    public List<Consulta> filtrarPorValor(Predicate<Consulta> filtro) {
        return repositorio.buscar(filtro);
    }

    public void reagendarConsulta(int codigo, LocalDateTime novaData) throws ConsultaNaoEncontradaException, HorarioIndisponivelException {
        Consulta consulta = buscarPorCodigo(codigo);
        for (Consulta c : repositorio.listar()) {
            if (c.getCodigo() != codigo && c.getDataConsulta().equals(novaData)) {
                throw new HorarioIndisponivelException("Horário indisponível.");
            }
        }
        consulta.setDataConsulta(novaData);
    }

    public List<Consulta> ordenarConsultas(Comparator<Consulta> comparador) {
        List<Consulta> listaOrdenada = repositorio.listar();
        Collections.sort(listaOrdenada, comparador);
        return listaOrdenada;
    }

    public List<String> gerarRelatorio(Function<Consulta, String> formatador) {
        List<String> relatorio = new ArrayList<>();
        for (Consulta c : repositorio.listar()) {
            relatorio.add(formatador.apply(c));
        }
        return relatorio;
    }

    public void exibirConsultas(Consumer<Consulta> exibidor) {
        for (Consulta c : repositorio.listar()) {
            exibidor.accept(c);
        }
    }

    private Consulta buscarPorCodigo(int codigo) throws ConsultaNaoEncontradaException {
        for (Consulta c : repositorio.listar()) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        throw new ConsultaNaoEncontradaException("Consulta não encontrada.");
    }
}