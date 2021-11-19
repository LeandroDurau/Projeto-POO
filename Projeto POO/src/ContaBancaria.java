public class ContaBancaria extends Usuario{

    ContaBancaria() {
        super();
    }
    ContaBancaria(int i){
        super(i);
    }

    void deposito(float dinheiro){
        this.dinheiro = this.dinheiro + dinheiro;
    }

    void retirada(float dinheiro){
        this.dinheiro = this.dinheiro - dinheiro;
    }

    void pagarConta(float dinheiro, String conta){
        this.dinheiro = this.dinheiro - dinheiro;
    }

    void investir(float dinheiro, String invest){
        this.dinheiro = this.dinheiro - dinheiro;
    }



    void propriedadeConta(){
        System.out.printf("");
    }
}
