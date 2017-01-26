// name: MySQL_update
// outputs: 1
var newMsg = {};
var date;
date = new Date();
date = date.getUTCFullYear() + '-' +
    ('00' + (date.getMonth()+1)).slice(-2) + '-' +
    ('00' + date.getDate()).slice(-2) + ' ' + 
    ('00' + date.getHours()).slice(-2) + ':' + 
    ('00' + date.getMinutes()).slice(-2) + ':' + 
    ('00' + date.getSeconds()).slice(-2);

newMsg.topic = 'UPDATE ESIGELEC SET node_id='+msg.payload[1].wid+', node_ps='+msg.payload[0].ps+', node_bat='+msg.payload[0].bat+', node_last_update="'+date+'" WHERE node_id= '+msg.payload[1].wid;
return newMsg;