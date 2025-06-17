package dao;
//ele só salva dados em arquivos de texto no seu computador.
//Então, nesse trecho, o armazenamento é feito via arquivos, não via banco de dados tipo MySQL, PostgreSQL, SQLite, etc.
//A pasta DAO é onde ficam as classes que têm a missão de mexer nos dados: salvar, buscar, apagar, atualizar
//Log.java está lá porque ele é o cara que cuida de salvar e carregar informações de arquivos. Mesmo que não tenha banco de dados no projeto, ele faz algo parecido: acessa dados e armazena eles, só que em arquivos.
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
//FileReader: Lê dados de um arquivo.
//FileWriter: Escreve dados em um arquivo.
//IOException: Trata erros relacionados a arquivos.
//PrintWriter: Ajuda a escrever texto em arquivos.
//Scanner: Lê dados, linha por linha, de um arquivo ou entrada.

public class Log {

    public static void salvarObjeto(String dados, String caminho) {//Esse método serve para salvar dados num arquivo.
        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {
            //PrintWriter e FileWriter são usados para escrever no arquivo.
            //O try (...) garante que o arquivo será fechado certinho no final.
            pw.print(dados);
            //Escreve os dados no arquivo.
            pw.flush();
            //Garante que os dados foram realmente gravados no arquivo.
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public static String carregarObjeto(String caminho) {//Esse método serve para ler dados de um arquivo. le e retorna o arquivo montado
        try (FileReader fr = new FileReader(caminho);
             Scanner scanner = new Scanner(fr)) {//Abre o arquivo para leitura usando o FileReader.
                                                //O Scanner lê os dados do arquivo linha por linha.
            StringBuilder sb = new StringBuilder();
            //O StringBuilder é usado para ir montando o texto que está sendo lido. montando o relatorio solicitado
            while (scanner.hasNextLine()) {
                //Enquanto o arquivo tiver linhas para ler, continue.
                sb.append(scanner.nextLine());
                //Adiciona cada linha do arquivo no StringBuilder.
                sb.append(System.lineSeparator());
                //Adiciona uma quebra de linha entre as linhas lidas.
            }
            return sb.toString();
            //Retorna todo o texto que foi lido como uma única string.
        } catch (IOException e) {
            //Se algo der errado na leitura, o programa cai aqui.
            System.out.println("Erro ao carregar dados: " + e.getMessage());
            return null;
        }
    }
}
