package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	public int worldX, worldY;
	public double exactX, exactY;
	public int speed;
	
	public BufferedImage idle1,idle2, up1, up2, down1, down2, left1, left2, left3, right1, right2, right3;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	//permet de créer un rectangle abstrait qui va pouvoir stocker des données
	public Rectangle solidArea;
	public boolean collisionOn = false;

}
