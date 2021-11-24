import java.util.Scanner;
import java.util.function.DoubleFunction;

public class ContaBancaria {

    double dinheiro;
    String nome, agencia, conta;

    public ContaBancaria(String nome, String agencia, String conta, double dinheiro) {
        this.nome = nome;
        this.agencia = agencia;
        this.conta = conta;
        this.dinheiro = dinheiro;
    }

    void deposito(double dinheiro){
        this.dinheiro = this.dinheiro + dinheiro;
        Menu.mudarInformação("Account.txt", String.valueOf(this.dinheiro),agencia,6);
    }

    void retirada(double dinheiro){
        if (this.dinheiro < dinheiro){
            System.out.printf("Você não possui a quantia necessaria %n");
            return;
        }
        this.dinheiro = this.dinheiro - dinheiro;
        Menu.mudarInformação("Account.txt", String.valueOf(this.dinheiro),agencia,6);
    }

    void pix(double dinheiro, String agencia){
        if (this.dinheiro < dinheiro){
            System.out.printf("Você não possui a quantia necessaria %n");
            return;
        }
        this.dinheiro = this.dinheiro - dinheiro;
        double pixdinheiro= Double.parseDouble(Menu.pegarInformação("Account.txt",agencia,6));
        pixdinheiro += dinheiro;
        Menu.mudarInformação("Account.txt", String.valueOf(this.dinheiro),this.agencia,6);
        Menu.mudarInformação("Account.txt", String.valueOf(pixdinheiro),agencia,6);
    }

}
