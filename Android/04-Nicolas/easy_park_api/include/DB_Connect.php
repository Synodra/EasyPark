<?php
class DB_Connect {
    private $conn;
 
    // Connection a la bdd & mysql
    public function connect() {
        require_once 'include/config.php';
        $this->conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
         
        return $this->conn;
    }
}
 
?>