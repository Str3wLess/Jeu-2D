package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    
    private String lastHorizontalDirection = "right"; // Pour mémoriser gauche/droite

    public Player (KeyHandler keyH, GamePanel gp)
    {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }
    
    public void setDefaultValues()
    {
        x = 100;
        y = 100;
        exactX=x;
        exactY=y;
        speed = 4;
        direction = "idle";
        lastHorizontalDirection = "right"; // Par défaut regarde à droite
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
        if (keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true)
        {
            // GESTION DES DÉPLACEMENTS DIAGONAUX
            if(keyH.upPressed == true && keyH.rightPressed == true)
            {
                direction = "up";
                lastHorizontalDirection = "right";
                exactX += speed * 0.707;  // ← Utilise exactX
                exactY -= speed * 0.707;  // ← Utilise exactY
            }
            else if(keyH.upPressed == true && keyH.leftPressed == true)
            {
                direction = "up";
                lastHorizontalDirection = "left";
                exactX -= speed * 0.707;
                exactY -= speed * 0.707;
            }
            else if(keyH.downPressed == true && keyH.rightPressed == true)
            {
                direction = "down";
                lastHorizontalDirection = "right";
                exactX += speed * 0.707;
                exactY += speed * 0.707;
            }
            else if(keyH.downPressed == true && keyH.leftPressed == true)
            {
                direction = "down";
                lastHorizontalDirection = "left";
                exactX -= speed * 0.707;
                exactY += speed * 0.707;
            }
            // MOUVEMENTS SIMPLES
            else if(keyH.upPressed == true)
            {
                direction = "up";
                exactY -= speed;  // ← Utilise exactY
            }
            else if (keyH.downPressed == true)
            {
                direction = "down";
                exactY += speed;
            }
            else if (keyH.leftPressed == true)
            {
                direction = "left";
                lastHorizontalDirection = "left";
                exactX -= speed;  // ← Utilise exactX
            }
            else if (keyH.rightPressed == true)
            {
                direction = "right";
                lastHorizontalDirection = "right";
                exactX += speed;
            }
            
            // ← AJOUT : Convertir les positions précises en entiers pour l'affichage
            x = (int)exactX;
            y = (int)exactY;

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
            // ← MODIFICATION : Utilise lastHorizontalDirection
            if (lastHorizontalDirection.equals("left"))
            {
                // Animation vers la gauche pour monter
                if (spriteNum == 1)
                {
                    image = left1;
                }
                else if (spriteNum == 2)
                {
                    image = left2;
                }
                else if (spriteNum == 3)
                {
                    image = left3;
                }
            }
            else // right
            {
                // Animation vers la droite pour monter
                if (spriteNum == 1)
                {
                    image = right1;
                }
                else if (spriteNum == 2)
                {
                    image = right2;
                }
                else if (spriteNum == 3)
                {
                    image = right3;
                }
            }
            break;

        case "down":
            // ← MODIFICATION : Utilise lastHorizontalDirection
            if (lastHorizontalDirection.equals("left"))
            {
                // Animation vers la gauche pour descendre
                if (spriteNum == 1)
                {
                    image = left1;
                }
                else if (spriteNum == 2)
                {
                    image = left2;
                }
                else if (spriteNum == 3)
                {
                    image = left3;
                }
            }
            else // right
            {
                // Animation vers la droite pour descendre
                if (spriteNum == 1)
                {
                    image = right1;
                }
                else if (spriteNum == 2)
                {
                    image = right2;
                }
                else if (spriteNum == 3)
                {
                    image = right3;
                }
            }
            break;

        case "left":
            if (spriteNum == 1)
            {
                image = left1;
            }
            else if (spriteNum == 2)
            {
                image = left2;
            }
            else if (spriteNum == 3)
            {
                image = left3;
            }
            break;

        case "right":
            if (spriteNum == 1)
            {
                image = right1;
            }
            else if (spriteNum == 2)
            {
                image = right2;
            }
            else if (spriteNum == 3)
            {
                image = right3;
            }
            break;

        case "idle":
        default:
            if (lastHorizontalDirection.equals("left"))
            {
                image = idle1; // idle vers la gauche
            }
            else // right ou par défaut
            {
                image = idle2; // idle vers la droite
            }
            break;
        }
        
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}