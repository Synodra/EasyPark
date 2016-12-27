<?php
/**
 * @author Antoine MAYSLICH
 */
require_once 'include/DB_Fonctions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['beacon_id']) && isset($_POST['longitude']) && isset($_POST['latitude'])) {

    // receiving the post params
    $id = $_POST['beacon_id'];
    $longitude = $_POST['longitude'];
    $latitude = $_POST['latitude'];

    // check if user is already existed with the same email
    if ($db->isBeaconExisted($id, $longitude, $latitude)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "Beacon already existed with " . $id . " or the same GPS coordonate ";
        echo json_encode($response);
    } else {
        // create a new user
        $beacon = $db->storeBeacon($id, $longitude, $latitude);
        if ($beacon) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["beacon"]["unique_id"] = $beacon["unique_id"];
            $response["beacon"]["longitude"] = $beacon["longitude"];
            $response["beacon"]["latitude"] = $beacon["latitude"];
            $response["beacon"]["created_at"] = $beacon["created_at"];
            $response["beacon"]["updated_at"] = $beacon["updated_at"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (beacon_id, longitude, latitude) is missing!";
    echo json_encode($response);
}
?>