package controller;
//Isso aqui define que essa classe EventoController está dentro da pasta (ou pacote) controller. É um jeito de organizar o código.

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import util.LogUtil;
import model.*;
import util.MensagemUtil;
/*LocalDate e DateTimeParseException ajudam a trabalhar com datas.
ArrayList é uma lista que pode crescer, onde a gente vai guardar os eventos.
Scanner é para ler o que o usuário digita no teclado.
LogUtil e MensagemUtil são classes que provavelmente você ou seu time criaram para mostrar mensagens e gravar logs.
model.* traz todas as classes do pacote model, como a classe Evento e outras.*/

public class EventoController implements ICadastro {
    /*  Por que Polimorfismo na EventoController?
1. Interface como Contrato (ICadastro)
A classe EventoController implementa a interface ICadastro, que define métodos como cadastrar, listar, atualizar e remover.

Polimorfismo permite que diferentes classes possam implementar a mesma interface, mas com comportamentos personalizados.

Exemplo: Se você tiver um ParticipanteController ou OrganizadorController, ambos podem implementar ICadastro e ter implementações diferentes para esses métodos.herança so pode ser herdada uma vez e temos e utilizar os metodos do icadastro varias vezes*/
    //Começa a definição da classe EventoController.
//Ela implementa a interface ICadastro, que significa que essa classe vai ter que ter os métodos que ICadastro exige (como cadastrar, listar, atualizar, remover).
    private ArrayList<Evento> eventos = new ArrayList<>();
    //Aqui a gente cria uma lista vazia para guardar os eventos que forem cadastrados.
    private OrganizadorController organizadorController;
    private Scanner scanner;
    //organizadorController: para gerenciar os organizadores dos eventos.
//scanner: para ler o que o usuário digita.

    public EventoController(Scanner scanner, OrganizadorController organizadorController) {
        this.scanner = scanner;
        this.organizadorController = organizadorController;
    }
    //construtor: para utilizar o (chamar) eventocontroller precisa de um scanner e um organizadorcontroller, onde eles vao estar interligados

    public ArrayList<Evento> getEventos() {
        return eventos;
    } 
    // é um metodo chamado getEventos para retornar a lista caso precise em alguma classe

    @Override // ei, voce esta criando um metodo, (sobrescrevendo) que ja foi criado primeiro em outro lugar (ICadastro)
    public void cadastrar() {
        MensagemUtil.exibirMensagemSemNovaLinha("Título do evento: ");
        String titulo = scanner.nextLine();
        //mostra a mensagem titulo do evento e le o que o usuario digita

        MensagemUtil.exibirMensagemSemNovaLinha("Local do evento: ");
        String local = scanner.nextLine();
        //mostra a mensagem local do evento e le oq o usuario digita

        LocalDate data = null; // criacao da variavel data com o valor nulo inicialmente
        while (data == null) { // loop que continua executando data enquanto for nulo
            try { // tenta executar o codigo dentro do bloco
                MensagemUtil.exibirMensagemSemNovaLinha("Data (AAAA-MM-DD): ");
                data = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                MensagemUtil.exibirMensagem("Formato de data inválido. Use o formato AAAA-MM-DD.");
            }
        }
        //Aqui ele tenta ler a data do evento.
//Usa um loop até o usuário digitar uma data válida no formato certo (ex: 2025-06-14).
//Se o formato estiver errado, mostra uma mensagem e pede de novo.

        
        if (organizadorController.getOrganizadores().isEmpty()) {
            MensagemUtil.exibirMensagem("Não há organizadores cadastrados. Cadastre um organizador primeiro.");
            return;
        }
        //Antes de continuar, verifica se existe pelo menos um organizador cadastrado.
        //Se não tiver, mostra mensagem e termina o método (return), porque não dá pra cadastrar evento sem organizador.
        //isempty - Verifica se essa lista está vazia.

        organizadorController.listar();

        MensagemUtil.exibirMensagemSemNovaLinha("Digite o CPF do organizador: ");
        String cpfOrg = scanner.nextLine();
        Organizador organizador = organizadorController.buscarOrganizadorPorCpf(cpfOrg);

        if (organizador == null) {// se o organizador nao existir
            MensagemUtil.exibirMensagem("Organizador não encontrado. Deseja cadastrar um novo? (S/N)");
            String opcao = scanner.nextLine();
            if (opcao.equalsIgnoreCase("S")) {
                organizadorController.cadastrar();
                //metodo cadastrar que foi desenvolvido em organizadorcontroller
                MensagemUtil.exibirMensagemSemNovaLinha("Digite novamente o CPF do organizador: ");
                cpfOrg = scanner.nextLine();
                organizador = organizadorController.buscarOrganizadorPorCpf(cpfOrg);

                if (organizador == null) {
                    MensagemUtil.exibirMensagem("Organizador ainda não encontrado. Evento não cadastrado.");
                    return;
                }
            } else {
                MensagemUtil.exibirMensagem("Evento não cadastrado.");
                return;//Informa ao usuário que o evento não será cadastrado porque ele escolheu não cadastrar um novo organizador.
            }
        }

        Evento evento = new Evento(titulo, local, data, organizador);
        eventos.add(evento);

        LogUtil.registrar("Evento cadastrado: " + titulo);
        MensagemUtil.exibirMensagem("Evento cadastrado com sucesso!");
        //Aqui temos uma chamada para LogUtil (provavelmente uma classe utilitária) que registra a ação de cadastrar o evento no log. Depois, MensagemUtil exibe uma mensagem para o usuário informando o sucesso do cadastro.
    }

