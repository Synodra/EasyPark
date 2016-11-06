# EasyPark_Raspberry Pi 3
ESIGELEC - PING 14 - 2016/2017
authors :

- Dingjie MA

Description:
Fichiers de configuration de la raspberry. Les éléments requise pour la raspberry:

1. node-red avec:
	* node-red-contrib-influxdb module
	* node-red-admin module pour hasher les mots de passe

2. Mosquitto le MQTT serveur, remote serveur: test.mosquitto.org. Avec le topic: EasyPark/1
3. Influxdb la base de donnée locale
4. Grafana la visualisation de base de donnée:
![alt tag](https://github.com/mdj2812/EasyPark/blob/master/Raspberry/Grafana_node1.png)
