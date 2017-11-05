/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.fotoherfert4school_admin.others;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author 20110423
 */
public class WasserzeichenRunnable implements Runnable {

  private File file;
  private File file_hash;
  private String kennzeichnung;
  
  public WasserzeichenRunnable(File file, File file_hash, String kennzeichnung) {
    this.file = file;
    this.file_hash = file_hash;
    this.kennzeichnung = kennzeichnung;
  }

  @Override
  public void run() {
    try {
      //Bild einlesen
      BufferedImage sourceImage = ImageIO.read(this.file_hash);

      //String mit Wasserzeichentext anlegen
      String text = "HERFERT";
      //Höhe und Breite für später instanzieren
      int h = sourceImage.getHeight();
      int w = sourceImage.getWidth();

      //Graphics2D Objekt erzeugen
      BufferedImage newImg = new BufferedImage(w / 4, h / 4, sourceImage.getType());
      Graphics2D g2d2 = (Graphics2D) newImg.createGraphics();
      g2d2.drawImage(sourceImage, 0, 0, w / 4, h / 4, null);
      g2d2.dispose();

      //ImageIO.write(newImg, "jpg", new File("resized.jpg"));
      Graphics2D g2d = (Graphics2D) newImg.createGraphics();

      int divisor = 6;
      int posX = newImg.getWidth() / divisor;
      int posY = (divisor - 1) * newImg.getHeight() / divisor;

      AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
      //Durchsichtigkeit setzen
      g2d.setComposite(ac);
      //Wasserzeichen drehen
      g2d.rotate(Math.toRadians(berechneWinkel((divisor - 1) * newImg.getWidth() / divisor, newImg.getHeight() / divisor, posX, posY)), posX, posY);

      //Schriftfarbe festlegen
      int size = 195;
      g2d.setColor(Color.BLUE);
      //Schriftart und -größe festlegen
      if(newImg.getWidth() > newImg.getHeight()){
        size = 155;
      }
      Font font = new Font("Calibri", Font.BOLD, size);   
      //Wasserzeichen einfügen
      g2d.setFont(font);
      g2d.drawString(text, posX, posY);

      g2d.dispose();

      //speichern im Ursprungsverzeichnis
      String[] help = file.getPath().split("[\\\\/]");
      String path = help[help.length - 1];
      String newpath = file.getPath().substring(0, file.getPath().length() - path.length()) + this.kennzeichnung + path;

      ImageIO.write(newImg, "jpg", new File(newpath));
//      ImageIO.write(newImg, "jpg", new File("H:\\1" + file.getName()));
//                dao.insertArtikel(new Artikel(newpath, artikel.getKnr(), artikel.isMaster(), 
//                                    artikel.getArtikeltyp(), artikel.getKlasse()));
      Logger.getLogger(WasserzeichenRunnable.class.getName()).log(Level.SEVERE, null, "-----------------neues Wasserzeichen bild gespeichert");
    } catch (IOException ex) {
      ex.printStackTrace();
      System.out.println("fehler");
      Logger.getLogger(WasserzeichenRunnable.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public double berechneWinkel(double aX, double aY, double bX, double bY) {
    double cX = aX;
    double cY = bY;
    double gegeKat = aY - cY;
    double anKat = cX - bX;
    return Math.toDegrees(Math.tanh(gegeKat / anKat));
  }

  public static boolean checkIfBlack(File file) throws IOException {
    BufferedImage original = ImageIO.read(file);
    int height = original.getHeight() / 6;
    int width = original.getWidth() / 6;
    BufferedImage img = new BufferedImage(width, height, original.getType());
    Graphics2D g2d2 = (Graphics2D) img.createGraphics();
    g2d2.drawImage(original, 0, 0, width, height, null);
    g2d2.dispose();
    int pixelSchwarz = 0, toleranz = 5;
    for (int i = 0; i < img.getHeight() - 1; i++) {
      for (int j = 0; j < img.getWidth() - 1; j++) {
        int rot = ((img.getRGB(j, i) >> 16) & 0xff);
        int gruen = ((img.getRGB(j, i) >> 8) & 0xff);
        int blau = (img.getRGB(j, i) & 0xff);
        if (rot < toleranz && blau < toleranz && gruen < toleranz) {
          pixelSchwarz++;
        }
      }
    }
    // wenn mehr als 90% der pixel schwarz sind dann gilt es als schwarzes bild
    return pixelSchwarz >= ((img.getHeight() * img.getWidth()) * 0.9);
  }

}
