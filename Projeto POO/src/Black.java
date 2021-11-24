public class Black extends ContaBancaria{


    public Black(String nome, String agencia, String conta, double dinheiro) {
        super(nome, agencia, conta, dinheiro);
    }

    @Override
    void deposito(double dinheiro) {
        System.out.printf("Como conta Black você ganha 0,1 de todo deposito feito %n ");
        this.dinheiro = this.dinheiro + dinheiro*1.001;
        Menu.mudarInformação("Account.txt", String.valueOf(this.dinheiro),agencia,6);
    }
}
