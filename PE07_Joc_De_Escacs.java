package Practica7;

import java.util.Scanner;

public class PE07_Joc_De_Escacs {
    String entrada;
    boolean partidaActiva = true;
    int[] origen;
    int[] desti;
    char[][] tauler = new char [8][8];
    Scanner j = new Scanner(System.in);
    String jugadorBlanc;
    String jugadorNegre;
    String torn = "blanc";
    String[] historial = new String[200];
    int totalMoviments = 0;
    public static void main(String[] args) {
    PE07_Joc_De_Escacs p = new PE07_Joc_De_Escacs();
    p.iniciarJoc();
}
public void iniciarJoc() {
    demanarJugadors();
    inicialitzarTauler();
    imprimirTauler();
    buclePartida();
}

public void demanarJugadors() {
    do{
        System.out.println("Jugador Blanques: ");
        jugadorBlanc = j.nextLine();
    } while (jugadorBlanc.isEmpty());

    do {
        System.out.println("Jugador Negres: ");
        jugadorNegre = j.nextLine();
    } while (jugadorNegre.isEmpty());
}

public void inicialitzarTauler() {
    crearMatriuTauler();
    omplirTaulerBuit();
    colocarPecesNegres();
    colocarPecesBlanques();

}

public void crearMatriuTauler() {
    tauler = new char[8][8];
}

public void omplirTaulerBuit() {
    for (int i = 0; i < 8; i++)
        for (int j = 0;j < 8; j++)
            tauler[i][j] = '.';
}

public void colocarPecesNegres() {
    char[] peces = {'t','c','a','q','k','a','c','t'};
    tauler[0] = peces;
    for (int i = 0; i < 8; i++)
        tauler[1][i] = 'p';
}

public void colocarPecesBlanques() {
    char[] peces = {'T','C','A','Q','K','A','C','T'};
    tauler[7] = peces;
    for (int i = 0; i < 8; i++)
        tauler[6][i] = 'P';
}

public void imprimirTauler() {
    System.out.println("  a b c d e f g h");
    for (int i = 0; i < 8; i++) {
        System.out.print((8 - i) + " ");
        for (int j = 0; j < 8; j++) {
            System.out.print(tauler[i][j] + " ");
        }
        System.out.println(8 - i);
    }
    System.out.println("  a b c d e f g h");
}

public void buclePartida() {

    char[] moviment = new char[5];   // e2 e4
    int llegit;
    int idx;

    do {
        System.out.println("Torn: " + torn);
        System.out.println("Moviment (e2 e4): ");

        idx = 0;

        try {
            while ((llegit = System.in.read()) != '\n' && idx < 5) {
                moviment[idx] = (char) llegit;
                idx++;
            }
        } catch (Exception e) {
            return;
        }

        if (idx != 5 ||
            moviment[0] < 'a' || moviment[0] > 'h' ||
            moviment[1] < '1' || moviment[1] > '8' ||
            moviment[2] != ' ' ||
            moviment[3] < 'a' || moviment[3] > 'h' ||
            moviment[4] < '1' || moviment[4] > '8') {

            System.out.println("Format incorrecte");
            continue;
        }

        origen = convertirCoordenadaChar(moviment[0], moviment[1]);
        desti  = convertirCoordenadaChar(moviment[3], moviment[4]);

        if (validarMoviment(origen, desti)) {
            executarMoviment(origen, desti);
            historial[totalMoviments++] = entrada;
            canviarTorn();
            imprimirTauler();
            mostrarHistorial();
        } else {
            System.out.println("Moviment no permes");
        }

    } while (partidaActiva);
}

public int[] convertirCoordenadaChar(char col, char fila) {
    int f = 8 - (fila - '0');
    int c = col - 'a';
    return new int[]{f, c};
}

public boolean validarMoviment(int[] origen, int[] desti) {

    char p = tauler[origen[0]][origen[1]];
    char dest = tauler[desti[0]][desti[1]];

    if (p == '.') return false;

    // torn correcte
    if (torn.equals("blanc") && p >= 'a' && p <= 'z') return false;
    if (torn.equals("negre") && p >= 'A' && p <= 'Z') return false;

    // no capturar peça pròpia
    if (dest != '.') {
        if (p >= 'A' && p <= 'Z' && dest >= 'A' && dest <= 'Z') return false;
        if (p >= 'a' && p <= 'z' && dest >= 'a' && dest <= 'z') return false;
    }

    // ===== MOVIMENT PEÓ =====
    if (p == 'P') { // peó blanc
        if (origen[1] == desti[1] &&
            dest == '.' &&
            desti[0] == origen[0] - 1)
            return true;

        // dues caselles des de fila inicial
        if (origen[1] == desti[1] &&
            origen[0] == 6 &&
            dest == '.' &&
            tauler[5][origen[1]] == '.' &&
            desti[0] == 4)
            return true;

        return false;
    }

    if (p == 'p') { // peó negre
        if (origen[1] == desti[1] &&
            dest == '.' &&
            desti[0] == origen[0] + 1)
            return true;

        if (origen[1] == desti[1] &&
            origen[0] == 1 &&
            dest == '.' &&
            tauler[2][origen[1]] == '.' &&
            desti[0] == 3)
            return true;

        return false;
    }

    // altres peces (temporal)
    return false;
}

public void executarMoviment(int[] origen, int[] desti) {
    tauler[desti[0]][desti[1]] = tauler[origen[0]][origen[1]];
    tauler[origen[0]][origen[1]] = '.';
}

public void canviarTorn() {
    if (torn.equals("blanc")) torn = "negre";
    else torn = "blanc";
}

public void mostrarHistorial() {
    System.out.println("Historial de moviments:");
    for(int i = 0; i < totalMoviments; i++) {
        System.out.println((i + 1) + ". " + historial[i]);
    }
}
}