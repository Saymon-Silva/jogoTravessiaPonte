import java.util.ArrayList;

public class Querubim extends Personagem{
    private int id;

    public Querubim(){
        this.id = todos.size() + 1;
        todos.add(this);
    }
    @Override
    public String toString() {
        return "Querubim : " + id;
    }
}
