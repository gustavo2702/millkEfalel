import java.time.LocalDateTime;
import java.util.Objects;

public class Consulta {
    private int codigo;
    private String nomePaciente;
    private Especialidade especialidade;
    private LocalDateTime dataConsulta;
    private double valorConsulta;

    public Consulta(int codigo, String nomePaciente, Especialidade especialidade, LocalDateTime dataConsulta, double valorConsulta) {
        this.codigo = codigo;
        this.nomePaciente = nomePaciente;
        this.especialidade = especialidade;
        this.dataConsulta = dataConsulta;
        this.valorConsulta = valorConsulta;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return codigo == consulta.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}