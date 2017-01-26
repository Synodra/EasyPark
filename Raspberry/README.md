# EasyPark_Raspberry Pi 3
ESIGELEC - PING 14 - 2016/2017
authors :

- Dingjie MA

Description:
Fichiers de configuration de la raspberry. Les éléments requise pour la raspberry:

1. node-red avec:
	* node-red-contrib-influxdb
	* node-red-node-mysql

2. Mosquitto le MQTT serveur, remote serveur: test.mosquitto.org. Avec le topic: EasyPark/1
3. Influxdb la base de donnée pour enregistrer les logs des données.
4. Grafana la visualisation de base de donnée Influxdb:
![alt tag](https://github.com/mdj2812/EasyPark/blob/master/Raspberry/Grafana_node1.png)
5. MySQL la base de donnée static. Il enregistre les informations de chaque Sensor Node (node_id, latitude, longitude, status, battery). Et il va renouveller son état et la batterie dès qu'une nouvelle message arrive.
D'ailleur, il sert à la base de donnée pour le site web d'administration.
6. phpMyAdmin l'application Web de gestion pour MySQL:
![alt tag](https://github.com/mdj2812/EasyPark/blob/master/Raspberry/phpMyAdmin_MySQL_easypark_nodes.png)
7. Apache2, PHP5. Ils sont nécessaires pour le site web. Le site contient les informations de chaque node et une lien vers le dashboard de Grafana du node log respective.
![alt tag](https://github.com/mdj2812/EasyPark/blob/master/Raspberry/Admin_Site.png)