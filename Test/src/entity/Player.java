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
            // Permet de changer la position du joueur
            // En java, la bordure haute gauche est X:0 Y:0
            // X augmente vers la droite et Y vers le bas
            if(keyH.upPressed == true)
            {
                direction = "up";
                y -= speed;
            }
            else if (keyH.downPressed == true)
            {
                direction = "down";
                y += speed;
            }
            else if (keyH.leftPressed == true)
            {
                direction = "left";
                lastHorizontalDirection = "left"; // ← MÉMORISER
                x -= speed;
            }
            else if (keyH.rightPressed == true)
            {
                direction = "right";
                lastHorizontalDirection = "right"; // ← MÉMORISER
                x += speed;
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