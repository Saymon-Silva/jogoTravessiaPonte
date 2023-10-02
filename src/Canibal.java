public class Canibal extends Personagem{
    private int id;

    public Canibal(){
        this.id = todos.size() + 1;
        todos.add(this);
    }
    @Override
    public String toString() {
        return "Canibal : " + id;
    }
}
