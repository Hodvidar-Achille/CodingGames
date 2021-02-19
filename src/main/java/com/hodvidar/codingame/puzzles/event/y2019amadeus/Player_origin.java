package com.hodvidar.codingame.puzzles.event.y2019amadeus;

import java.util.Scanner;

public final class Player_origin {

	@SuppressWarnings({ "resource", "unused" })
	public static void main(final String[] args) {
		final Scanner in = new Scanner(System.in);
		final int width = in.nextInt();
		final int height = in.nextInt(); // size of the map

		// game loop
		while (true) {
			final int myScore = in.nextInt(); // Amount of ore delivered
			final int opponentScore = in.nextInt();
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					final String ore = in.next(); // amount of ore or "?" if unknown
					final int hole = in.nextInt(); // 1 if cell has a hole
				}
			}
			final int entityCount = in.nextInt(); // number of entities visible to you
			final int radarCooldown = in.nextInt(); // turns left until a new radar can be requested
			final int trapCooldown = in.nextInt(); // turns left until a new trap can be requested
			for (int i = 0; i < entityCount; i++) {
				final int entityId = in.nextInt(); // unique id of the entity
				final int entityType = in.nextInt(); // 0 for your robot, 1 for other robot, 2 for radar,
				                               // 3 for trap
				final int x = in.nextInt();
				final int y = in.nextInt(); // position of the entity
				final int item = in.nextInt(); // if this entity is a robot, the item it is carrying (-1
				                         // for NONE, 2 for RADAR, 3 for TRAP, 4 for ORE)
			}
			for (int i = 0; i < 5; i++) {

				// Write an action using System.out.println()
				// To debug: System.err.println("Debug messages...");

				System.out.println("WAIT"); // WAIT|MOVE x y|DIG x y|REQUEST item
			}
		}
	}
}

