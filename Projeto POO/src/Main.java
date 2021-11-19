import java.util.Scanner;

public class Main {
    static Usuario user;
    static ContaBancaria contabancaria;

     public static void  menuEntrada(){
        var ler = new Scanner(System.in);
        System.out.printf("Ola seja bem vindo ao banco%n ");
        System.out.printf("O que deseja fazer? %n");
        System.out.printf("1 - Entrar na conta %n");
        System.out.printf("2 - Cadastrar nova conta %n");
        System.out.printf("3 - Fechar programa %n");
        String escolha = ler.nextLine();
        switch (escolha){
            case "1":
                user = new Usuario(0);
                menuBanco();
                break;
            case "2":
                user = new Usuario();
                menuBanco();
                break;
            case "3":
                System.exit(0);
            default:
                System.out.printf("Opção incorreta, insira novamente %n");
                menuEntrada();
                break;
        }
    }

    public static void menuBanco(){
         contabancaria = (ContaBancaria) user;
        double dinheiro;
        var ler = new Scanner(System.in);
        System.out.printf("Seja bem vindo %s %n", contabancaria.nome);
        System.out.printf("O que deseja fazer ? %n");
        System.out.printf("1 - Ver saldo %n");
        System.out.printf("2 - Depositar na conta corrente %n");
        System.out.printf("3 - Retirar da conta corrente %n");
        System.out.printf("4 - Pix %n");
        System.out.printf("5 -  %n");
        System.out.printf("6 - Fechar programa %n");
        String escolha = ler.nextLine();
        switch (escolha){
            case "1":
                System.out.printf("Você possui R$%2f %n", contabancaria.dinheiro);
                break;
            case "2":
                System.out.printf("Quanto dinheiro deseja depositar? %n");
                dinheiro = ler.nextDouble();
                contabancaria.deposito(dinheiro);
                break;
            case "3":
                System.out.printf("Quanto dinheiro deseja retirar? %n");
                dinheiro = ler.nextDouble();
                contabancaria.retirada(dinheiro);
                break;
            case "4":
                System.out.printf("A qual conta deseja enviar o dinheiro? %n");
                String conta = ler.nextLine();
                System.out.printf("Quando deseja enviar? %n");
                dinheiro = ler.nextDouble();
                contabancaria.pix(dinheiro,conta);
                break;
            case "5":

                break;
            case "6":
                System.exit(0);

            default:
                System.out.printf("Opção incorreta, insira novamente %n");



        }

    }



    public static void main(String[] args) {
         menuEntrada();
    }
}
