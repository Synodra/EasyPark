<?php
 
require_once 'include/DB_Fonctions.php';
$db = new DB_Fonctions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['nom']) && isset($_POST['prenom']) && isset($_POST['login']) && isset($_POST['email']) && isset($_POST['password'])) {
 
    // receiving the post params
    $nom = $_POST['nom'];
    $prenom = $_POST['prenom'];
    $login = $_POST['login'];
    $email = $_POST['email'];
    $date_naissance = $_POST['date_naissance']
    $password = $_POST['password'];
 
    // check if user is already existed with the same email
    if ($db->isUserExisted($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($name, $email, $password);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["uid"] = $user["unique_id"];
            $response["user"]["nom"] = $user["nom"];
            $response["user"]["prenom"] = $user["prenom"];
            $response["user"]["email"] = $user["email"];
            $response["user"]["login"] = $user["login"];
            $response["user"]["date_creation"] = $user["date_creation"];
            $response["user"]["date_naissance"] = $user["date_naissance"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Une erreur est arrivé pendant l'inscription!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Un des champs requis est manquant, veuillez remplir toutes les zones!";
    echo json_encode($response);
}
?>