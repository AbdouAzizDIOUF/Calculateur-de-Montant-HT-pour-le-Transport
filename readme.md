# Calculateur de Montant HT pour le Transport

## Description

Ce projet permet de calculer le montant hors taxe (HT) d'un transport en se basant sur divers paramètres tels que l'expéditeur, le destinataire, 
le nombre de colis, le poids de l'expédition, et les conditions de paiement du transport. 

Le calcul du montant HT s'effectue en fonction des tarifs disponibles dans un fichier XML spécifique (tarif.xml) 
et des taxes définies dans un autre fichier XML (conditiontaxation.xml).

## Prérequis

- Java 11
- Spring 2.7
- Maven

## Installation

1. Clonez le dépôt :
   ```bash
   git clone  https://github.com/AbdouAzizDIOUF/Calculateur-de-Montant-HT-pour-le-Transport.git
   ```
2. Accédez au dossier du projet :
   ```bash
   cd Calculateur-de-Montant-HT-pour-le-Transport
   ```
   
3. Exécuter le projet via Maven: 
   ```bash
   mvn spring-boot:run
   ```

4. ou Utilisez Maven pour construire le projet :
   ```bash
   mvn clean install
   ```
   
## Utilisation

**Après avoir lancé l'application, vous pouvez tester le calculateur de montant HT en accédant au endpoint suivant :**

`http://localhost:8080/calcul-montant?idExp=1&idDest=2&nombreColis=4&poids=6.4&portPaye=true`

Voici une explication des différents paramètres que vous pouvez modifier :

- `idExp` : Identifiant de l'expéditeur
- `idDest` : Identifiant du destinataire
- `nombreColis` : Nombre de colis pour l'expédition
- `poids` : Poids total de l'expédition
- `portPaye` : Indique si le transport est payé (true) ou non (false)

## Contribuer

Les Pull Requests sont les bienvenues. Pour les modifications majeures, veuillez ouvrir d'abord une issue pour discuter de ce que vous aimeriez changer.

