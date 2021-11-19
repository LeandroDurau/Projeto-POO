import java.util.Scanner;
import java.util.function.DoubleFunction;

public class ContaBancaria extends Usuario{




    void deposito(double dinheiro){
        this.dinheiro = this.dinheiro + dinheiro;
    }

    void retirada(double dinheiro){
        this.dinheiro = this.dinheiro - dinheiro;
    }

    void pix(double dinheiro, String conta){
        this.dinheiro = this.dinheiro - dinheiro;
    }

    void propriedadeConta(){
        System.out.printf("");
    }
}
