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
    String jugadorCian;
    String torn = "blanc";
    String[] historial = new String[200];
    int totalMoviments = 0;
    char[] capturesBlanc = new char[32];
    char[] capturesCian = new char[32];
    int capB = 0, capN = 0;
    public static final String GROC  = "\u001B[33m";
    public static final String RESET   = "\u001B[0m";
    public static final String ROIG    = "\u001B[31m";
    public static final String VERD  = "\u001B[32m";
    public static final String cian = "\u001B[36m";

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
        System.out.println(GROC + "Jugador Blanques: " + RESET);
        jugadorBlanc = j.nextLine();
    } while (jugadorBlanc.isEmpty());

    do {
        System.out.println(GROC + "Jugador Cian: " + RESET);
        jugadorCian = j.nextLine();
    } while (jugadorCian.isEmpty());
}

public void inicialitzarTauler() {
    crearMatriuTauler();
    omplirTaulerBuit();
    colocarPecesCian();
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

public void colocarPecesCian() {
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
    System.out.println();
    System.out.println(cian + "    a   b   c   d   e   f   g   h" + RESET);

    for (int i = 0; i < 8; i++) {
        System.out.println("  +---+---+---+---+---+---+---+---+");
        System.out.print((8 - i) + " |");

        for (int j = 0; j < 8; j++) {
            char p = tauler[i][j];

            if (p >= 'a' && p <= 'z') {
                // piezas negras en cian
                System.out.print(" " + cian + p + RESET + " |");
            } else {
                // blancas o vacío
                System.out.print(" " + p + " |");
            }
        }
        System.out.println(" " + (8 - i));
    }

    System.out.println("  +---+---+---+---+---+---+---+---+");
    System.out.println("    a   b   c   d   e   f   g   h");
    System.out.println();
}


    public void buclePartida() {
        char[] moviment = new char[20];
        int idx, llegit;

        while (partidaActiva) {
            System.out.println(GROC + "Torn: " + torn + RESET);
            System.out.print("Moviment ex. e2 e4 / Escriu Abandonar per rendirte: ");

            idx = 0;
            try {
                while ((llegit = System.in.read()) != '\n' && idx < 20) {
                    moviment[idx++] = (char) llegit;
                }
            } catch (Exception e) { return; }

            String linia = new String(moviment, 0, idx).trim().toLowerCase();


           if (linia.equalsIgnoreCase("Abandonar")) {
            System.out.println(cian + "El jugador " + torn + " abandona." + RESET);
            partidaActiva = false;

            mostrarBotoHistorial();
            mostrarHistorial();
            preguntarTornarJugar();
            }

            if (!formatMovimentValid(linia)) {
                System.out.println(ROIG + "Moviment incorrecte. Usa format: e2 e4 / E2 E4" + RESET);
                continue;
                }

            origen = convertirCoordenadaChar(linia.charAt(0), linia.charAt(1));
            desti  = convertirCoordenadaChar(linia.charAt(3), linia.charAt(4));
            entrada = linia;

            if (validarMoviment(origen, desti)) {
                executarMoviment(origen, desti);
                historial[totalMoviments++] = entrada;
                canviarTorn();
                imprimirTauler();
                mostrarCaptures();
            } else {
                System.out.println(ROIG + "Moviment no permès" + RESET);
            }
        }
    }

public void mostrarBotoHistorial() {
    System.out.println();
    System.out.println(VERD + "Prem ENTER per veure l'historial de moviments..." + RESET);
    j.nextLine();
}

public boolean formatMovimentValid(String linia) {
    if (linia.length() != 5) return false;
    if (linia.charAt(2) != ' ') return false;

    char c1 = linia.charAt(0);
    char f1 = linia.charAt(1);
    char c2 = linia.charAt(3);
    char f2 = linia.charAt(4);

    return c1 >= 'a' && c1 <= 'h' &&
           c2 >= 'a' && c2 <= 'h' &&
           f1 >= '1' && f1 <= '8' &&
           f2 >= '1' && f2 <= '8';
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
    if (torn.equals("cian") && p >= 'A' && p <= 'Z') return false;

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

        //Capturar Peo
        if (Math.abs(origen[1] - desti[1]) == 1 &&
            desti[0] == origen[0] - 1 &&
            dest != '.' && Character.isLowerCase(dest))
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

        if (Math.abs(origen[1] - desti[1]) == 1 &&
            desti[0] == origen[0] + 1 &&
            dest != '.' && Character.isUpperCase(dest))
            return true;
        return false;
    }

    //Cavall
    if (p == 'C' || p == 'c') {
    int df = Math.abs(origen[0] - desti[0]);
    int dc = Math.abs(origen[1] - desti[1]);
    return (df == 2 && dc == 1) || (df == 1 && dc == 2);
    }

    //Rei
    if (p == 'K' || p == 'k') {
    int df = Math.abs(origen[0] - desti[0]);
    int dc = Math.abs(origen[1] - desti[1]);
    return df <= 1 && dc <= 1;
    }

    //Reina
    if (p == 'Q' || p == 'q') {
    int df = desti[0] - origen[0];
    int dc = desti[1] - origen[1];

    if (df != 0 && dc != 0 && Math.abs(df) != Math.abs(dc)) return false;

    df = Integer.compare(df, 0);
    dc = Integer.compare(dc, 0);

    int f = origen[0] + df;
    int c = origen[1] + dc;

    while (f != desti[0] || c != desti[1]) {
        if (tauler[f][c] != '.') return false;
        f += df;
        c += dc;
    }
    return true;
    }

    //Alfil
    if (p == 'A' || p == 'a') {
    int df = desti[0] - origen[0];
    int dc = desti[1] - origen[1];

    if (Math.abs(df) != Math.abs(dc)) return false;

    df = Integer.compare(df, 0);
    dc = Integer.compare(dc, 0);

    int f = origen[0] + df;
    int c = origen[1] + dc;

    while (f != desti[0]) {
        if (tauler[f][c] != '.') return false;
        f += df;
        c += dc;
    }
    return true;
    }

    //Torre
    if (p == 'T' || p == 't') {
    if (origen[0] != desti[0] && origen[1] != desti[1]) return false;

    int df = Integer.compare(desti[0], origen[0]);
    int dc = Integer.compare(desti[1], origen[1]);

    int f = origen[0] + df;
    int c = origen[1] + dc;

    while (f != desti[0] || c != desti[1]) {
        if (tauler[f][c] != '.') return false;
        f += df;
        c += dc;
    }
    return true;
    }
    
    return false;
}

