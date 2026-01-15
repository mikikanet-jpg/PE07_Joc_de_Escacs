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

}

public void crearMatriuTauler() {
    tauler = new char[8][8];
}

public void omplirTaulerBuit() {
    for (int i = 0; i < 8; i++)
        for (int j = 0;j < 8; j++)
            tauler[i][j] = '.';
}


}