    @Override
    public void listar() {
        if (eventos.isEmpty()) {
            MensagemUtil.exibirMensagem("Não há eventos cadastrados.");
            return;
        }

        MensagemUtil.exibirMensagem("\n=== Lista de Eventos ===");
        for (Evento e : eventos) {
            e.exibirResumo();
        }
    }

    @Override
    public void atualizar() {
        if (eventos.isEmpty()) {
            MensagemUtil.exibirMensagem("Não há eventos cadastrados para atualizar.");
            return;
        }

        MensagemUtil.exibirMensagemSemNovaLinha("Digite o título do evento para atualizar: ");
        String titulo = scanner.nextLine();

        for (Evento e : eventos) {
            if (e.getTitulo().equalsIgnoreCase(titulo)) {
                //ele roda todos os titulos e verifica se é igual ao titulo informado
                MensagemUtil.exibirMensagemSemNovaLinha("Novo local (ou Enter para manter): ");
                String novoLocal = scanner.nextLine();
                if (!novoLocal.isEmpty()) {
                    e.setLocal(novoLocal);
                }

                LocalDate novaData = null;
                while (novaData == null) {
                    try {
                        MensagemUtil.exibirMensagemSemNovaLinha("Nova data (AAAA-MM-DD) (ou Enter para manter): ");
                        String dataStr = scanner.nextLine();
                        if (dataStr.isEmpty()) {
                            break;
                        }
                        novaData = LocalDate.parse(dataStr);
                        e.setData(novaData);
                    } catch (DateTimeParseException ex) {
                        MensagemUtil.exibirMensagem("Formato de data inválido. Use o formato AAAA-MM-DD.");
                    }
                }

                LogUtil.registrar("Evento atualizado: " + titulo);
                MensagemUtil.exibirMensagem("Evento atualizado com sucesso!");
                return;
            }
        }
        MensagemUtil.exibirMensagem("Evento não encontrado.");
    }

    @Override
    public void remover() {
        if (eventos.isEmpty()) {
            MensagemUtil.exibirMensagem("Não há eventos cadastrados para remover.");
            return;
        }

        MensagemUtil.exibirMensagemSemNovaLinha("Digite o título do evento para remover: ");
        String titulo = scanner.nextLine();

        boolean removido = eventos.removeIf(e -> e.getTitulo().equalsIgnoreCase(titulo));
        //Remove da lista o evento que tem o título igual ao digitado.

        if (removido) {
            LogUtil.registrar("Evento removido: " + titulo);
            MensagemUtil.exibirMensagem("Evento removido com sucesso.");
        } else {
            MensagemUtil.exibirMensagem("Evento não encontrado.");
        }
    }

    public Evento buscarEventoPorTitulo(String titulo) {
        for (Evento evento : eventos) {
            if (evento.getTitulo().equalsIgnoreCase(titulo)) {
                return evento;
            }
        }
        return null;
    }
    //O método buscarEventoPorTitulo é usado por outros métodos, como cadastrar (ao verificar o organizador associado) ou remover. Ele não precisa ser chamado diretamente pelo usuário, mas serve como uma função auxiliar para facilitar o trabalho dos métodos principais.
}
