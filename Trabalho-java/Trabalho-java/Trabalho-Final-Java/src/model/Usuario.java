package model;

import java.io.Serializable;
//nao pode ser criado metodos para criar objetos como cadastrar,nao pode criar um objeto nela, so pode ser usada como modelo para ser herdada em outras classes
public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 123456789L; // Id melhor para serialização

    protected String nome;// se nao for herdada nao tem acesso a esse atributo
    protected String email;

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public abstract void exibirDados();

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;//nao retornam nenhum valor
    }

    public void setNome(String nome) {
        this.nome = nome;//retorna a palavra 'nome'
    }

    public void setEmail(String email) {
        this.email = email;
    }
    //void: Faz algo, mas não dá nada de volta.

//String: Faz algo e devolve um texto para você usar.
}