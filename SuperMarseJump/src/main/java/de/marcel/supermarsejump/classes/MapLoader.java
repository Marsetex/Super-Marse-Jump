package de.marcel.supermarsejump.classes;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapLoader {

    Image                  block, burg, spikes;
    Image                  hintergrund[] = new Image[4]; // Array f�r Hintergrund-Bilder

    int                    lvlnr;
    int                    mapH, mapW;
    int[][]                mapLayout;

    static int             lx_pos        = 0;           // Variable mit der das Level sich bewegt

    String                 lvlname;
    private BufferedReader reader;

    public MapLoader() {

    }

    public void initMapGegenstaende() {

        BufferedImage bImage1 = null;
        BufferedImage bImage2 = null;
        BufferedImage bImage3 = null;

        try {
            bImage1 = ImageIO.read(new File("block.png"));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        block = bImage1;

        try {
            bImage2 = ImageIO.read(new File("burg.png"));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        burg = bImage2;

        try {
            bImage3 = ImageIO.read(new File("spikes.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        spikes = bImage3;
    }

    public void initHintergrund() {

        for (int i = 1; i <= 3; i++) {
            BufferedImage bImage = null;
            try {
                bImage = ImageIO.read(new File("background" + (i) + ".gif"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            hintergrund[i] = bImage;
        }
    }

    private void pruefeLevel(int Nr) {

        switch (Nr) {
        case 1:
            lvlname = "level_1.txt";
            break;

        case 2:
            lvlname = "level_2.txt";
            break;

        case 3:
            lvlname = "level_3.txt";
            break;

        default:
            lvlname = "level_1.txt";
            break;
        }
    }

    public void mapLesen(int Nr) {

        String zeile = "", map = ""; //Lesestrings

        pruefeLevel(Nr);

        try {
            reader = new BufferedReader(new FileReader(lvlname));

            // Lese alle Zeilen ein und speichere sie in der Variable "map"
            while ((zeile = reader.readLine()) != null) {
                map = map + zeile;
            }

            mapSplitten(map);

        } catch (IOException f) {
            System.err.println("Error 2: " + f);
        }
    }

    public void mapSplitten(String s) {

        String[] maps = s.split("\\,");

        mapW = Integer.parseInt(maps[0]);
        mapH = Integer.parseInt(maps[1]);

        mapLayout = new int[mapW][mapH];
        int x = 0, y = 0;

        for (int i = 2; i < maps.length; i++) {
            mapLayout[x][y] = Integer.parseInt(maps[i]);
            x++;

            if (x >= mapW) {
                x = 0;
                y++;
            }
        }
    }

    public void erhoeheMapNr() {

        Main.derHighscore.berechneScore();

        if (lvlnr >= 3) {

            lvlnr = 3;
            Menu.gewonnen = true;
        } else {

            lvlnr++;
            lx_pos = 0;
            Main.derTimer.starteTimer();
        }
    }

    public void setzeLevel() {

        lvlnr = 1;
    }

    public int gibMapNr() {

        return lvlnr;
    }

    public int[][] gibmapLayout() {

        return mapLayout;
    }

    public void paintMap(Graphics g) {

        int levelX = 0, levelY = 0;
        int appSizeX = 900, appSizeY = 600;

        g.drawImage(hintergrund[lvlnr], 0, 0, appSizeX, appSizeY, null);

        for (int spalte = 0; spalte < mapW; spalte++) {
            for (int zeile = 0; zeile < mapH; zeile++) {

                //Abfrage ob Block zeichnen
                if (mapLayout[spalte][zeile] == 1) g.drawImage(block, levelX - lx_pos, levelY, 50, 50, null);
                // Abfrage ob Spikes zeichnen
                else if (mapLayout[spalte][zeile] == 2) g.drawImage(spikes, levelX - lx_pos, levelY, 50, 50, null);
                // Abfrage ob Burg zeichnen
                else if (mapLayout[spalte][zeile] == 3) g.drawImage(burg, levelX - lx_pos, levelY, null);

                levelY = levelY + 50;

                if (zeile == 11) {
                    levelX = levelX + 50;
                    levelY = 0;
                }
            }
        }

        g.drawString(String.valueOf("Level " + lvlnr), 80, 50);
    }
}
