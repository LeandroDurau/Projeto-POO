import java.io.*;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;


public class Menu{
   // double dinheiro;
    static ContaBancaria contabancaria;
    static boolean pri_mensagem;


    private static final Random RANDOM = new SecureRandom();

    static String criarSal() {
        String stringSal = null;
        byte[] sal = new byte[16];
        RANDOM.nextBytes(sal);
        try {
            stringSal = new String(sal, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        char[] charSal = stringSal.toCharArray();

        for (int i=0;i<charSal.length;i++){
            if (charSal[i]=='#'){
                charSal[i] = '%';
            }
        }
        stringSal = String.valueOf(charSal);
        return stringSal;
    }

    Menu() {
        //cadastro
        var ler = new Scanner(System.in);
        Hash hash = Hash.getInstance();


        System.out.println();
        System.out.println("Insira um novo nome de usuario:");
        String nome = ler.nextLine();
        while (existeusernoArquivo(nome, "Account.txt",0)) {
            System.out.println("Usuario já existe");
            System.out.println("Insira um novo nome de usuario:");
            nome = ler.nextLine();
        }

        String agencia = String.format("%04d", RANDOM.nextInt(10000));
        while (existeusernoArquivo(agencia,"Account.txt",1)){
            agencia = String.format("%04d", RANDOM.nextInt(10000));
        }
        String conta = String.format("%06d", RANDOM.nextInt(1000000));
        while (existeusernoArquivo(conta,"Account.txt",2)){
            conta = String.format("%06d", RANDOM.nextInt(1000000));
        }
        String senha = String.format("%06d", RANDOM.nextInt(100000));
        String sal = criarSal();
        String ssenha = senha;
        senha = senha +sal;
        senha = hash.toString(senha);
        String tipo= "Normal";
        double dinheiro = 0.0;

        AppendArquivo(nome+"#"+agencia+"#"+conta+"#"+sal+"#"+senha+"#"+tipo+"#"+dinheiro,"Account.txt");
        System.out.printf("Seja Bem Vindo ao nosso banco %s%n sua agencia é %s%n sua conta é %s%n sua senha é %s%n",nome,agencia,conta,ssenha);
        contabancaria = new Normal(nome,agencia,conta,dinheiro);

    }
    Menu(int i){
        //login
        i=0;
        String agencia = "";
        String conta = "";
        String senha = "";
        var ler = new Scanner(System.in);
        while (!login(agencia,conta,senha,"Account.txt")) {
            if (i == 1) {
                System.out.printf("Agencia/conta/senha incorretas, tente novamente%n");
            }
            System.out.printf("Insira sua agencia%n");
            agencia = ler.nextLine();
            System.out.printf("Insira sua conta:%n");
            conta = ler.nextLine();
            System.out.printf("Insira sua senha:%n");
            senha = ler.nextLine();
            i = 1;
        }
        String nome = pegarInformação("Account.txt", agencia, 0);
        String tipo = pegarInformação("Account.txt",agencia,5);
        double dinheiro = Double.valueOf(pegarInformação("Account.txt",agencia,6));
        if (tipo.equals("Normal")){
            contabancaria = new Normal(nome,agencia,conta,dinheiro);
        }
        if (tipo.equals("Black")){
            contabancaria = new Black(nome,agencia,conta,dinheiro);
        }
        if (tipo.equals("Menorde18")){
            contabancaria = new Menorde18(nome,agencia,conta,dinheiro);
        }
    }

    public static void  menuEntrada(){
        Menu menu;
        var ler = new Scanner(System.in);
        System.out.printf("Ola seja bem vindo ao banco%n ");
        System.out.printf("O que deseja fazer? %n");
        System.out.printf("1 - Entrar na conta %n");
        System.out.printf("2 - Cadastrar nova conta %n");
        System.out.printf("3 - Fechar programa %n");
        String escolha = ler.nextLine();
        switch (escolha){
            case "1":
                menu = new Menu(0);
                pri_mensagem = true;
                while (true){
                    menuBanco();
                }
                case "2":
                menu = new Menu();
                pri_mensagem = true;
                while (true){
                    menuBanco();
                }
            case "3":
                System.exit(0);
            default:
                System.out.printf("Opção incorreta, insira novamente %n");
                menuEntrada();
        }
    }

    public static void menuBanco(){

        double dinheiro;
        var ler = new Scanner(System.in);
        if (pri_mensagem){
            System.out.printf("Seja bem vindo %s %n", contabancaria.nome);
            pri_mensagem = false;
        }
        System.out.printf("O que deseja fazer ? %n");
        System.out.printf("1 - Ver saldo %n");
        System.out.printf("2 - Depositar na conta corrente %n");
        System.out.printf("3 - Retirar da conta corrente %n");
        System.out.printf("4 - Pix %n");
        System.out.printf("5 - Mudar tipo de conta %n");
        System.out.printf("6 - Mudar senha %n");
        System.out.printf("7 - Fechar programa %n");
        String escolha = ler.nextLine();
        switch (escolha){
            case "1":
                System.out.printf("Você possui R$%.2f %n", contabancaria.dinheiro);
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
                System.out.printf("A qual agencia deseja enviar o dinheiro? %n");
                String agencia = ler.nextLine();
                System.out.printf("Quanto deseja enviar? %n");
                dinheiro = ler.nextDouble();
                contabancaria.pix(dinheiro,agencia);
                break;
            case "5":
                String tipo = contabancaria.getClass().getSimpleName();
                System.out.printf("A sua conta é %s, para qual tipo deseja mudar? %n",tipo);
                if (!tipo.equals("Normal")){

                    System.out.printf("1 - Normal %n");
                }
                if (!tipo.equals("Black")){

                    System.out.printf("2 - Black %n");
                }
                if (!tipo.equals("Menorde18")){

                    System.out.printf("3 - Menor de 18 %n");
                }
                int opção = ler.nextInt();
                switch (opção){
                case 1:
                    contabancaria = new Normal(contabancaria.nome,contabancaria.agencia,contabancaria.conta, contabancaria.dinheiro);
                    mudarInformação("Account.txt","Normal",contabancaria.agencia,5);
                    break;
                case 2:
                    contabancaria = new Black(contabancaria.nome,contabancaria.agencia,contabancaria.conta, contabancaria.dinheiro);
                    mudarInformação("Account.txt","Black",contabancaria.agencia,5);
                    break;
                case 3:
                    contabancaria = new Menorde18(contabancaria.nome,contabancaria.agencia,contabancaria.conta, contabancaria.dinheiro);
                    mudarInformação("Account.txt","Menorde18",contabancaria.agencia,5);
                    break;
                default:
                    System.out.printf("Opção incorreta, insira novamente %n");
            }

                break;
            case "6":
                boolean senha_certa = false;
                String nova_senha = "";
                while (!senha_certa) {
                    System.out.printf("Para mudar sua conta você deve escrever sua senha novamente: %n");
                    String senha = ler.nextLine();
                    senha_certa = login(contabancaria.agencia, contabancaria.conta, senha, "Account.txt");
                    if (!senha_certa) System.out.printf("Senha incorreta %n");
                }
                senha_certa = false;
                while (!senha_certa) {
                    System.out.printf("Qual é a nova senha? %n");
                    System.out.printf("OBS: senha deve ser de 6 digitos só com números %n");
                    nova_senha = ler.nextLine();
                    senha_certa = senhaValida(nova_senha);
                }
                mudarInformação("Account.txt",nova_senha,contabancaria.agencia,4);
                break;

            case "7":
                System.exit(0);

            default:
                System.out.printf("Opção incorreta, insira novamente %n");



        }

    }


    static boolean senhaValida(String senha){
        if (senha.length() < 6){
            System.out.printf("A senha é muito pequena %n");
            return false;
        }
        if (senha.length() > 6){
            System.out.printf("A senha é muito grande %n");
            return false;
        }

        String Num = "[0-9]+";
        if (!senha.matches(Num)){
            System.out.printf("A senha possui caracteres errados %n");
            return false;
        }


        return true;

    }
    static boolean existeusernoArquivo(String user, String nomeArquivo,int posi) {

        try {
            BufferedReader br = new BufferedReader(

                    new InputStreamReader(new FileInputStream(nomeArquivo), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] us = line.split("\\#");
                if (us[posi].equals(user)) {
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    static String  pegarInformação(String nomeArquivo, String agencia,int localização){
        try {
            String nome;
            BufferedReader br = new BufferedReader(

                    new InputStreamReader(new FileInputStream(nomeArquivo), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] us = line.split("\\#");
                if (us[1].equals(agencia)) {
                    br.close();

                    return nome = us[localização];
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean login(String agencia,String conta,String senha,String nomeArquivo) {
        Hash hash = Hash.getInstance();
        try {
            BufferedReader br = new BufferedReader(

                    new InputStreamReader(new FileInputStream(nomeArquivo), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] us = line.split("\\#");
                if (us[1].equals(agencia) && us[2].equals(conta)){
                    String sal = us[3];
                    senha = senha + sal;
                    senha = hash.toString(senha);
                    if (us[4].equals(senha)){
                        br.close();
                        return true;
                    }else return false;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    public static void AppendArquivo(String conteudo, String nomeArquivo) {

        try {
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(nomeArquivo, true));
            bw.newLine();
            bw.write(conteudo);
            bw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mudarInformação(String nomeArquivo, String informação, String agencia, int posi){
        Hash hash = Hash.getInstance();
        String[][] us = new String[100][7];
        try {
            BufferedReader br = new BufferedReader(

                    new InputStreamReader(new FileInputStream(nomeArquivo), "UTF-8"));
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] u= line.split("\\#");
                us[i] = u;
                i++;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < us.length; i++){
            if (agencia.equals(us[i][1])){
                if (posi == 4){
                    informação = informação + us[i][3];
                    informação = hash.toString(informação);
                }
                us[i][posi] = informação;
            }
        }
        reescreverArquivo(us,"Account.txt");
    }

    public static void reescreverArquivo(String[][] conteudo, String nomeArquivo) {

        try {
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(nomeArquivo, false));
            for (int i=0;i< conteudo.length;i++) {
                if (conteudo[i][0]==null){
                    continue;
                }
                bw.write(conteudo[i][0]+"#"+conteudo[i][1]+"#"+conteudo[i][2]+"#"+conteudo[i][3]+"#"+conteudo[i][4]+"#"+conteudo[i][5]+"#"+conteudo[i][6]);
                if (conteudo[i+1][0]==null){
                    continue;
                }
                bw.newLine();
            }
            bw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
       new Menu();
        new Menu(0);
    }
}