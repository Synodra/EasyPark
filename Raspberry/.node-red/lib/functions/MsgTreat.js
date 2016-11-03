// name: MsgTreat
// outputs: 1
//slpit the msg
var FT, NB, SID, WID, FSeq, S1, S2, S3, S4;
var PS, MF, TEMP, BAT;
var outputMsgs = {};
var sensors = msg.payload.split("#");
var i = 0;

for (var w in sensors) {
    switch(i)
    {
        case 0:
            FT = sensors[w];
            break;
        case 1:
            SID = sensors[w];
            break;
        case 2:
            WID = sensors[w];
            break;
        case 3:
            FSeq = sensors[w];
            break;
        case 4:
            S1 = sensors[w];
            PS = S1.split(":")[1];
            break;
        case 5:
            S2 = sensors[w];
            MF = S2.split(":")[1];
            break;
        case 6:
            S3 = sensors[w];
            TEMP = S3.split(":")[1];
            break;
        case 7:
            S4 = sensors[w];
            BAT = S4.split(":")[1];
            break;
        default:
    }
    i++;
}

    outputMsgs.payload = {WID, PS, MF, TEMP, BAT};

return [outputMsgs];