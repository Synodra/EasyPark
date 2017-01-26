<?php
/**
 * Created by PhpStorm.
 * User: antoine
 * Date: 1/24/17
 * Time: 3:40 PM
 */

require_once 'include/DB_Fonctions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

$method = $_SERVER['REQUEST_METHOD'];
switch($method) {
    case 'POST':
        switch ($_POST['action']) {

            case 'changepassword':
                if (isset($_POST['email']) && isset($_POST['password']) && isset($_POST['newpassword']) ) {
                    // receiving the post params
                    $email = $_POST['email'];
                    $password = $_POST['password'];
                    $newpassword = $_POST['newpassword'];

                    if ($db->isUserExisted($email)) {
                        $db->changePassword($email, $password, $newpassword);

                        // user is not found with the credentials
                        $response["error"] = FALSE;
                        $response["error_msg"] = "Login credentials have been updated";
                        echo json_encode($response);

                    } else {
                        // user does not exist
                        sendError("User does not exist yet");
                    }


                }else {
                    sendError("Wrong parameters");
                }
                break;
            case 'changeusername':
                if (isset($_POST['email']) && isset($_POST['name']) && isset($_POST['firstname'])) {
                    // receiving the post params
                    $email = $_POST['email'];
                    $name = $_POST['name'];
                    $firstname = $_POST['firstname'];

                    if ($db->isUserExisted($email)) {
                        $user = $db->changeUsername($email, $name, $firstname);

                        if ($user != false) {
                            // use is found
                            $response["error"] = FALSE;
                            $response["uid"] = $user["unique_id"];
                            $response["user"]["name"] = $user["name"];
                            $response["user"]["firstname"] = $user["firstname"];
                            $response["user"]["email"] = $user["email"];
                            $response["user"]["created_at"] = $user["created_at"];
                            $response["user"]["updated_at"] = $user["updated_at"];
                            echo json_encode($response);
                        } else {
                            // user is not found with the credentials
                            $response["error"] = TRUE;
                            $response["error_msg"] = "Login credentials are wrong. Please try again!";
                            echo json_encode($response);
                        }

                    } else {
                        // user does not exist
                        sendError("User does not exist yet");
                    }
                }else {
                    sendError("Wrong parameters");
                }
                break;
            default:
                sendError("Your action is not yes define ! :/ ");
                break;
        };
        break;
    case 'GET':
        sendError("GET method is not available for this function");
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