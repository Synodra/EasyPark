<?php
/**
 * Created by PhpStorm.
 * User: antoine
 * Date: 1/21/17
 * Time: 5:40 PM
 */

require_once 'include/DB_Fonctions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

$method = $_SERVER['REQUEST_METHOD'];
switch($method) {
    case 'POST':
        switch ($_POST['action']) {

            case 'add':
                if (isset($_POST['beacon_id']) && isset($_POST['longitude']) && isset($_POST['latitude'])) {

                    // receiving the post params
                    $id = $_POST['beacon_id'];
                    $longitude = $_POST['longitude'];
                    $latitude = $_POST['latitude'];

                    // check if user is already existed with the same email
                    if ($db->isBeaconExisted($id, $longitude, $latitude)) {
                        // user already existed
                        sendError("Beacon already existed with " . $id . " or the same GPS coordonate ");
                    } else {
                        // create a new user
                        $beacon = $db->addBeacon($id, $longitude, $latitude);
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
                            sendError("Unknown error occurred in registration!");
                        }
                    }
                } else {
                    sendError("Required parameters beacon_id, longitude, latitude is missing!");
                    echo json_encode($response);
                }
                break;
            case 'update':
                if (isset($_POST['beacon_id']) && isset($_POST['longitude']) && isset($_POST['latitude'])) {

                    // receiving the post params
                    $id = $_POST['beacon_id'];
                    $longitude = $_POST['longitude'];
                    $latitude = $_POST['latitude'];

                    // check if user is already existed with the same email
                    if (!$db->isBeaconExisted($id, $longitude, $latitude)) {
                        // user already existed
                        sendError("Beacon don't exist yet ");
                    } else {
                        // create a new user
                        $beacon = $db->updateBeacon($id, $longitude, $latitude);
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
                            sendError("Unknown error occurred in update!");
                        }
                    }
                } else {
                    sendError("Required parameters beacon_id, longitude, latitude is missing!");
                    echo json_encode($response);
                }
                break;
            case 'remove':
                if (isset($_POST['beacon_id'])) {

                    // receiving the post params
                    $id = $_POST['beacon_id'];
                    $longitude = 0;
                    $latitude = 0;

                    // check if user is already existed with the same email
                    if (!$db->isBeaconExisted($id, $longitude, $latitude)) {
                        // user already existed
                        sendError("Beacon don't exist yet ");
                    } else {
                        // create a new user
                        if($db->deleteBeacon($id)){
                            // user stored successfully
                            $response["error"] = FALSE;
                            $response["beacon"] = "Beacon has been deleted !";
                            echo json_encode($response);
                        } else {
                            sendError("Unknown error occurred in the operation!");
                        }
                    }
                } else {
                    sendError("Required parameters beacon_id, longitude, latitude is missing!");
                    echo json_encode($response);
                }
                break;
            case 'batterie':
                if (isset($_POST['beacon_id']) && isset($_POST['node_bat'])) {

                    $batterie = $_POST['node_bat'];
                    $id = $_POST['beacon_id'];
                    $longitude = 0;
                    $latitude = 0;

                    if ($db->isBeaconExisted($id, $longitude, $latitude)) {

                        $result = $db->updateBeaconBattery($id, $batterie);

                        if ($result) {
                            // user failed to store
                            $response["error"] = FALSE;
                            $response["error_msg"] = "Batterie info has been updated!";
                            echo json_encode($response);
                        }


                    } else {
                        // user already existed
                        $response["error"] = TRUE;
                        $response["error_msg"] = "Beacon " . $id . " does not exist.";

                        echo json_encode($response);
                    }

                }
                break;
            case 'presence':
                if (isset($_POST['beacon_id']) && isset($_POST['node_ps'])) {

                    $presence = $_POST['node_ps'];
                    $id = $_POST['beacon_id'];
                    $longitude = 0;
                    $latitude = 0;

                    if ($db->isBeaconExisted($id, $longitude, $latitude)) {

                        $result = $db->updateBeaconPs($id, $presence);

                        if($result){
                            // user failed to store
                            $response["error"] = FALSE;
                            $response["error_msg"] = "Presence info has been updated!";
                            echo json_encode($response);
                        }


                    } else {
                        // user already existed
                        sendError("Beacon " . $id . " does not exist.");
                    }

                }
                break;
            default:
                sendError("Your action is not yes define ! :/ ");
                break;
        };
        break;
    case 'GET':
        $db->show();
        break;
    default:
        sendError("You don't select the wright method");
        break;
}

/**
 * Send back a JSON message with an error message.
 * @param $message
 */
function sendError($message) {
    $response["error"] = TRUE;
    $response["error_msg"] = $message;
    echo json_encode($response);
}
?>