package de.marcel.supermarsejump.classes;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Leben {

    Image leben;

    int   anzahlLeben;

    public Leben() {

    }

    public void initLebenicon() {

        BufferedImage bImage = null;
        try {
            bImage = ImageIO.read(new File("leben.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        leben = bImage;
    }

    public boolean pruefeLeben() {

        if (anzahlLeben <= 1) {

            return true;
        } else {

            return false;
        }

    }

    public void setzeLebenaufMax() {

        anzahlLeben = 3;
    }

    public void reduziereLeben() {

        anzahlLeben--;
    }

    public int gibAnzahlLeben() {

        return anzahlLeben;
    }

    public void paintLebenicon(Graphics g) {

        g.drawString(String.valueOf(anzahlLeben + "x"), 50, 570);
        g.drawImage(leben, 80, 552, 20, 20, null);
    }
}
