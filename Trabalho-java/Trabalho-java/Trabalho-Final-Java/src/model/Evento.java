package model;

import java.io.Serializable;
//A serialização transforma um objeto em uma sequência de bytes.
//Esses bytes podem ser:
//Armazenados em arquivos.

import java.time.LocalDate;
import java.util.ArrayList;
import util.LogUtil;

public class Evento implements Serializable {//Diz que a classe pode ser "serializada", ou seja, convertida para um formato que dá para salvar ou carregar.
//NÃO É UM POLIMORFISMO
//Isso é só um contrato que a classe aceita para ser serializável, não envolve comportamento diferente em tempo de execução.
    private static final long serialVersionUID = 987654321L; //entao o id serve para manter a versao que esta criada naquela classe como base padrao para salvar os arquivos
    // se voce fizer alteracoes o id garante que vai ser salvo como o padrao criado com esse id de agora

    private String titulo;
    private String local;
    private LocalDate data;
    private Organizador organizador;
    private ArrayList<Participante> participantes;
   //Só declara que a variável participantes existe e será usada como uma lista de objetos do tipo Participante. Mas até que a lista seja inicializada, ela é apenas uma referência nula (ou seja, não aponta para nada).
    public Evento(String titulo, String local, LocalDate data, Organizador organizador) {
        this.titulo = titulo;
        this.local = local;
        this.data = data;
        this.organizador = organizador;
        this.participantes = new ArrayList<>();
        //lista de participantes especificas de um evento
        //p criaçao de um evento 
    }

    public boolean adicionarParticipante(Participante participante) {//ele cadastra no eventocontroller e add no evento
        //5 - evento add participante, ele nao esta no controller pq sao cadastros gerais, nao especificos para o evento
        //se fosse void ele nao retornaria nada.
        for (Participante p : participantes) {
            if (p.getCpf().equals(participante.getCpf())) {
                LogUtil.registrar("Participante já está inscrito neste evento!");
                return false;
            }
        }
        participantes.add(participante);
        return true;
    }

    public void listarParticipantes() {// imagina q essas classes é para rodar em um evento especifico
        if (participantes.isEmpty()) {
            LogUtil.registrar("Nenhum participante inscrito neste evento.");
            return;
        }

        LogUtil.registrar("Participantes inscritos:");
        for (Participante participante : participantes) {
            LogUtil.registrar("- " + participante.getNome() + " (CPF: " + participante.getCpf() + ")");//registra o cpf de cada um
        }
    }

    public void exibirResumo() {//listareventos
        String resumo = String.format("Evento: %s | Local: %s | Data: %s | Organizador: %s | Participantes: %d",
                titulo, local, data.toString(), organizador.getNome(), participantes.size());
        LogUtil.registrar(resumo);//registra a acao no log, o resumo
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }

    public ArrayList<Participante> getParticipantes() {
        return participantes;
    }
    /*getTitulo, setTitulo: Para acessar e alterar o título.

getLocal, setLocal: Para acessar e alterar o local.

getData, setData: Para acessar e alterar a data.

getOrganizador, setOrganizador: Para acessar e alterar o organizador.

getParticipantes: Para pegar a lista de participantes (não tem set porque não faz sentido trocar toda a lista de uma vez). */
}
