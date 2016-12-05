var newMsg = {};
newMsg.query = { '_id' : msg.payload[1].wid };
newMsg.payload = { $set:
                    {
                        'ps' : msg.payload[0].ps,
                        'bat': msg.payload[0].bat
                    } 
                 };
newMsg.limit = 1;
newMsg.skip = 0;
return newMsg;