public class Menorde18 extends ContaBancaria{


    public Menorde18(String nome, String agencia, String conta, double dinheiro) {
        super(nome, agencia, conta, dinheiro);
    }

    void pix(double dineiro,String agencia){
        System.out.printf("Você é menor de idade é não pode fazer pix %n");

    }
}