public void executarMoviment(int[] origen, int[] desti) {
    char origenP = tauler[origen[0]][origen[1]];
    char destP = tauler[desti[0]][desti[1]];

    if (destP != '.') {
        if (Character.isUpperCase(destP)) {
            capturesCian[capN++] = destP;
        } else {
            capturesBlanc[capB++] = destP;
        }
    }

    tauler[desti[0]][desti[1]] = origenP;
    tauler[origen[0]][origen[1]] = '.';
}

public void canviarTorn() {
    if (torn.equals("Blanc")) torn = "Cian";
    else torn = "Blanc";
}

public void mostrarHistorial() {
    if (totalMoviments == 0) return;
    System.out.println(VERD + "Historial de moviments:" + RESET);
    for(int i = 0; i < totalMoviments; i++) {
        System.out.println((i + 1) + ". " + historial[i]);
    }
}

public void mostrarCaptures() {
    System.out.println("Captures Blanques: ");
    for (int i = 0; i < capB; i++)
        System.out.print(capturesBlanc[i] + " ");
    System.out.println();

    System.out.print("Captures Cian: ");
    for (int i = 0; i < capN; i++)
        System.out.print(capturesCian[i] + " ");
    System.out.println();
}

public void reiniciarPartida() {
    partidaActiva = true;
    torn = "blanc";

    totalMoviments = 0;
    capB = 0;
    capN = 0;

    historial = new String[200];
    capturesBlanc = new char[32];
    capturesCian = new char[32];
}

public void preguntarTornarJugar() {
    String resposta;

    do {
        System.out.print(GROC + "Vols tornar a jugar? (S/N): " + RESET);
        resposta = j.nextLine().trim().toUpperCase();
    } while (!resposta.equals("S") && !resposta.equals("N"));

    if (resposta.equals("S")) {
        reiniciarPartida();
        iniciarJoc();
    } else {
        System.out.println(cian + "Gràcies per jugar!" + RESET);
        System.exit(0);
    }
}
}