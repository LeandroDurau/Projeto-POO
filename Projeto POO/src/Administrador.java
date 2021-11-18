public class Administrador extends Usuario{

    private String nome;

    public Administrador(String senha,String nome) {
        super(senha);
        this.nome = nome;
    }

}
