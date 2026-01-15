package Practica7;

import java.util.Scanner;

public class PE07_Joc_De_Escacs {
    char[][] tauler = new char [8][8];
    Scanner j = new Scanner(System.in);
    String jugadorBlanc;
    String jugadorNegre;
    String torn = "blanc";
    public static void main(String[] args) {
    PE07_Joc_De_Escacs p = new PE07_Joc_De_Escacs();
    p.iniciarJoc();
}
public void iniciarJoc() {
    demanarJugadors();
    inicialitzarTauler();

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
        System.out.println((8 - i) + " ");
        for (int j = 0; j < 8; j++)
            System.out.println(tauler[i][j] + " ");
        System.out.println(8 - i);
    }
    System.out.println("  a b c d e f g h");
}


}