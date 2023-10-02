import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static Boolean jogoGanho = false;
    private static Personagem[] canoa = new Personagem[2];
    static Personagem querubim1 = new Querubim();
    static Personagem querubim2 = new Querubim();
    static Personagem querubim3 = new Querubim();
    static Personagem canibal1 = new Canibal();
    static Personagem canibal2 = new Canibal();
    static Personagem canibal3 = new Canibal();

    public static void main(String[] args) throws OpcaoNaoEncontrada {
        Personagem.personagensLadoInicio.add(querubim1);
        Personagem.personagensLadoInicio.add(querubim2);
        Personagem.personagensLadoInicio.add(querubim3);
        Personagem.personagensLadoInicio.add(canibal1);
        Personagem.personagensLadoInicio.add(canibal2);
        Personagem.personagensLadoInicio.add(canibal3);
        System.out.println(regras() + "\n\n");

        menu();
    }

    public static void menu() throws OpcaoNaoEncontrada {
        jogoGanho = validarVitoria();
        int opcao;
        do {
            System.out.println("Personagens para atravessar :");
            System.out.println(lerTodosLadoInicio());
            System.out.println("Personagens na canoa:");
            System.out.println(canoa + "\n");
            System.out.println("""
                    MENU
                    0 - Desistir
                    1 - Colocar Querubim
                    2 - Colocar Canibal
                    3 - Atravessar
                    4 - Remover Querubim
                    5 - Remover Canibal
                    """);
            opcao = sc.nextInt();
            switch (opcao) {
                case 0 -> {
                    System.out.println("Adeus!");
                }
                case 1, 2 -> {
                    if (validarCanoa()) {
                        if (validarEscolha(1)) {
                            if (canoa[0] == null && canoa[1] != null) {
                                canoa[0] = escolherPersonagem(opcao);
                            } else if (canoa[1] == null && canoa[0] != null) {
                                canoa[1] = escolherPersonagem(opcao);
                            } else {
                                canoa[1] = escolherPersonagem(opcao);
                            }
                        }
                    }
                }
                case 3 -> {
                    if (validarCanoaTravessia()) {
                        if (travessia(canoa)) {
                            voltar();
                        }
                    }
                }
                case 4, 5 -> {
                    Personagem personagem = canoa[0];
                    Personagem personagem1 = canoa[1];
                    if (opcao == 4) {
                        if (personagem1 instanceof Querubim) {
                            canoa[1] = null;
                        } else if (personagem instanceof Querubim) {
                            canoa[0] = null;
                        } else {
                            System.out.println("Não ha nehum querubim na canoa!");
                            System.out.println("Personagens na canoa:");
                            System.out.println(canoa + "\n");
                        }
                    } else {
                        if (personagem instanceof Canibal) {
                            canoa[0] = null;
                        } else if (personagem1 instanceof Canibal) {
                            canoa[1] = null;
                        } else {
                            System.out.println("Não ha nehum canibal na canoa!");
                            System.out.println("Personagens na canoa:");
                            System.out.println(canoa + "\n");
                        }
                    }
                }
                default -> {
                    throw new OpcaoNaoEncontrada(opcao);
                }
            }
        } while (!jogoGanho || opcao != 0);
    }

    public static String regras() {
        return "Regras :\n" + "1) Escolha até duas pessoas para cada travessia, clicando sobre cada uma delas.\n" +
                "2) Escolhida(s) a(s) pessoa(s), clique na balsa para fazer a travessia.\n" +
                "3) Para substituir uma pessoa já escolhida para fazer a travessia, basta clicar na pessoa a ser substituída e, em seguida, clicar na pessoa correspondente à nova escolha.\n" +
                "4) A travessia poderá ser feita nos dois sentidos: da direita para a esquerda e da esquerda para direita.\n" +
                "5) Quando a balsa chega a uma margem, os elementos que nela estão são contados como estando naquela margem.\n" +
                "6) A balsa não atravessa sozinha, é necessário que alguém esteja nela.";
    }

    public static boolean travessia(Personagem[] canoa) {//ida
        Personagem personagem0 = canoa[0];
        Personagem personagem1 = canoa[1];
        canoa[0] = null;
        canoa[1] = null;

        if (validacaoNumeroPersonagensAmbosLados() == false) {
            Personagem.personagensLadoAtravessado.remove(personagem0);
            Personagem.personagensLadoAtravessado.remove(personagem1);
            Personagem.personagensLadoInicio.add(personagem0);
            Personagem.personagensLadoInicio.add(personagem1);
            return false;
        }
        System.out.println("Personagens que fizeram a travessia :");
        System.out.println(Personagem.personagensLadoAtravessado);
        if (validarVitoria()) {
            System.out.println("Você concluiu o desafio!!!");
            jogoGanho = validarVitoria();
        }
        return true;
    }

    public static boolean retorno(Personagem[] canoa) {//volta
        Personagem personagem0 = canoa[0];
        Personagem personagem1 = canoa[1];
        canoa[0] = null;
        canoa[1] = null;
        Personagem.personagensLadoAtravessado.remove(personagem0);
        Personagem.personagensLadoAtravessado.remove(personagem1);
        Personagem.personagensLadoInicio.add(personagem0);
        Personagem.personagensLadoInicio.add(personagem1);
        if (validacaoNumeroPersonagensAmbosLados() == false) {
            Personagem.personagensLadoInicio.remove(personagem0);
            Personagem.personagensLadoInicio.remove(personagem1);
            Personagem.personagensLadoAtravessado.add(personagem0);
            Personagem.personagensLadoAtravessado.add(personagem1);
            return false;
        }
        return true;
    }

    public static void voltar() throws OpcaoNaoEncontrada {
        int opcao;
        do {
            System.out.println("Personagens para voltar :");
            System.out.println(lerTodosLadoAtravessado());
            System.out.println("Personagens na canoa:");
            System.out.println(canoa + "\n");
            System.out.println("""
                    MENU
                    0 - Desistir
                    1 - Colocar Querubim
                    2 - Colocar Canibal
                    3 - Atravessar
                    """);
            opcao = sc.nextInt();
            switch (opcao) {
                case 0 -> {
                    System.out.println("Adeus!");
                }
                case 1, 2 -> {
                    if (validarCanoa()) {
                        if (validarEscolhaRetorno(1)) {
                            if (canoa[0] == null && canoa[1] != null) {
                                canoa[0] = escolherPersonagemRetorno(opcao);
                            } else if (canoa[1] == null && canoa[0] != null) {
                                canoa[1] = escolherPersonagemRetorno(opcao);
                            } else {
                                canoa[1] = escolherPersonagemRetorno(opcao);
                            }
                        }
                    }
                }
                case 3 -> {
                    if (validarCanoaTravessia()) {
                        if (retorno(canoa)) {
                            menu();
                        }
                    }
                }
                default -> {
                    throw new OpcaoNaoEncontrada(opcao);
                }
            }
        } while (opcao != 0);
    }

    public static String lerTodosLadoInicio() {
        String dados = "";
        for (Personagem personagemFor : Personagem.personagensLadoInicio) {
            dados += personagemFor + "\n";
        }
        return dados;
    }

    public static String lerTodosLadoAtravessado() {
        String dados = "";
        for (Personagem personagemFor : Personagem.personagensLadoAtravessado) {
            dados += personagemFor + "\n";
        }
        return dados;
    }

    public static boolean validarCanoa() {
        if (canoa[1] == null && canoa[2] == null) {
            return true;
        } else if (canoa[1] == null || canoa[2] == null) {
            return true;
        }
        return false;//colocar exceao aqui
    }

    public static boolean validarCanoaTravessia() {
        if (canoa[1] != null && canoa[2] != null) {
            return true;
        } else if (canoa[1] != null || canoa[2] != null) {
            return true;
        }
        return false;//colocar exceao aqui
    }

    public static boolean validarEscolha(int escolha) {
        int numeroQuerubim = 0;
        int numeroCanibal = 0;

        if (escolha == 1) {
            for (Personagem personagemFor : Personagem.personagensLadoInicio) {
                if (personagemFor instanceof Querubim) {
                    numeroQuerubim++;
                }
            }
            if (numeroQuerubim > 0) {
                return true;
            }
        }
        if (escolha == 2) {
            for (Personagem personagemFor : Personagem.personagensLadoInicio) {
                if (personagemFor instanceof Canibal) {
                    numeroCanibal++;
                }
            }
            if (numeroCanibal > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean validarEscolhaRetorno(int escolha) {
        int numeroQuerubim = 0;
        int numeroCanibal = 0;

        if (escolha == 1) {
            for (Personagem personagemFor : Personagem.personagensLadoAtravessado) {
                if (personagemFor instanceof Querubim) {
                    numeroQuerubim++;
                }
            }
            if (numeroQuerubim > 0) {
                return true;
            }
        }
        if (escolha == 2) {
            for (Personagem personagemFor : Personagem.personagensLadoAtravessado) {
                if (personagemFor instanceof Canibal) {
                    numeroCanibal++;
                }
            }
            if (numeroCanibal > 0) {
                return true;
            }
        }
        return false;
    }

    public static Personagem escolherPersonagem(int escolha) {
        if (escolha == 1) {
            for (Personagem personagemFor : Personagem.personagensLadoInicio) {
                if (personagemFor instanceof Querubim) {
                    return personagemFor;
                }
            }
        }
        if (escolha == 2) {
            for (Personagem personagemFor : Personagem.personagensLadoInicio) {
                if (personagemFor instanceof Canibal) {
                    return personagemFor;
                }
            }
        }
        return null;
    }

    public static Personagem escolherPersonagemRetorno(int escolha) {
        if (escolha == 1) {
            for (Personagem personagemFor : Personagem.personagensLadoAtravessado) {
                if (personagemFor instanceof Querubim) {
                    return personagemFor;
                }
            }
        }
        if (escolha == 2) {
            for (Personagem personagemFor : Personagem.personagensLadoAtravessado) {
                if (personagemFor instanceof Canibal) {
                    return personagemFor;
                }
            }
        }
        return null;
    }

    public static boolean validacaoNumeroPersonagensAmbosLados() {
        int numeroQuerubimLadoTravessado = 0;
        int numeroQuerubimLadoInicio = 0;
        int numeroCanibalLadoTravessado = 0;
        int numeroCanibalLadoInicio = 0;
        for (Personagem personagemFor : Personagem.personagensLadoInicio) {
            if (personagemFor instanceof Querubim) {
                numeroQuerubimLadoInicio++;
            } else {
                numeroCanibalLadoInicio++;
            }
        }
        for (Personagem personagemFor : Personagem.personagensLadoAtravessado) {
            if (personagemFor instanceof Querubim) {
                numeroQuerubimLadoTravessado++;
            } else {
                numeroCanibalLadoTravessado++;
            }
        }
        if (numeroQuerubimLadoInicio > numeroCanibalLadoInicio && numeroQuerubimLadoTravessado > numeroCanibalLadoTravessado) {
            return true;
        }
        return false;
    }

    public static boolean validarVitoria() {
        int numeroPersonagensPassados = 0;
        for (Personagem personagemFor : Personagem.personagensLadoAtravessado) {
            numeroPersonagensPassados++;
        }
        if (numeroPersonagensPassados == 6) {
            return true;
        }
        return false;
    }
}
