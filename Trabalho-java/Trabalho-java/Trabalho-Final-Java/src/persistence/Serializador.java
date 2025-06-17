package persistence;
//ent o serializador serve para ajudar a converter os objetos criados pelo usuario em formato de arquivo para vir no relatorio
//persistence porque essa pasta geralmente é usada para organizar o código que lida com o armazenamento e recuperação de dados.
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//FileInputStream e FileOutputStream: Usados para ler e escrever em arquivos.
//ObjectInputStream e ObjectOutputStream: Usados para transformar objetos em dados que podem ser salvos ou lidos de arquivos.
//IOException: Lida com erros que podem acontecer ao trabalhar com arquivos.

public class Serializador {//O objetivo dessa classe é manipular objetos como dados para armazenamento.

    public static void salvarObjeto(Object objeto, String caminho) {//os objetos seria os eventos, participantes e organizadores criados pelo usuario, o void É usado para métodos que só fazem algo e não devolvem nada, como salvar um objeto
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {//ObjectOutputStream: Prepara o objeto para ser gravado no arquivo.
          //new FileOutputStream(caminho): Abre (ou cria) um arquivo no caminho especificado.
            oos.writeObject(objeto);//Transforma o objeto em bytes e grava no arquivo.
            System.out.println("Objeto salvo com sucesso em " + caminho);
            //Mostra no console que o objeto foi salvo com sucesso no caminho especificado.
        } catch (IOException e) {
            System.out.println("Erro ao salvar objeto: " + e.getMessage());
            //catch: Se algo der errado (exemplo: problema de permissão ou caminho inválido), essa parte pega o erro e exibe uma mensagem.
        }
    }

    public static Object carregarObjeto(String caminho) {//Use Object (ou outros tipos de retorno) quando o método precisa devolver um valor ou objeto para quem o chamou. o object É usado quando o método precisa devolver algo, como carregar um evento salvo
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            Object objeto = ois.readObject();
            System.out.println("Objeto carregado com sucesso de " + caminho);
            return objeto;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar objeto: " + e.getMessage());
            return null;
        }
    }
}
