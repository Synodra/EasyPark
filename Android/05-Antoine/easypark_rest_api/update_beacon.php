<?php
/**
 * Created by PhpStorm.
 * User: antoine
 * Date: 1/19/17
 * Time: 11:48 AM
 */

require_once 'include/DB_Fonctions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

// Update batterie info
if (isset($_POST['beacon_id']) && isset($_POST['node_bat'])) {

    $batterie = $_POST['node_bat'];
    $id = $_POST['beacon_id'];
    $result = $db->updateBeaconBattery($id, $batterie);

    if($result){
        // user failed to store
        $response["error"] = FALSE;
        $response["error_msg"] = "Batterie info has been updated!";
        echo json_encode($response);
    }


}
// Update presence info
elseif (isset($_POST['beacon_id']) && isset($_POST['node_ps'])) {

    $presence = $_POST['node_ps'];
    $id = $_POST['beacon_id'];
    $result = $db->updateBeaconPs($id, $presence);

    if($result){
        // user failed to store
        $response["error"] = FALSE;
        $response["error_msg"] = "Presence info has been updated!";
        echo json_encode($response);
    }

}else {
    // user is not found with the credentials
    $response["error"] = TRUE;
    $response["error_msg"] = "Wrong parameters. Please try again!";
    echo json_encode($response);
}

?>