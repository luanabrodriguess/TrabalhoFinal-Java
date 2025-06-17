
package model;

import java.io.Serializable;
import util.LogUtil;

public class Relatorio implements Serializable {
    private static final long serialVersionUID = 1L;

    public void gerar() {
        LogUtil.registrar("Relatório geral gerado.");
    }//registra o relatorio gral no log
    //Polimorfismo: mesmo nome, diferentes parâmetros(sobrecarga)

    public void gerar(String tipoEvento) {
        LogUtil.registrar("Relatório para eventos do tipo: " + tipoEvento);
    }

    public void gerar(String tipoEvento, int ano) {
        LogUtil.registrar("Relatório do tipo " + tipoEvento + " para o ano " + ano);
    }

//Conceito	Onde acontece	O que muda
//Sobrecarga Na mesma classe	Muda parâmetros do método e o metodo
//Sobrescrita Entre classe mãe e filha	Muda o comportamento do método
}
