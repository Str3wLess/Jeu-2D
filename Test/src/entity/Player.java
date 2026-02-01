package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    
    private String lastHorizontalDirection = "right";

    public Player (KeyHandler keyH, GamePanel gp)
    {
        this.gp = gp;
        this.keyH = keyH;  
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }
    
    public void setDefaultValues()
    {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        
        exactX = worldX;
        exactY = worldY;
        speed = 4;
        direction = "idle";
        lastHorizontalDirection = "right";
    }

    public void getPlayerImage()
    {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Idle_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Idle_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Idle_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Idle_1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Left_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Left_3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Right_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Right_3.png"));
            idle1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Idle_1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Idle_2.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update()
    {
        // Le if qui vérifie si une touche est pressée (était manquant)
        if (keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true)
        {
            // DÉPLACEMENTS DIAGONAUX
            if(keyH.upPressed == true && keyH.rightPressed == true)
            {
                direction = "upRight";
                lastHorizontalDirection = "right";
            }
            else if(keyH.upPressed == true && keyH.leftPressed == true)
            {
                direction = "upLeft";
                lastHorizontalDirection = "left";
            }
            else if(keyH.downPressed == true && keyH.rightPressed == true)
            {
                direction = "downRight";
                lastHorizontalDirection = "right";
            }
            else if(keyH.downPressed == true && keyH.leftPressed == true)
            {
                direction = "downLeft";
                lastHorizontalDirection = "left";
            }
            // MOUVEMENTS CARDINAUX
            else if(keyH.upPressed == true)
            {
                direction = "up";
            }
            else if (keyH.downPressed == true)
            {
                direction = "down";
            }
            else if (keyH.leftPressed == true)
            {
                direction = "left";
                lastHorizontalDirection = "left";
            }
            else if (keyH.rightPressed == true)
            {
                direction = "right";
                lastHorizontalDirection = "right";
            }

            worldX = (int)exactX;
            worldY = (int)exactY;

            collisionOn = false;
            gp.cChecker.checkTile(this);

            if(collisionOn == false) {
                switch(direction) {
                    case "up":          exactY -= speed; break;
                    case "down":        exactY += speed; break;
                    case "left":        exactX -= speed; break;
                    case "right":       exactX += speed; break;
                    case "upRight":     exactX += speed * 0.707; exactY -= speed * 0.707; break;
                    case "upLeft":      exactX -= speed * 0.707; exactY -= speed * 0.707; break;
                    case "downRight":   exactX += speed * 0.707; exactY += speed * 0.707; break;
                    case "downLeft":    exactX -= speed * 0.707; exactY += speed * 0.707; break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 7)
            {
                if(spriteNum == 1)
                {
                    spriteNum = 2;
                }
                else if(spriteNum == 2)
                {
                    spriteNum = 3;
                }
                else if (spriteNum == 3)
                {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else
        {
            direction = "idle";
            spriteNum = 1;
        }
    }
    
    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        switch(direction) {
        case "up":
            if (lastHorizontalDirection.equals("left"))
            {
                if (spriteNum == 1) image = left1;
                else if (spriteNum == 2) image = left2;
                else if (spriteNum == 3) image = left3;
            }
            else
            {
                if (spriteNum == 1) image = right1;
                else if (spriteNum == 2) image = right2;
                else if (spriteNum == 3) image = right3;
            }
            break;

        case "down":
            if (lastHorizontalDirection.equals("left"))
            {
                if (spriteNum == 1) image = left1;
                else if (spriteNum == 2) image = left2;
                else if (spriteNum == 3) image = left3;
            }
            else
            {
                if (spriteNum == 1) image = right1;
                else if (spriteNum == 2) image = right2;
                else if (spriteNum == 3) image = right3;
            }
            break;

        case "left":
            if (spriteNum == 1) image = left1;
            else if (spriteNum == 2) image = left2;
            else if (spriteNum == 3) image = left3;
            break;

        case "right":
            if (spriteNum == 1) image = right1;
            else if (spriteNum == 2) image = right2;
            else if (spriteNum == 3) image = right3;
            break;

        // Les directions diagonales pour l'animation
        case "upRight":
        case "downRight":
            if (spriteNum == 1) image = right1;
            else if (spriteNum == 2) image = right2;
            else if (spriteNum == 3) image = right3;
            break;

        case "upLeft":
        case "downLeft":
            if (spriteNum == 1) image = left1;
            else if (spriteNum == 2) image = left2;
            else if (spriteNum == 3) image = left3;
            break;

        case "idle":
        default:
            if (lastHorizontalDirection.equals("left"))
            {
                image = idle1;
            }
            else
            {
                image = idle2;
            }
            break;
        }
        
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}