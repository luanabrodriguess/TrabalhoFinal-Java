package controller;

import java.util.ArrayList;
import java.util.Scanner;
import model.ICadastro;
import model.Participante;
import util.LogUtil;
import util.MensagemUtil;

public class ParticipanteController implements ICadastro {
    private ArrayList<Participante> participantes = new ArrayList<>();//lista geral de todos os participantes do sistema
    private Scanner scanner;

    public ParticipanteController(Scanner scanner) {
        this.scanner = scanner;
    }//utilizado apenas esse atributo para usar essa classe

    public ArrayList<Participante> getParticipantes() {
        return participantes;
    }

    @Override
    public void cadastrar() {
        MensagemUtil.exibirMensagemSemNovaLinha("Nome: ");
        String nome = scanner.nextLine();
        while (nome.trim().isEmpty()) {
            MensagemUtil.exibirMensagem("Nome não pode ser vazio. Digite novamente:");
            nome = scanner.nextLine();
        }

        MensagemUtil.exibirMensagemSemNovaLinha("Email: ");
        String email = scanner.nextLine();
        while (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {//usuario.exemplo-123@gmail.com
            MensagemUtil.exibirMensagem("Email inválido. Tente novamente:");
            email = scanner.nextLine();
        }

        MensagemUtil.exibirMensagemSemNovaLinha("CPF (apenas números): ");
        String cpf = scanner.nextLine();

        while (!cpf.matches("\\d{11}")) {
            MensagemUtil.exibirMensagem("CPF inválido. Deve conter 11 dígitos numéricos:");
            cpf = scanner.nextLine();
        }

       
        if (buscarParticipantePorCpf(cpf) != null) {
            MensagemUtil.exibirMensagem("CPF já cadastrado para outro participante.");
            return;
        }

        Participante p = new Participante(nome, email, cpf);
        participantes.add(p);

        LogUtil.registrar("Participante cadastrado: " + nome + " (CPF: " + cpf + ")");
        MensagemUtil.exibirMensagem("Participante cadastrado com sucesso!");
    }

    public Participante buscarParticipantePorCpf(String cpf) {
        for (Participante participante : participantes) {
            if (participante.getCpf().equals(cpf)) {
                return participante;
            }
        }
        return null;
    }

    @Override
    public void listar() {
        if (participantes.isEmpty()) {
            MensagemUtil.exibirMensagem("Não há participantes cadastrados.");
            return;
        }

        MensagemUtil.exibirMensagem("\n=== Lista de Participantes ===");
        for (Participante p : participantes) {
            MensagemUtil.exibirMensagem(p.getDadosString());
        }
    }

    @Override
    public void atualizar() {
        if (participantes.isEmpty()) {
            MensagemUtil.exibirMensagem("Não há participantes cadastrados para atualizar.");
            return;
        }

        MensagemUtil.exibirMensagemSemNovaLinha("Digite o CPF do participante para atualizar: ");
        String cpf = scanner.nextLine();

        for (Participante p : participantes) {
            if (p.getCpf().equals(cpf)) {
                MensagemUtil.exibirMensagemSemNovaLinha("Novo nome (ou Enter para manter): ");
                String novoNome = scanner.nextLine();
                if (!novoNome.isEmpty()) {
                    p.setNome(novoNome);
                }

                MensagemUtil.exibirMensagemSemNovaLinha("Novo email (ou Enter para manter): ");
                String novoEmail = scanner.nextLine();
                if (!novoEmail.isEmpty()) {
                    if (novoEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                        p.setEmail(novoEmail);
                    } else {
                        MensagemUtil.exibirMensagem("Email inválido. Campo não atualizado.");
                    }
                }

                LogUtil.registrar("Participante atualizado: CPF " + cpf);
                MensagemUtil.exibirMensagem("Participante atualizado com sucesso!");
                return;
            }
        }
        MensagemUtil.exibirMensagem("Participante não encontrado.");
    }

    @Override
    public void remover() {
        if (participantes.isEmpty()) {
            MensagemUtil.exibirMensagem("Não há participantes cadastrados para remover.");
            return;
        }

        MensagemUtil.exibirMensagemSemNovaLinha("Digite o CPF do participante para remover: ");
        String cpf = scanner.nextLine();

        boolean removido = participantes.removeIf(p -> p.getCpf().equals(cpf));

        if (removido) {
            LogUtil.registrar("Participante removido: CPF " + cpf);
            MensagemUtil.exibirMensagem("Participante removido com sucesso.");
        } else {
            MensagemUtil.exibirMensagem("Participante não encontrado.");
        }
    }
}
