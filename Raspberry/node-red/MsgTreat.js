//slpit the msg
var FT, NB, SID, WID, FSeq, S1, S2, S3, S4, S5;
var MODE, PS, MF, MFX, MFY, MFZ, TEMP, BAT;
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
            MODE = S1.split(":")[1];
            break;
        case 5:
            S2 = sensors[w];
            PS = parseInt(S2.split(":")[1]);
            break;
        case 6:
            S3 = sensors[w];
            MF = S3.split(":")[1];
            MFX = parseInt(MF.split(";")[0]);
            MFY = parseInt(MF.split(";")[1]);
            MFZ = parseInt(MF.split(";")[2]);
            break;
        case 7:
            S4 = sensors[w];
            TEMP = parseInt(S4.split(":")[1]);
            break;
        case 8:
            S5 = sensors[w];
            BAT = parseInt(S5.split(":")[1]);
            break;
        default:
    }
    i++;
}

outputMsgs.payload = [  {
                            ps : PS,
                            mfx : MFX,
                            mfy : MFY,
                            mfz : MFZ,
                            temp : TEMP, 
                            bat : BAT,
                        },
                        {
                            wid : WID,
                            mode : MODE
                        }];

outputMsgs.topic = "easypark/esigelec/" + WID;
return [outputMsgs];