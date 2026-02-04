package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	public int worldX, worldY;
	public double exactX, exactY;
	public int speed;

	public BufferedImage idle1, idle2;
	public BufferedImage up1, up2, up3;
	public BufferedImage down1, down2, down3;
	public BufferedImage left1, left2, left3;
	public BufferedImage right1, right2, right3;
	// Diagonales
	public BufferedImage upLeft1, upLeft2, upLeft3;
	public BufferedImage upRight1, upRight2, upRight3;
	public BufferedImage downLeft1, downLeft2, downLeft3;
	public BufferedImage downRight1, downRight2, downRight3;
	
	public String direction;

	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	// permet de créer un rectangle abstrait qui va pouvoir stocker des données
	public Rectangle solidArea;
	public boolean collisionOn = false;
}