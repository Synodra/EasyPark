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
    /*$minLat = doubleval($_POST['minLat']);
    $maxLat = doubleval($_POST['maxLat']);
    $minLng = doubleval($_POST['minLng']);
    $maxLng = doubleval($_POST['maxLng']);*/

    $minLat = $_POST['minLat'];
    $maxLat = $_POST['maxLat'];
    $minLng = $_POST['minLng'];
    $maxLng = $_POST['maxLng'];

    // Get the list of beacon
    $beacon = $db->getBeaconByLocation($minLat, $maxLat, $minLng, $maxLng);

    if ($beacon != false) {
        // GeoJson entete
        $response["type"] = "MultiPoint";
        // Creation d'un array pour regrouper tous les balises
        $list = array();
        // regroupement de toutes les balises dans un array
        for ($i=0; $i<sizeof($beacon); $i++) {
            $device = $beacon[$i];
            $list[] = array($device[2], $device[1]);
        }
        // inclusion de l'array dans le JSON
        $response["coordinates"] = $list;
        // création du JSON
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "No beacon found. Please try again!";
        echo json_encode($response);
    }
} else {
    // user is not found with the credentials
    $response["error"] = TRUE;
    $response["error_msg"] = "Wrong parameters. Please try again!";
    echo json_encode($response);
}
?>