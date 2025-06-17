package model;

import java.io.Serializable;
//Serializable: Faz a classe poder ser transformada em um formato que dá para salvar em arquivo
public class Organizador extends Usuario implements Serializable { // pode ser convertida em arquivo
    ///Organizador herda de Usuario:

//A palavra-chave extends significa que a classe Organizador está herdando todas as propriedades e métodos de Usuario.

//Isso permite que a classe Organizador reutilize código da classe Usuario, como os atributos nome e email e os métodos públicos definidos lá.
    private static final long serialVersionUID = 1L;//serialVersionUID: É um número único para a classe Organizador. Ele é usado pelo Java para garantir que a classe não mudou de forma incompatível ao salvar ou carregar um objeto.
//padrao de arquivo a ser salvo vai ser desta forma
//atributo de serialização
    private String empresa;
    private String cpf;

    public Organizador(String nome, String email, String empresa, String cpf) {
        super(nome, email);
        this.empresa = empresa;
        this.cpf = cpf;
    }

    @Override
    public void exibirDados() {
        // Método mantido para compatibilidade, sem saída direta
    }

    public String getDadosString() {
        return "[Organizador] Nome: " + nome + " | Email: " + email + " | Empresa: " + empresa + " | CPF: " + cpf;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
