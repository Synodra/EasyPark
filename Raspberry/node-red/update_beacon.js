msg.method="POST";
msg.url="synodra.ddns.net/easypark_rest_api/update_beacon.php";
msg.headers={'content-type':"application/x-www-form-urlencoded"};
msg.payload=    {
                    beacon_id:msg.payload[1].wid,
                    node_ps:msg.payload[0].ps,
                    node_bat:msg.payload[0].bat
                }
return msg;