/*
 * Objectif
 * 
 * L'Amadeusium est un cristal rare et précieux, que l'on trouve sous la surface de certaines
 * planètes inhospitalières. Vous devez contrôler les robots de votre société minière, présents à la
 * surface d'une de ces planètes pour extraire le plus possible de cristal. Cependant, vous n'êtes
 * pas les seuls sur le coup ! Livrez plus de cristaux d'Amadeusium que la société minière adverse !
 * Règles
 * 
 * Chaque joueur contrôle une équipe de robots. Les deux équipes démarrent au même endroit sur la
 * zone de jeu, au quartier général. Les robots peuvent faire chacune des actions suivantes : se
 * déplacer, forer la surface, miner un filon d'Amadeusium, mettre en place un radar pour détecter
 * des filons, mettre en place un piège électromagnétique (EMP) pour saboter les robots adverses.
 * 
 * La grille de jeu
 * 
 * Le jeu est joué sur une grille de 30 cases en largeur par 15 en hauteur. Les coordonnées x=0, y=0
 * correspondent à la case en haut à gauche.
 * 
 * La première colonne à gauche de la grille de jeu correspond au quartier général. C'est là que le
 * cristal d'Amadeusium doit être amené une fois extrait et que les objets sont demandés.
 * 
 * Certaines cases cachent des filons d'Amadeusium. Par défaut, les filons ne sont pas visibles des
 * joueurs. On ne peut pas trouver de filon au quartier général.
 * 
 * Les robots peuvent forer la surface et creuser un trou sur n'importe quelle case de la grille de
 * jeu (hors quartier général). Les trous sont visibles des 2 joueurs et n'empêchent pas le
 * mouvement des robots.
 * 
 * Les robots
 * 
 * Chaque robot ne peut transporter qu' 1 seul objet dans son inventaire.
 * 
 * Un robot peut faire les actions suivantes :
 * 
 * demander un objet au quartier général avec la commande REQUEST. se déplacer avec la commande
 * MOVE. utiliser la commande DIG pour creuser un trou et interagir avec. Voici, dans l'ordre, ses
 * effets : Si la case ne contient pas déjà un trou, un nouveau trou est creusé. Si le robot
 * contient un objet, l'objet est enterré dans le trou (et retiré de l'inventaire du robot). Si la
 * case contient un filon d'Amadeusium (et qu'un cristal n'a pas été enterré à l'étape 2), un
 * cristal est extrait du filon et ajouté à l'inventaire du robot. ne rien faire avec la commande
 * WAIT.
 * 
 * Précisions :
 * 
 * Chaque robot ne peut creuser que sur la case qu'il occupe ou les cases adjacentes à sa position.
 * Chaque case a 4 cases adjacentes : en haut, à droite, en bas et à gauche. Si un robot possède un
 * cristal dans son inventaire en arrivant au quartier général, le cristal est automatiquement livré
 * et le joueur marque 1 point. Plusieurs robots peuvent occuper une même case. Les robots ne
 * peuvent pas quitter la grille de jeu. Les inventaires des robots sont invisibles des joueurs
 * adverses.
 * 
 * Les objets
 * 
 * Un cristal d'Amadeusium est considéré comme un objet et doit être livré au quartier général pour
 * marquer 1 point.
 * 
 * Au quartier général, un robot peut demander l'un des 2 objets suivants : un radar avec la
 * commande RADAR ou un piège EMP avec la commande TRAP.
 * 
 * Si un objet est livré suite à une demande d'un robot, cet objet sera de nouveau disponible pour
 * la même équipe de robot après 5 tours.
 * 
 * Un piège EMP enterrée dans un trou ne se déclenche que si un robot utilise la commande DIG sur ce
 * même trou. L'impulsion électromagnétique qui s'en suit détruit tous les robots sur la case
 * correspondante et les 4 cases adjacentes. N'importe quel autre piège EMP se déclenche à son tour
 * si il se trouve sur une des cases touchées par l'impulsion, entraînant une réaction en chaîne.
 * 
 * Un radar enterré dans un trou permet de détecter les filons d'Amadeusium et de connaître la
 * quantité de cristaux disponibles dans chaque, dans un rayon de 4 cases, pour l'équipe qui l'a
 * enterré. Si un robot adverse utilise la commande DIG sur le trou contenant l'un de vos radars, le
 * radar est détruit.
 * 
 * Ordre des actions pour un tour de jeu
 * 
 * Si des commandes DIG déclenchent des piège EMP, ils sont détonés. Les autres commandes DIG sont
 * résolues. Les commandes REQUEST sont résolues. Les temps de recharge des objets sont incrémentés.
 * Les commandes MOVE et WAIT sont résolues. Les cristaux d'Amadeusium sont livrés au quartier
 * général. Il n'est pas nécessaire de détecter un filon d'Amadeusium pour pouvoir extraire un
 * cristal.
 * 
 * Conditions de victoire
 * 
 * Après 200 tours, votre équipe de robot a marqué le plus de points. Vous avez marqué plus de
 * points que votre adversaire, et tous ses robots ont été détruits.
 * 
 * Conditions de défaite
 * 
 * Votre programme ne retourne pas de commande valide dans le temps imparti pour chaque robot de
 * votre équipe, y compris ceux qui sont détruits.
 * 
 * Détails techniques
 * 
 * Un robot peut enterrer un cristal dans un trou. Si la case ne contenait pas déjà un filon, alors
 * un nouveau filon est créé, détectable par les radars. Chaque robot, radar, et piège EMP possède
 * un unique identifiant. Recevoir un objet du quartier général détruira n'importe quel objet que le
 * robot a déjà dans son inventaire. Quand plusieurs robots de la même équipe demandent le même
 * objet, les robots sans objets auront la priorité. Les pièges EMP n'ont pas d'effet sur les radars
 * et les filons. Si un robot transportant un objet est détruit, l'objet est détruit.
 * 
 * Vous pouvez voir le code source de ce jeu sur ce repo GitHub. Données d'entrée Entrée pour le
 * premier tour Ligne 1: deux entiers width et height pour la taille de la grille. La première
 * colonne à gauche de la grille de jeu correspond au quartier général. Entrée pour un tour de jeu
 * Première ligne : Deux entiers
 * 
 * myScore pour le score du joueur. enemyScore pour le score de son adversaire.
 * 
 * Prochaines height lignes : width * 2 variables ore et hole. ore vaut :
 * 
 * ? si la case n'est pas à portée d'un radar que le joueur contrôle. Un entier positif, pour la
 * quantité de cristaux dans le filon. 0, si la case ne cache pas de filon.
 * 
 * hole vaut :
 * 
 * 1 s'il y a un trou sur la case. 0 sinon.
 * 
 * 
 * 
 * Prochaine ligne : 3 entiers
 * 
 * entityCount pour la quantité d'entités : robots, radars et pièges EMP visibles au joueur.
 * radarCooldown pour le nombre de tours jusqu'à ce qu'un nouveau radar soit disponible au quartier
 * général. trapCooldown pour le nombre de tours jusqu'à ce qu'un nouveau piège EMP soit disponible
 * au quartier général.
 * 
 * 
 * 
 * Prochaines entityCount lignes : 5 entiers pour décrire chaque entité
 * 
 * entityId: son unique identifiant. entityType: son type. 0 pour l'un de vos robots 1 pour un robot
 * adverse 2 pour un de vos radars enterrés 3 pour une de vos pièges enterrées x & y: les
 * coordonnées de l'entité. Si l'entité est un robot détruit, ses coordonnées x yvalent -1 -1 item:
 * si l'entité est un robot, l'objet présent dans son inventaire. -1 si l'inventaire est vide 2 pour
 * un radar 3 pour un piège EMP 4 pour un cristal d'Amadeusium
 * 
 * Sortie pour un tour de jeu 5 lignes, pour chaque robot de l'équipe, suivant l'ordre de leurs
 * identifiants, une des commandes suivantes :
 * 
 * WAIT: le robot ne fait rien. MOVE x y: le robot se déplace de 4 cases vers la case (x, y). DIG x
 * y: le robot tente d'enterrer un objet s'il en transporte, et d'extraire un cristal d'un possible
 * filon. Si la case n'est pas adjacente, le robot effectuera une commande MOVE pour se rapprocher
 * de la destination à la place. REQUEST suivi de RADAR ou TRAP: le robot tente de récupérer un
 * objet du quartier général. Si le robot n'est pas sur une case du quartier général, il effectuera
 * la commande MOVE 0 y où y est l'ordonnée du robot.
 * 
 * Il est possible d'ajouter un message à chaque commande (séparé d'un espace) pour qu'il soit
 * affiché au-dessus du robot.
 * 
 * Exemples:
 * 
 * MOVE 8 4 DIG 7 10 let's go mining
 * 
 * Il est nécessaire d'envoyer une commande pour les robots détruits. Elle sera ignorée. Contraintes
 * Temps de réponse pour un tour ≤ 50ms Temps de réponse pour le premier tour ≤ 1000ms
 * 
 * 
 */