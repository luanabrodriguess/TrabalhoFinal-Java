package util;
//Log em dao: É focado em simular interações com dados, substituindo a necessidade de um banco de dados. Ele lida com a persistência de dados como se fosse uma base de dados local usando arquivos.
//Log em util: É uma ferramenta mais genérica para salvar e carregar qualquer tipo de texto. Pode ser usada para logar mensagens, salvar relatórios ou qualquer outro tipo de texto.
//Essa classe serve para salvar textos em arquivos e também ler textos de arquivos. Ela é como uma ferramenta que você pode usar sempre que quiser guardar informações no seu computador e depois recuperá-las.

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
//FileReader: para abrir e ler arquivos de texto.
//FileWriter: para criar ou modificar arquivos de texto.
//IOException: tipo de erro que pode acontecer ao mexer com arquivos (arquivo não existe, problema de permissão, etc).
//PrintWriter: ajuda a escrever texto dentro do arquivo de forma simples.
//Scanner: facilita a leitura linha por linha do arquivo.
//Ela permite que seu programa grava informações (dados, relatórios, logs, mensagens, qualquer texto) em arquivos, para que essas informações fiquem salvas mesmo depois que o programa termina de rodar.
public class Log {

    public static void salvarObjeto(String dados, String caminho) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {
            pw.print(dados);
            pw.flush();
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }//Ele cria ou substitui o arquivo no caminho informado e escreve o conteúdo lá dentro. Caso ocorra algum problema (como falta de permissão ou caminho inválido), ele captura o erro e exibe uma mensagem explicando o que deu errado.

    public static String carregarObjeto(String caminho) {
        try (FileReader fr = new FileReader(caminho);
             Scanner scanner = new Scanner(fr)) {
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
            return null;
        }
    }
    //O método carregarObjeto abre um arquivo no caminho que você passar (caminho), lê todo o conteúdo dele linha por linha, junta tudo em uma única string (com as quebras de linha preservadas) e devolve essa string.
    //Se der algum problema pra abrir ou ler o arquivo, ele mostra uma mensagem de erro e retorna null.
   //Resumo prático: É um método para ler todo o texto de um arquivo e trazer ele para o programa como uma string.
}
