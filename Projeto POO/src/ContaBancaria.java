public class ContaBancaria extends Usuario{
    private float dinheiro;
    private String nome,agencia,conta;

    public ContaBancaria(String senha,String nome, String agencia, String conta) {
        super(senha);
        this.nome = nome;
        this.agencia = agencia;
        this.conta = conta;
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
