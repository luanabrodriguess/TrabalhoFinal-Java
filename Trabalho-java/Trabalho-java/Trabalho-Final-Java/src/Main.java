//Este projeto é um sistema de gerenciamento de eventos que permite a criação, organização e participação em eventos. Nele, há três tipos principais de usuários: organizadores, que criam e gerenciam eventos; participantes, que se inscrevem nesses eventos; e o próprio evento, que armazena informações como título, local e data.

//Esse arquivo .class é tipo um código "traduzido" que a Máquina Virtual do Java (JVM) entende e roda no seu computador. Então, o .class é o código pronto pra execução, mas não é o código que você escreve, é uma versão que a JVM lê e roda.

//src vem de "source" (fonte, em inglês). Então, é tipo: "aqui tá todo o código que a gente escreveu". Isso ajuda a separar o código da galera de outras coisas do projeto, tipo arquivos de configuração, libs, arquivos compilados, etc.
import controller.EventoController; 
import controller.OrganizadorController;
import controller.ParticipanteController;
import java.util.Scanner;
import model.Evento;
import model.Participante;
import model.Relatorio;
import persistence.Serializador;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Cria um scanner para ler o que o usuário digitar

        // Caminho do arquivo onde os dados pré-carregados serão salvos/recuperados
        String caminhoDados = "dados_pre_carregados.ser";
        Object dadosCarregados = null;  // Variável pra guardar os dados que vamos carregar

        try {
            // Tenta carregar os dados do arquivo
            dadosCarregados = Serializador.carregarObjeto(caminhoDados);
        } catch (Exception e) {
            // Se der erro, mostra mensagem no console
            System.out.println("Erro ao carregar dados pré-carregados: " + e.getMessage());
        }

        if (dadosCarregados == null) {
            // Se não tinha dados salvos, cria um exemplo novo e salva
            String dadosExemplo = "Exemplo de dados pré-carregados para o sistema.";
            try {
                Serializador.salvarObjeto(dadosExemplo, caminhoDados);
                System.out.println("Dados pré-carregados criados.");
            } catch (Exception e) {
                // Caso dê erro ao salvar, avisa no console
                System.out.println("Erro ao salvar dados pré-carregados: " + e.getMessage());
            }
        } else {
            // Se conseguiu carregar os dados, mostra no console
            System.out.println("Dados pré-carregados carregados:");
            System.out.println(dadosCarregados.toString());
        }

        // Cria os controllers que vão gerenciar participantes, organizadores e eventos
        OrganizadorController organizadorController = new OrganizadorController(scanner);
        ParticipanteController participanteController = new ParticipanteController(scanner);
        EventoController eventoController = new EventoController(scanner, organizadorController);

        int opcao = -1; // Variável para guardar a opção que o usuário escolhe no menu

        // Loop principal do menu, vai rodar até o usuário digitar 0 para sair
        while (opcao != 0) {
            // Opções que serão exibidas no menu principal
            String[] opcoes = {
                "1 - Gerenciar Participantes",
                "2 - Gerenciar Eventos",
                "3 - Gerenciar Organizadores",
                "4 - Gerar Relatórios",
                "0 - Sair"
            };

            // Mostra o menu e lê a opção digitada pelo usuário
            opcao = obterOpcaoMenu("MENU PRINCIPAL", opcoes, scanner);

            switch (opcao) {
                case 1 ->  // Se escolher "Gerenciar Participantes"
                    menuParticipantes(participanteController, scanner);

                case 2 ->  // Se escolher "Gerenciar Eventos"
                    menuEventos(eventoController, participanteController, scanner);

                case 3 ->  // Se escolher "Gerenciar Organizadores"
                    menuOrganizadores(organizadorController, scanner);

                case 4 ->  // Se escolher "Gerar Relatórios"
                    menuRelatorios(scanner);

                case 0 -> { // Se escolher sair
                    System.out.println("Encerrando...");
                    // Aqui poderia registrar o log do sistema encerrado, mas está comentado
                    // LogUtil.registrar("Sistema encerrado");
                }

                default ->  // Se digitar qualquer coisa que não seja uma opção válida
                    System.out.println("Opção inválida");
            }
        }

        scanner.close(); // Fecha o scanner (importante para liberar recursos)
    }

    // Método que lê do teclado e garante que o usuário digite um número válido (int)
    private static int obterOpcaoValida(Scanner scanner) {
        int opcao = -1;
        while (opcao == -1) {
            try {
                String entrada = scanner.nextLine().trim();  // Lê a linha e tira espaços
                if (entrada.isEmpty()) {
                    System.out.println("Digite um número válido");
                    continue;  // Volta para o começo do while pedindo de novo
                }
                opcao = Integer.parseInt(entrada);  // Tenta converter para número
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido");
            }
        }
        return opcao;  // Retorna o número válido digitado
    }

    // Mostra um menu com um título e opções, e lê a opção escolhida pelo usuário
    private static int obterOpcaoMenu(String titulo, String[] opcoes, Scanner scanner) {
        exibirMenu(titulo, opcoes);  // Mostra o menu na tela
        return obterOpcaoValida(scanner);  // Lê e retorna a opção digitada
    }

    // Mostra o título do menu e as opções formatadas na tela
    private static void exibirMenu(String titulo, String[] opcoes) {
        System.out.println("\n=== " + titulo + " ===");  // Título com destaque
        for (String opcao : opcoes) {
            System.out.println(opcao);  // Lista todas as opções na tela
        }
        System.out.print("Escolha uma opção: ");  // Pede para o usuário escolher
    }

    // Menu específico para gerenciar participantes (cadastrar, listar, etc)
    private static void menuParticipantes(ParticipanteController controller, Scanner scanner) {
        int opcao = -1;

        while (opcao != 0) {
            String[] opcoes = {
                "1 - Cadastrar",
                "2 - Listar",
                "3 - Atualizar",
                "4 - Remover",
                "0 - Voltar"
            };
            opcao = obterOpcaoMenu("Menu Participantes", opcoes, scanner);

            switch (opcao) {
                case 1 -> controller.cadastrar();   // Chama método para cadastrar participante
                case 2 -> controller.listar();      // Lista participantes
                case 3 -> controller.atualizar();   // Atualiza participante
                case 4 -> controller.remover();     // Remove participante
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida");
            }
        }
    }

    // Menu específico para gerenciar eventos
    private static void menuEventos(EventoController eventoController, ParticipanteController participanteController,
            Scanner scanner) {
        int opcao = -1;

        while (opcao != 0) {
            String[] opcoes = {
                "1 - Cadastrar evento",
                "2 - Listar eventos",
                "3 - Atualizar evento",
                "4 - Remover evento",
                "5 - Inscrever participante no evento",
                "0 - Voltar"
            };
            opcao = obterOpcaoMenu("Menu Eventos", opcoes, scanner);

            switch (opcao) {
                case 1 -> eventoController.cadastrar();
                case 2 -> eventoController.listar();
                case 3 -> eventoController.atualizar();
                case 4 -> eventoController.remover();
                case 5 -> inscreverParticipanteEvento(eventoController, participanteController, scanner);
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida");
            }
        }
    }

    // Menu para gerenciar organizadores
    private static void menuOrganizadores(OrganizadorController controller, Scanner scanner) {
        int opcao = -1;

        while (opcao != 0) {
            String[] opcoes = {
                "1 - Cadastrar",
                "2 - Listar",
                "3 - Atualizar",
                "4 - Remover",
                "0 - Voltar"
            };
            opcao = obterOpcaoMenu("Menu Organizadores", opcoes, scanner);

            switch (opcao) {
                case 1 -> controller.cadastrar();
                case 2 -> controller.listar();
                case 3 -> controller.atualizar();
                case 4 -> controller.remover();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida");
            }
        }
    }

    // Menu para gerar relatórios
    private static void menuRelatorios(Scanner scanner) {
        int opcao = -1;
        Relatorio relatorio = new Relatorio();  // Cria o objeto que gera os relatórios

        while (opcao != 0) {
            String[] opcoes = {
                "1 - Gerar relatório geral",
                "2 - Gerar relatório por tipo",
                "3 - Gerar relatório por tipo e ano",
                "0 - Voltar"
            };
            opcao = obterOpcaoMenu("Menu Relatórios", opcoes, scanner);

            switch (opcao) {
                case 1 -> {
                    relatorio.gerar();  // Gera relatório geral
                    LogUtil.registrar("Relatório geral gerado");  // Loga ação
                }
                case 2 -> {
                    System.out.print("Digite o tipo de evento: ");
                    String tipo = scanner.nextLine();
                    relatorio.gerar(tipo);  // Gera relatório por tipo
                    LogUtil.registrar("Relatório gerado para tipo: " + tipo);
                }
                case 3 -> {
                    System.out.print("Digite o tipo de evento: ");
                    String tipo = scanner.nextLine();
                    int ano = -1;
                    while (ano == -1) {  // Lê o ano, validando para ser um número válido
                        try {
                            System.out.print("Digite o ano: ");
                            ano = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Digite um ano válido");
                        }
                    }
                    relatorio.gerar(tipo, ano);  // Gera relatório com tipo e ano
                    LogUtil.registrar("Relatório gerado para tipo: " + tipo + " e ano: " + ano);
                }
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida");
            }
        }
    }

    // Método para inscrever um participante num evento
    private static void inscreverParticipanteEvento(EventoController eventoController,
            ParticipanteController participanteController, Scanner scanner) {

        if (eventoController.getEventos().isEmpty()) { // Se não tem eventos cadastrados
            System.out.println("Não há eventos cadastrados");
            return;  // Sai do método
        }

        eventoController.listar();  // Mostra os eventos disponíveis

        System.out.print("\nDigite o título do evento para inscrever um participante: ");
        String tituloEvento = scanner.nextLine();

        Evento evento = eventoController.buscarEventoPorTitulo(tituloEvento); // Procura o evento pelo título

        if (evento == null) {  // Se não encontrou
            System.out.println("Evento não encontrado!");
            return;
        }

        if (participanteController.getParticipantes().isEmpty()) {  // Se não tem participantes cadastrados
            System.out.println("Não há participantes cadastrados");
            return;
        }

        participanteController.listar();  // Mostra a lista de participantes

        System.out.print("Digite o CPF do participante para inscrever: ");
        String cpfParticipante = scanner.nextLine();

        Participante participante = participanteController.buscarParticipantePorCpf(cpfParticipante);  // Busca participante

        if (participante == null) {  // Se não encontrou participante
            System.out.println("Participante não encontrado!");
            return;
        }

        // Tenta adicionar o participante no evento (retorna true se conseguiu)
        if (evento.adicionarParticipante(participante)) {
            System.out.println("Participante inscrito no evento com sucesso!");
            LogUtil.registrar("Participante " + participante.getNome() + " inscrito no evento " + evento.getTitulo());
        }

        evento.listarParticipantes();  // Mostra os participantes inscritos no evento
    }
}