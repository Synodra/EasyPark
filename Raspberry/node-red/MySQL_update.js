// name: MySQL_update
// outputs: 1
var newMsg = {};

newMsg.topic = 'UPDATE ESIGELEC SET node_ps='+msg.payload[0].ps+', node_bat='+msg.payload[0].bat+' WHERE node_id= "'+msg.payload[1].wid+'"';
return newMsg;