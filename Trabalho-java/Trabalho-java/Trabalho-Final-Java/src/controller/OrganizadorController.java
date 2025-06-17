package controller;

import java.util.ArrayList;
import java.util.Scanner;
import model.ICadastro;
import model.Organizador;
import util.LogUtil;
import util.MensagemUtil;
// importação serve para reutilizar o código
public class OrganizadorController implements ICadastro {
    private ArrayList<Organizador> organizadores = new ArrayList<>();
    private Scanner scanner;

    public OrganizadorController(Scanner scanner) {
        this.scanner = scanner;
    }

    public ArrayList<Organizador> getOrganizadores() {
        return organizadores;
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
        while (email.trim().isEmpty() || !email.contains("@")) {
            MensagemUtil.exibirMensagem("Email inválido. Tente novamente:");
            email = scanner.nextLine();
        }//Valida se não está vazio e se contém o caractere @.

        MensagemUtil.exibirMensagemSemNovaLinha("Empresa: ");
        String empresa = scanner.nextLine();

        MensagemUtil.exibirMensagemSemNovaLinha("CPF (apenas números): ");
        String cpf = scanner.nextLine();
        while (cpf.trim().length() != 11 || !cpf.chars().allMatch(Character::isDigit)) {
            MensagemUtil.exibirMensagem("CPF inválido. Deve conter 11 dígitos numéricos:");
            cpf = scanner.nextLine();
        }//Valida se contém exatamente 11 dígitos e se todos são números.

        
        if (buscarOrganizadorPorCpf(cpf) != null) {
            MensagemUtil.exibirMensagem("CPF já cadastrado para outro organizador.");
            return;//vai verificar se esse cpf ja nao foi cadastrado, ja nao tem na lista
        }

        Organizador o = new Organizador(nome, email, empresa, cpf);
        organizadores.add(o);

        LogUtil.registrar("Organizador cadastrado: " + nome + " (CPF: " + cpf + ")");
        MensagemUtil.exibirMensagem("Organizador cadastrado com sucesso!");
    }

    @Override
    public void listar() {
        if (organizadores.isEmpty()) {
            MensagemUtil.exibirMensagem("Não há organizadores cadastrados.");
            return;
        }

        MensagemUtil.exibirMensagem("\n=== Lista de Organizadores ===");
        for (Organizador o : organizadores) {
            MensagemUtil.exibirMensagem(o.getDadosString());
        }
    }

    @Override
    public void atualizar() {
        if (organizadores.isEmpty()) {
            MensagemUtil.exibirMensagem("Não há organizadores cadastrados para atualizar.");
            return;
        }

        MensagemUtil.exibirMensagemSemNovaLinha("Digite o CPF do organizador para atualizar: ");
        String cpf = scanner.nextLine();

        for (Organizador o : organizadores) {
            if (o.getCpf().equals(cpf)) {
                MensagemUtil.exibirMensagemSemNovaLinha("Novo email (ou Enter para manter): ");
                String novoEmail = scanner.nextLine();
                if (!novoEmail.isEmpty()) {
                    if (novoEmail.contains("@")) {//Verifica se o novo email contém o caractere @, o que é um requisito básico para ser considerado válido.
                        o.setEmail(novoEmail);
                    } else {
                        MensagemUtil.exibirMensagem("Email inválido. Campo não atualizado.");
                    }//O programa não interrompe ou reinicia devido ao email inválido; ele simplesmente pula a atualização do email e segue para atualizar o campo da empresa.
                }

                MensagemUtil.exibirMensagemSemNovaLinha("Nova empresa (ou Enter para manter): ");
                String novaEmpresa = scanner.nextLine();
                if (!novaEmpresa.isEmpty()) {
                    o.setEmpresa(novaEmpresa);
                }// set pode alterar

                LogUtil.registrar("Organizador atualizado: CPF " + cpf);
                MensagemUtil.exibirMensagem("Organizador atualizado com sucesso!");
                return;
            }
        }
        MensagemUtil.exibirMensagem("Organizador não encontrado.");
    }

    @Override
    public void remover() {
        if (organizadores.isEmpty()) {
            MensagemUtil.exibirMensagem("Não há organizadores cadastrados para remover.");
            return;
        }

        MensagemUtil.exibirMensagemSemNovaLinha("Digite o CPF do organizador para remover: ");
        String cpf = scanner.nextLine();

        boolean removido = organizadores.removeIf(o -> o.getCpf().equals(cpf));

        if (removido) {
            LogUtil.registrar("Organizador removido: CPF " + cpf);
            MensagemUtil.exibirMensagem("Organizador removido com sucesso.");
        } else {
            MensagemUtil.exibirMensagem("Organizador não encontrado.");
        }
    }

    public Organizador buscarOrganizadorPorCpf(String cpf) {
        for (Organizador org : organizadores) {
            if (org.getCpf().equals(cpf)) {
                return org;
            }
        }
        return null;
    }//teste
}
