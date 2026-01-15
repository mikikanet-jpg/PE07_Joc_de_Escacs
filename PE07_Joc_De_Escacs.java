package Practica7;

import java.util.Scanner;

public class PE07_Joc_De_Escacs {
    char[][] tauler = new char [8][8];
    Scanner j = new Scanner(System.in);
    String jugadorBlanc;
    String jugadorNegre;
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

}

public void crearMatriuTauler() {
    tauler = new char[8][8];
}
}