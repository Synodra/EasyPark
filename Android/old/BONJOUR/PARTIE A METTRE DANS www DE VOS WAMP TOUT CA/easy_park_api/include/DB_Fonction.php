<?php
 
class DB_Functions {
 
    private $conn;
 
    // Constructeur
    function __construct() {
        require_once 'DB_Connect.php';
        // connection bdd
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // Destructeur
    function __destruct() {
         
    }
 
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($nom, $prenom, $email, $mdp, $date_birth, $login) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($mdp);
        $mdp_crypte = $hash["encrypted"]; // mot de passe
        $salt = $hash["salt"];

        $stmt = $this->conn->prepare("INSERT INTO users(unique_id, nom, prenom, email, mdp, salt, login, date_naissance,date_creation) VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW())");
        $stmt->bind_param("ssssssss", $uuid, $nom, $prenom, $email, $mdp_crypte, $salt $login, $date_birth);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE (email = ? AND login = ?)");
            $stmt->bind_param("ss", $email, $login);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }
    }
 
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($login, $mdp) {
 
        $stmt = $this->conn->prepare("SELECT * FROM users WHERE login = ?");
        $stmt->bind_param("s", $login);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            // verification du mot de passe
            $salt = $user['salt'];
            $pass = $user['mdp'];
            $hash = $this->checkhashSSHA($salt, $mdp);
            if ($pass == $hash) {
                //si mot de passe valide
                return $user;
            }
        } else {
            return NULL;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($login) {
        $stmt = $this->conn->prepare("SELECT login from users WHERE login = ?");
        $stmt->bind_param("s", $login);
        $stmt->execute();
        $stmt->store_result();
        if ($stmt->num_rows > 0) {
            // login valide 
            $stmt->close();
            return true;
        } else {
            // login non valide
            $stmt->close();
            return false;
        }
    }
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10)
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
 
}
 
?>