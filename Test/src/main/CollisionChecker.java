package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int maxCol = gp.tileM.mapTileNum.length - 1;
		int maxRow = gp.tileM.mapTileNum[0].length - 1;

		int tileNum1, tileNum2;

		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			if (entityTopRow < 0) { entity.collisionOn = true; break; }
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}
		break;

		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			if (entityBottomRow > maxRow) { entity.collisionOn = true; break; }
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}
		break;

		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			if (entityLeftCol < 0) { entity.collisionOn = true; break; }
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}
		break;

		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			if (entityRightCol > maxCol) { entity.collisionOn = true; break; }
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}
		break;

		// DIAGONAUX
		case "upRight":
		{
			int checkTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			int checkRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			if (checkTopRow < 0 || checkRightCol > maxCol) { entity.collisionOn = true; break; }
			// Vérifie vers le haut
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][checkTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][checkTopRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true; break;
			}
			// Vérifie vers la droite
			tileNum1 = gp.tileM.mapTileNum[checkRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[checkRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
		}
		break;

		case "upLeft":
		{
			int checkTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			int checkLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			if (checkTopRow < 0 || checkLeftCol < 0) { entity.collisionOn = true; break; }
			// Vérifie vers le haut
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][checkTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][checkTopRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true; break;
			}
			// Vérifie vers la gauche
			tileNum1 = gp.tileM.mapTileNum[checkLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[checkLeftCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
		}
		break;

		case "downRight":
		{
			int checkBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			int checkRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			if (checkBottomRow > maxRow || checkRightCol > maxCol) { entity.collisionOn = true; break; }
			// Vérifie vers le bas
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][checkBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][checkBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true; break;
			}
			// Vérifie vers la droite
			tileNum1 = gp.tileM.mapTileNum[checkRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[checkRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
		}
		break;

		case "downLeft":
		{
			int checkBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			int checkLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			if (checkBottomRow > maxRow || checkLeftCol < 0) { entity.collisionOn = true; break; }
			// Vérifie vers le bas
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][checkBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][checkBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true; break;
			}
			// Vérifie vers la gauche
			tileNum1 = gp.tileM.mapTileNum[checkLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[checkLeftCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
		}
		break;
		}
	}
}