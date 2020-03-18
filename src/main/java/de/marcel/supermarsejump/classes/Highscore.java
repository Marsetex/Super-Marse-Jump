package de.marcel.supermarsejump.classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextArea;

public class Highscore {

    int             iScore, iSpeicher;
    int[]           iaScore          = new int[10];

    static TextArea AusgabeHighscore = new TextArea("", 0, 0, TextArea.SCROLLBARS_NONE);

    Font            font             = new Font("Arial", Font.PLAIN, 20);

    public Highscore() {

    }

    public void initHighscore() {

        StandardHighscore();

        // ARRAY AUSSEHEN
        Color back_color = new Color(20, 43, 107);
        Highscore.AusgabeHighscore.setBounds(145, 150, 600, 320);
        Highscore.AusgabeHighscore.setEditable(false);
        Highscore.AusgabeHighscore.setBackground(back_color);

        // TEXTFARBE
        Highscore.AusgabeHighscore.setFont(font);
        Highscore.AusgabeHighscore.setForeground(Color.WHITE);
    }

    public void StandardHighscore() {

        iaScore[0] = 10000;
        iaScore[1] = 8500;
        iaScore[2] = 7000;
        iaScore[3] = 6000;
        iaScore[4] = 5000;
        iaScore[5] = 4500;
        iaScore[6] = 4000;
        iaScore[7] = 3000;
        iaScore[8] = 1500;
        iaScore[9] = 1337;
    }

    public int[] UpdateHighscore() {

        int i = 9;

        if (iScore > iaScore[i]) {
            iaScore[i] = iScore;

            for (int x = 9; x >= 1; x--) {
                if (iaScore[x] > iaScore[x - 1]) {
                    iSpeicher = iaScore[x - 1];
                    iaScore[x - 1] = iaScore[x];
                    iaScore[x] = iSpeicher;
                }
            }
        }

        return iaScore;
    }

    public void setzeScore() {

        iScore = 0;
    }

    public void ausgebenHighscore() {

        AusgabeHighscore.setText("");

        for (int i = 0; i < 10; i++) {
            AusgabeHighscore.append(i + 1 + ". Platz ......................................... " + iaScore[i] + " Punkte" + '\n');
        }
    }

    public void berechneScore() {

        int timer = Main.derTimer.gibTimer();
        iScore = iScore + (2 * timer);
    }

    public void berechneBonuspunkte() {

        int anzleben = Main.dieLeben.gibAnzahlLeben();
        anzleben = anzleben * 500;

        iScore = iScore + anzleben;
    }

    public int gibScore() {

        return iScore;
    }

    public void paintHighscore(Graphics g, int x, int y) {

        g.drawString(String.valueOf(iScore + " Punkte"), x, y);
    }
}
