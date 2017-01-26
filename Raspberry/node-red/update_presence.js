// name: update_presence
// outputs: 1
msg.method="POST";
msg.url="synodra.ddns.net/easypark_rest_api/admin_beacon.php";
msg.headers={'content-type':"application/x-www-form-urlencoded"};
msg.payload=    {
                    action:"presence",
                    beacon_id:msg.payload[1].wid,
                    node_ps:msg.payload[0].ps,
                }
return msg;