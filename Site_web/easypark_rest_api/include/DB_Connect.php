<?php

class DB_Connect {
    private $conn;


    /**Connection à MySQL
     * @return mysqli
     */
    public function connect() {
        //include mySQL configuration
        require_once 'config.php';

        // Connexion à mySQL
        $this->conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

        // Return connexion
        return $this->conn;
    }
}

