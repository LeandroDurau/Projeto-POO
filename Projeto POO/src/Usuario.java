import java.io.*;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

public class Usuario {
    double dinheiro;
    String nome, agencia, conta, senha;

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

    Usuario() {
        //cadastro
        var ler = new Scanner(System.in);
        Hash hash = Hash.getInstance();

        System.out.println();
        System.out.println("Insira um novo nome de usuario:");
        nome = ler.nextLine();
        while (existeusernoArquivo(nome, "Account.txt",0)) {
            System.out.println("Usuario já existe");
            System.out.println("Insira um novo nome de usuario:");
            nome = ler.nextLine();
        }
        this.nome = nome;
        agencia = String.format("%04d", RANDOM.nextInt(10000));
        while (existeusernoArquivo(agencia,"Account.txt",1)){
            agencia = String.format("%04d", RANDOM.nextInt(10000));
        }
        conta = String.format("%06d", RANDOM.nextInt(1000000));
        while (existeusernoArquivo(conta,"Account.txt",2)){
            conta = String.format("%06d", RANDOM.nextInt(1000000));
        }
        senha = String.format("%06d", RANDOM.nextInt(100000));
        String sal = criarSal();
        String ssenha = senha;
        senha = senha +sal;
        senha = hash.toString(senha);
        String tipo= "Normal";
        dinheiro = 0.0;

        AppendArquivo(nome+"#"+agencia+"#"+conta+"#"+sal+"#"+senha+"#"+tipo+"#"+dinheiro,"Account.txt");
        System.out.printf("Seja Bem Vindo ao nosso banco %s%n sua agencia é %s%n sua conta é %s%n sua senha é %s%n",nome,agencia,conta,ssenha);

    }
    Usuario(int i){
        //login
        i=0;
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
    }


    public static boolean existeusernoArquivo(String user, String filename,int posi) {

        try {
            BufferedReader br = new BufferedReader(

                    new InputStreamReader(new FileInputStream(filename), "UTF-8"));
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

    public static double pegarDinheiro(String filename, String agencia){
        try {
            double dinheiro;
            BufferedReader br = new BufferedReader(

                    new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] us = line.split("\\#");
                if (us[1].equals(agencia)) {
                    br.close();

                    return dinheiro = Double.valueOf(us[6]);
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
        return -1.0;
    }

    public static boolean login(String agencia,String conta,String senha,String filename) {
        Hash hash = Hash.getInstance();
        try {
            BufferedReader br = new BufferedReader(

                    new InputStreamReader(new FileInputStream(filename), "UTF-8"));
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


    public static void main(String[] args) {
       new Usuario();
        new Usuario(0);
    }
}