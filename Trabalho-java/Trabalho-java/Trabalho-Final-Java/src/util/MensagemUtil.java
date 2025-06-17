package util;

public class MensagemUtil {

    public static void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public static void exibirMensagemSemNovaLinha(String mensagem) {
        System.out.print(mensagem);
    }
}
//MensagemUtil é uma ajudante pra mostrar mensagens no programa, uma com quebra de linha e outra sem, pra deixar a interação com o usuário mais organizada.