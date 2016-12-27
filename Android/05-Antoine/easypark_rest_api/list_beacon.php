<?php
/**
 * Created by PhpStorm.
 * User: antoine
 * Date: 12/27/16
 * Time: 12:32 AM
 */

require_once 'include/DB_Fonctions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['minLat']) && isset($_POST['maxLat']) && isset($_POST['minLng']) && isset($_POST['maxLng']) && isset($_POST['other'])) {

    // receiving the post params
    $minLat = $_POST['minLat'];
    $maxLat = $_POST['maxLat'];
    $minLng = $_POST['minLng'];
    $maxLng = $_POST['maxLng'];

    // Get the list of beacon
    $beacon = $db->getBeaconByLocation($minLat, $maxLat, $minLng, $maxLng);

    if ($beacon != false) {
        $response["type"] = "MultiPoint";
        $response["coordinates"] = $beacon;
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Wrong parameters. Please try again!";
        echo json_encode($response);
    }
}
?>