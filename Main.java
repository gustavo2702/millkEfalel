import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GerenciadorDeConsultas gerenciador = new GerenciadorDeConsultas();
        inicializarDadosSemente(gerenciador);

        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU CLÍNICA MÉDICA ---");
            System.out.println("1. Cadastrar consulta");
            System.out.println("2. Remover consulta");
            System.out.println("3. Filtrar consultas por especialidade");
            System.out.println("4. Filtrar consultas com valor acima de um valor informado");
            System.out.println("5. Reagendar uma consulta");
            System.out.println("6. Ordenar consultas");
            System.out.println("7. Gerar relatório resumido das consultas");
            System.out.println("8. Cancelar uma consulta");
            System.out.println("9. Exibir todas as consultas cadastradas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.print("Código: ");
                        int codigo = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nome do Paciente: ");
                        String nome = scanner.nextLine();
                        System.out.print("Especialidade (CARDIOLOGIA, PEDIATRIA, ORTOPEDIA, DERMATOLOGIA): ");
                        Especialidade especialidade = Especialidade.valueOf(scanner.nextLine().toUpperCase());
                        System.out.print("Data e Hora (dd/MM/yyyy HH:mm): ");
                        LocalDateTime data = LocalDateTime.parse(scanner.nextLine(), formatter);
                        System.out.print("Valor: ");
                        double valor = Double.parseDouble(scanner.nextLine());

                        Consulta novaConsulta = new Consulta(codigo, nome, especialidade, data, valor);
                        gerenciador.cadastrarConsulta(novaConsulta);
                        System.out.println("Consulta cadastrada com sucesso!");
                        break;

                    case 2:
                        System.out.print("Código da consulta a remover: ");
                        int codRemover = Integer.parseInt(scanner.nextLine());
                        gerenciador.removerConsulta(codRemover);
                        System.out.println("Consulta removida com sucesso!");
                        break;

                    case 3:
                        System.out.print("Especialidade para filtrar: ");
                        Especialidade espFiltro = Especialidade.valueOf(scanner.nextLine().toUpperCase());
                        List<Consulta> filtradasEspecialidade = gerenciador.filtrarPorEspecialidade(c -> c.getEspecialidade() == espFiltro);
                        filtradasEspecialidade.forEach(c -> System.out.println(c.getCodigo() + " - " + c.getNomePaciente()));
                        break;

                    case 4:
                        System.out.print("Valor mínimo: ");
                        double valorMin = Double.parseDouble(scanner.nextLine());
                        List<Consulta> filtradasValor = gerenciador.filtrarPorValor(c -> c.getValorConsulta() > valorMin);
                        filtradasValor.forEach(c -> System.out.println(c.getCodigo() + " - " + c.getNomePaciente() + " - R$" + c.getValorConsulta()));
                        break;

                    case 5:
                        System.out.print("Código da consulta a reagendar: ");
                        int codReagendar = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nova Data e Hora (dd/MM/yyyy HH:mm): ");
                        LocalDateTime novaData = LocalDateTime.parse(scanner.nextLine(), formatter);
                        gerenciador.reagendarConsulta(codReagendar, novaData);
                        System.out.println("Consulta reagendada com sucesso!");
                        break;

                    case 6:
                        System.out.println("1. Ordenar por data");
                        System.out.println("2. Ordenar por nome do paciente");
                        System.out.print("Escolha a ordenação: ");
                        int opOrdenacao = Integer.parseInt(scanner.nextLine());
                        
                        if (opOrdenacao == 1) {
                            List<Consulta> ordenadasData = gerenciador.ordenarConsultas((c1, c2) -> c1.getDataConsulta().compareTo(c2.getDataConsulta()));
                            ordenadasData.forEach(c -> System.out.println(c.getDataConsulta().format(formatter) + " - " + c.getNomePaciente()));
                        } else if (opOrdenacao == 2) {
                            List<Consulta> ordenadasNome = gerenciador.ordenarConsultas((c1, c2) -> c1.getNomePaciente().compareToIgnoreCase(c2.getNomePaciente()));
                            ordenadasNome.forEach(c -> System.out.println(c.getNomePaciente() + " - " + c.getDataConsulta().format(formatter)));
                        } else {
                            System.out.println("Opção de ordenação inválida.");
                        }
                        break;

                    case 7:
                        List<String> relatorio = gerenciador.gerarRelatorio(c -> {
                            String especialidadeFormatada = c.getEspecialidade().name().substring(0, 1).toUpperCase() + 
                                                            c.getEspecialidade().name().substring(1).toLowerCase();
                            return c.getNomePaciente() + " - " + especialidadeFormatada + " - " + c.getDataConsulta().format(formatter);
                        });
                        relatorio.forEach(System.out::println);
                        break;

                    case 8:
                        System.out.print("Código da consulta a cancelar: ");
                        int codCancelar = Integer.parseInt(scanner.nextLine());
                        gerenciador.cancelarConsulta(codCancelar);
                        System.out.println("Consulta cancelada com sucesso!");
                        break;

                    case 9:
                        gerenciador.exibirConsultas(c -> System.out.println(
                            "Código: " + c.getCodigo() + " | Paciente: " + c.getNomePaciente() + 
                            " | Especialidade: " + c.getEspecialidade() + " | Data: " + 
                            c.getDataConsulta().format(formatter) + " | Valor: R$" + c.getValorConsulta()
                        ));
                        break;

                    case 0:
                        System.out.println("Encerrando o sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Digite os dados corretamente.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: Especialidade inválida.");
            } catch (DateTimeParseException e) {
                System.out.println("Erro: Formato de data inválido. Use dd/MM/yyyy HH:mm");
            } catch (ConsultaNaoEncontradaException | HorarioIndisponivelException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void inicializarDadosSemente(GerenciadorDeConsultas gerenciador) {
        try {
            gerenciador.cadastrarConsulta(new Consulta(1, "João Silva", Especialidade.CARDIOLOGIA, LocalDateTime.of(2026, 6, 15, 14, 0), 250.0));
            gerenciador.cadastrarConsulta(new Consulta(2, "Maria Oliveira", Especialidade.PEDIATRIA, LocalDateTime.of(2026, 6, 15, 15, 0), 200.0));
            gerenciador.cadastrarConsulta(new Consulta(3, "Carlos Souza", Especialidade.ORTOPEDIA, LocalDateTime.of(2026, 6, 16, 9, 30), 300.0));
            gerenciador.cadastrarConsulta(new Consulta(4, "Ana Costa", Especialidade.DERMATOLOGIA, LocalDateTime.of(2026, 6, 16, 10, 30), 280.0));
            gerenciador.cadastrarConsulta(new Consulta(5, "Lucas Pereira", Especialidade.CARDIOLOGIA, LocalDateTime.of(2026, 6, 17, 11, 0), 250.0));
        } catch (HorarioIndisponivelException e) {
            System.out.println("Erro ao inicializar dados semente: " + e.getMessage());
        }
    }
}