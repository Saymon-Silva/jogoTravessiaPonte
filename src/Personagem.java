import java.util.ArrayList;

public class Personagem {
    private int id;
    public static ArrayList<Personagem> todos = new ArrayList<>();
    public static ArrayList<Personagem> personagensLadoInicio = new ArrayList<>();
    public static ArrayList<Personagem> personagensLadoAtravessado = new ArrayList<>();

    public int getId() {
        return id;
    }
}
