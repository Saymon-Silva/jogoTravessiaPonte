public class OpcaoNaoEncontrada extends Exception{
    public OpcaoNaoEncontrada(int opcao){
        super("Insira uma opcao valida " +
                "\nOpção : " + opcao + " não encontrada");
    }
}
