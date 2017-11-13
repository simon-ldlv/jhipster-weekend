jhipster-weekend

*** Autor ***
    Simon LEDOUX-LEVIN
    Alan MARZIN
*************


ATTENTION : #DockerDuPauvre

1. Première étape : lancez un terminal avec la commande : docker image pull jhipster/jhipster:master
2. Lancez ensuite la commande suivante en remplaçant le "pathToTheProject" par la racine du repo GIT : 
	docker container run --name jhipsterProd -v /home/marzin/Bureau/jhipster-weekend-master/jhipster:/home/jhipster/app -v ~/.m2:/home/jhipster/.m2 -p 8080:8080 -p 9000:9000 -p 3001:3001 -d -t jhipster/jhipster

3. sudo docker container start jhipster

2- Connect to the container
    sudo docker container exec -it jhipster bash

2.5. lancez la commande : jhipster
2.6. N'écraser aucun fichier. Répondez "N" pour toutes les questions.

3- Compile web app client : 
    npm start

4- Compile and run the server : 
    ./mvnw

5- Connect to the browser at : http://localhost:8080

6- Sign in with ID 'admin' and password 'admin'

7- Go to Adminsitration -> Database 
   Click on "Connect"
   Copy and Paste the content of the file "src/data_weather.sql"
   Run the SQL script (the action fills the database)

8- Go to Administration -> Update Weather

9- Wait while the process of update.
   You must to see 0 value in NbError

10- Disconnect the admin account.

11- Sign in with ID "user" and password "user"

12- Go to Entities -> Pratique
    Add some Acitivité in your list 

13- Go to Entities -> Ville Préférées
    Add some Ville in your list

14- Go to "Get My Week-end!"

15- You can see what you can do the next week-end (saturday only).


    
