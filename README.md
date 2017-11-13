jhipster-weekend

*** Autor ***
    Simon LEDOUX-LEVIN
    Alan MARZIN
*************


ATTENTION : #DockerDuPauvre

 
0. Obtenir le repository Github : https://github.com/simon-ldlv/jhipster-weekend 

1. Télécharger l'image jhipster avec la commande : 
	docker image pull jhipster/jhipster:master

2. Lancez ensuite la commande suivante en remplaçant le "pathToTheProject" par la racine du repo GIT : 
	docker container run --name jhipsterDeploy -v <PathToTheRepo>/jhipster-weekend-master/jhipster:/home/jhipster/app -v ~/.m2:/home/jhipster/.m2 -p 8080:8080 -p 9000:9000 -p 3001:3001 -d -t jhipster/jhipster

3. Démarrer le container avec : 
	sudo docker container start jhipsterDeploy

4. Connect to the container
    	sudo docker container exec -it jhipster bash

5. Pour générer l'arborescence du projet, lancez la commande : 
	jhipster
	ATTENTION : N'écraser aucun fichier. Répondez "N" pour toutes les questions ! Attendez la fin de la génération.

6. Compilez la web app client : 
    	npm start

7. Compilez and executez le serveur : 
    	./mvnw

8. Allez à sur votre navigateur à l'adresse : http://localhost:8080

9. Connectez vous avec l'ID 'admin' and le mot de passe 'admin'

10. Allez dans Administration -> Database 
   Cliquez on "Connect"
   Copiez le contenu du fichier "<PathToTheRepo>/jhipster-weekend-master/jhipster/src/data_weather.sql"
   Collez le contenu dans la fenêtre d'administration de la bdd

11. Allez ensuite à Administration -> Update Weather

12. Attendez quelque instant pendant la mise à jour de la météo (ne marche pas le dimanche)

13. Déconnectez vous du compte administrateur 

14. Vous pouvez dorénavant vous connectez en mode utilisateur avec l'id 'user' et le mot de passe 'user'

15. Allez à Entities -> Pratique
    Ajoutez vous quelques activité 

16. Allez à Entities -> Ville Préférées
    Ajoutez quelque ville à vos ville préférées

17. Vous pouvez enfin profitez de votre week end en allant à "Get My Week-end!"

18. Vous pouvez voir ce que vous pouvez faire le week end porchain (seulement le samedi).


    
