
package model;

import java.io.Serializable;

public class Participante extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf;

    public Participante(String nome, String email, String cpf) {
        super(nome, email);
        this.cpf = cpf;
    }

    

    @Override
    public void exibirDados() {
        // Método mantido para compatibilidade, sem saída direta criado para a organização do relatorio
    }

    public String getDadosString() {
        return "[Participante] Nome: " + nome + " | Email: " + email + " | CPF: " + cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
