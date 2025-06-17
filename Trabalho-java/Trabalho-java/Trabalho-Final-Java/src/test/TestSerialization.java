package test;

import model.Organizador;
import model.Participante;
import model.Evento;
import util.Log;
import java.util.ArrayList;
//Organizador, Participante, Evento: Representam os modelos que você vai testar.
//Log: Classe que salva e carrega dados de arquivos.
//ArrayList: Estrutura de dados usada para guardar uma lista de strings.

public class TestSerialization {

    public static void main(String[] args) {
        // Criar objetos de teste
        Organizador org = new Organizador("João", "joao@email.com", "Empresa X", "12345678901");
        Participante part = new Participante("Maria", "maria@email.com", "10987654321");
        Evento evento = new Evento("Evento Teste", "Local Teste", java.time.LocalDate.now(), org);
        evento.adicionarParticipante(part);

        // Serializar dados
        ArrayList<String> dados = new ArrayList<>();
        //Cria uma lista de strings chamada dados para armazenar informações sobre os objetos criados.
        dados.add(org.getDadosString());
        dados.add(part.getDadosString());
        dados.add("Evento: " + evento.getTitulo());
        //Adiciona o título do evento na lista.

        String caminho = "test_data.txt";

        // Salvar dados
        Log.salvarObjeto(String.join(System.lineSeparator(), dados), caminho);

        // Carregar dados
        String dadosCarregados = Log.carregarObjeto(caminho);
        System.out.println("Dados carregados:");
        System.out.println(dadosCarregados);

        // Testar métodos de exibição
        org.exibirDados();
        part.exibirDados();
        evento.exibirResumo();
    }
}
//Esse teste serve para garantir que o processo de serialização e deserialização (salvar e carregar objetos) está funcionando corretamente. Ele faz isso ao:
//Criar objetos fictícios de teste (Organizador, Participante, Evento).
//Salvar as informações desses objetos em um arquivo usando o método salvarObjeto da classe Log.
//Carregar os dados salvos de volta para o programa usando o método carregarObjeto.
//Exibir os dados carregados no console para confirmar que foram armazenados e recuperados corretamente.
