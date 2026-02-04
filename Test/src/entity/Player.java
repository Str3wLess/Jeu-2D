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
    
    private boolean spriteIncreasing = true; // Pour savoir si on monte ou descend dans l'animation

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
        speed = 3;
        direction = "up";
    }

    public void getPlayerImage()
    {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_3.png"));
            
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_3.png"));  
            
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_3.png"));
            
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_3.png"));
           
            upLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/player_upLeft_1.png"));
            upLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/player_upLeft_2.png"));
            upLeft3 = ImageIO.read(getClass().getResourceAsStream("/player/player_upLeft_3.png"));
            
            upRight1 = ImageIO.read(getClass().getResourceAsStream("/player/player_upRight_1.png"));
            upRight2 = ImageIO.read(getClass().getResourceAsStream("/player/player_upRight_2.png"));
            upRight3 = ImageIO.read(getClass().getResourceAsStream("/player/player_upRight_3.png"));
            
            downLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/player_downLeft_1.png"));
            downLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/player_downLeft_2.png"));
            downLeft3 = ImageIO.read(getClass().getResourceAsStream("/player/player_downLeft_3.png"));
            
            downRight1 = ImageIO.read(getClass().getResourceAsStream("/player/player_downRight_1.png"));
            downRight2 = ImageIO.read(getClass().getResourceAsStream("/player/player_downRight_2.png"));
            downRight3 = ImageIO.read(getClass().getResourceAsStream("/player/player_downRight_3.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update()
    {
        if (keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true)
        {
            // DÉPLACEMENTS DIAGONAUX
            if(keyH.upPressed == true && keyH.rightPressed == true)
            {
                direction = "upRight";
            }
            else if(keyH.upPressed == true && keyH.leftPressed == true)
            {
                direction = "upLeft";
            }
            else if(keyH.downPressed == true && keyH.rightPressed == true)
            {
                direction = "downRight";
            }
            else if(keyH.downPressed == true && keyH.leftPressed == true)
            {
                direction = "downLeft";
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
            }
            else if (keyH.rightPressed == true)
            {
                direction = "right";
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
            if(spriteCounter > 15)
            {
                // Animation ping-pong : 1 → 2 → 3 → 2 → 1 → 2 → 3...
                if(spriteIncreasing)
                {
                    spriteNum++;
                    if(spriteNum == 3)
                    {
                        spriteIncreasing = false; // On atteint 3, on commence à descendre
                    }
                }
                else
                {
                    spriteNum--;
                    if(spriteNum == 1)
                    {
                        spriteIncreasing = true; // On atteint 1, on recommence à monter
                    }
                }
                spriteCounter = 0;
            }
        }
        else
        {
            direction = "up";
            spriteNum = 1;
            spriteIncreasing = true; // Reset de la direction d'animation
        }
    }
    
    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        switch(direction) {
        case "up":
            if (spriteNum == 1) image = up1;
            else if (spriteNum == 2) image = up2;
            else if (spriteNum == 3) image = up3;
            break;

        case "down":
            if (spriteNum == 1) image = down1;
            else if (spriteNum == 2) image = down2;
            else if (spriteNum == 3) image = down3;
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

        case "upRight":
            if (spriteNum == 1) image = upRight1;
            else if (spriteNum == 2) image = upRight2;
            else if (spriteNum == 3) image = upRight3;
            break;

        case "upLeft":
            if (spriteNum == 1) image = upLeft1;
            else if (spriteNum == 2) image = upLeft2;
            else if (spriteNum == 3) image = upLeft3;
            break;

        case "downRight":
            if (spriteNum == 1) image = downRight1;
            else if (spriteNum == 2) image = downRight2;
            else if (spriteNum == 3) image = downRight3;
            break;

        case "downLeft":
            if (spriteNum == 1) image = downLeft1;
            else if (spriteNum == 2) image = downLeft2;
            else if (spriteNum == 3) image = downLeft3;
            break;
        }
        
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}