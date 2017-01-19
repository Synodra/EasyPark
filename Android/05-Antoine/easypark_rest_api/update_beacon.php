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

if (isset($_POST['beacon_id']) && isset($_POST['node_bat'])) {

    $batterie = $_POST['node_bat'];


} elseif (isset($_POST['beacon_id']) && isset($_POST['node_ps'])) {

    $presence = $_POST['node_ps'];


}else {
    // user is not found with the credentials
    $response["error"] = TRUE;
    $response["error_msg"] = "Wrong parameters. Please try again!";
    echo json_encode($response);
}

?>