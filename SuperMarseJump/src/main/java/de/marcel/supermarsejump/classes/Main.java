package de.marcel.supermarsejump.classes;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class Main extends Applet implements Runnable {

	private static final long serialVersionUID = -2865477035409693342L;

	Image dbImage;
	Graphics dbg;

	AudioClip spiel_song;

	static Boolean aktiv = true, neustart = true;

	int appSizeX = 900, appSizeY = 600;

	Font font = new Font("Arial", Font.PLAIN, 20);

	static Character derCharacter = new Character();
	static MapLoader derMapLoader = new MapLoader();
	static Leben dieLeben = new Leben();
	static Timer derTimer = new Timer();
	static Highscore derHighscore = new Highscore();
	static Menu dasMenu = new Menu();
	Listener derListener = new Listener();
	Abfragen dieAbfrage = new Abfragen();

	@Override
	public void init() {

		this.setLayout(null);
		this.setSize(appSizeX, appSizeY);

		// Init der drei Listener
		addKeyListener(derListener);
		addMouseListener(derListener);
		addMouseMotionListener(derListener);

		// Layout f�r den Highscore
		derHighscore.initHighscore();

		// Bilder f�r das Men�
		dasMenu.initMenuBilder();

		// Rollover Bilder f�r das Men�
		dasMenu.initRollover();

		// Bilder f�r das Spiel
		derMapLoader.initHintergrund();
		derMapLoader.initMapGegenstaende();
		dieLeben.initLebenicon();
		derCharacter.initAnimationen();

		// Init. Audio f�r das Spiel
		spiel_song = getAudioClip(getCodeBase(), "spiel.wav");
		spiel_song.loop();
	}

	@Override
	public void start() {

		Thread th = new Thread(this);
		th.start();
	}

	@Override
	public void run() {

		while (true) {

			// Der Timer wird nur reduziert, wenn das Spiel gestartet ist
			if (Menu.start == true) {

				derTimer.reduziereTimer();
			}

			try {

				Thread.sleep(10); // Stoppen des Threads (in ms)
			} catch (InterruptedException ex) {

			}

			repaint();
		}
	}

	@Override
	public void update(Graphics g) {

		// Initialisierung des DoubleBuffers
		if (dbImage == null) {

			dbImage = createImage(this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics();
		}

		// Bildschirm im Hintergrund l�schen
		dbg.setColor(getBackground());
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

		// Auf gel�schten Hintergrund Vordergrund zeichnen
		dbg.setColor(getForeground());
		paint(dbg);

		// Nun fertig gezeichnetes Bild Offscreen auf dem richtigen Bildschirm
		// anzeigen
		g.drawImage(dbImage, 0, 0, this);
	}

	private void verarbeiteTasten() {

		// Esc gedr�ckt
		if (derListener.isPressed(5)) {

			dasMenu.pressedESC();
		}

		// Leertaste gedr�ckt
		else if (derListener.isPressed(4)) {

			derListener.jump();
		}

		// Pfeil-Taste (Links) gedr�ckt
		else if (derListener.isPressed(0)) {

			derCharacter.setzeSpeed(-4);
			derCharacter.starteAnimation();
			Character.links = true;
			derCharacter.berechneGeschwindigkeit();
		}

		// Pfeil-Taste (Rechts) gedr�ckt
		else if (derListener.isPressed(1)) {

			derCharacter.setzeSpeed(4);
			derCharacter.starteAnimation();
			Character.links = false;
			derCharacter.berechneGeschwindigkeit();
		}
	}

	@Override
	public void paint(Graphics g) {

		// Men� aktiv
		if (Menu.menu == true) {

			verarbeiteTasten(); // aktiviere Tasten
			remove(Highscore.AusgabeHighscore);

			if (Menu.start_over == true) // zeichne Start_Over
				dasMenu.paintRollover(g, 0);

			else if (Menu.highscore_over == true) // zeichne Highscore_Over
				dasMenu.paintRollover(g, 1);

			else if (Menu.steuerung_over == true) // zeichne Steuerung_Over
				dasMenu.paintRollover(g, 2);

			else if (Menu.credits_over == true) // zeichne Credits_Over
				dasMenu.paintRollover(g, 3);

			else
				dasMenu.paintMenu(g, 0); // zeichne normales Men�_Bild

			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("Version 1.0.1", 25, 580);
			g.drawString("(c) Marse Development", 675, 580);
		}

		// Spiel gestartet
		if (Menu.start == true) {

			verarbeiteTasten();
			remove(Highscore.AusgabeHighscore);
			g.setColor(Color.GRAY);

			if (neustart == true) {

				dieAbfrage.Neustart();
			}

			dieAbfrage.Bordererreicht();

			// Methoden um Level zu laden werden aufgerufen
			int lvlnr = derMapLoader.gibMapNr();
			derMapLoader.mapLesen(lvlnr);
			derMapLoader.paintMap(g);

			// Methoden die �berpr�femn ob sich Mario bewegt
			derCharacter.testeBewegung();
			derCharacter.paintAnimation(g);

			// zeichne Timer
			derTimer.paintTimer(g);

			// zeichne Score
			int aktScore = derHighscore.gibScore();
			g.drawString(String.valueOf("Score: " + aktScore), 400, 50);

			// zeichne Leben
			dieLeben.paintLebenicon(g);
		}

		// Game Over
		if (Menu.gameover == true) {

			verarbeiteTasten();
			MapLoader.lx_pos = 0;
			g.setColor(Color.YELLOW);

			dasMenu.paintMenu(g, 4);

			if (aktiv == true) {

				derHighscore.UpdateHighscore();

				aktiv = false;
			}

			derHighscore.paintHighscore(g, 400, 355);
		}

		// Gewonnen
		if (Menu.gewonnen == true) {

			verarbeiteTasten();
			MapLoader.lx_pos = 0;
			g.setColor(Color.YELLOW);

			dasMenu.paintMenu(g, 5);

			if (aktiv == true) {

				derHighscore.berechneBonuspunkte();
				derHighscore.UpdateHighscore();

				aktiv = false;
			}

			int anzLeben = dieLeben.gibAnzahlLeben();
			g.drawString(String.valueOf(
					String.valueOf(anzLeben * 500) + " Bonuspunkte (dank " + String.valueOf(anzLeben) + " Leben)"), 400,
					355);

			derHighscore.paintHighscore(g, 400, 410);
		}

		// Highscore aktiv
		if (Menu.highscore == true) {

			verarbeiteTasten();

			dasMenu.paintMenu(g, 1);

			if (aktiv == true) {

				add(Highscore.AusgabeHighscore);
				derHighscore.ausgebenHighscore();

				aktiv = false;
			}
		}

		// Steuerung aktiv
		if (Menu.steuerung == true) {

			verarbeiteTasten();
			remove(Highscore.AusgabeHighscore);

			dasMenu.paintMenu(g, 2);
		}

		// Credits aktiv
		if (Menu.credits == true) {

			verarbeiteTasten();
			remove(Highscore.AusgabeHighscore);

			dasMenu.paintMenu(g, 3);
		}
	}
}
