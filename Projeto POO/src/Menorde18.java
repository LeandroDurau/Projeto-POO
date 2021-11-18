public class Menorde18 extends ContaBancaria{


    public Menorde18(String senha, String nome, String agencia, String conta) {
        super(senha, nome, agencia, conta);
    }

    @Override
    void investir(float dinheiro, String invest) {
        System.out.printf("Não pode investir, menor de idade");
    }
}
