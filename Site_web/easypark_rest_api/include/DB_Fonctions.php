<?php

/**
 * @author Ravi Tamada
 * @author Antoine MAYSLICH
 * @link http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/ Complete tutorial
 *
 */

class DB_Functions {

    private $conn;

    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }

    // destructor
    function __destruct() {

    }

    // --------------- USER MANAGMENT ----------------
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name, $firstname, $email, $password) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt

        $stmt = $this->conn->prepare("INSERT INTO ep_users(unique_id, name, firstname, email, encrypted_password, salt, created_at) VALUES(?, ?, ?, ?, ?, ?, NOW())");
        $stmt->bind_param("ssssss", $uuid, $name, $firstname, $email, $encrypted_password, $salt);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM ep_users WHERE email = ?");
            $stmt->bind_param("s", $email);
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
    public function getUserByEmailAndPassword($email, $password) {

        $stmt = $this->conn->prepare("SELECT * FROM ep_users WHERE email = ?");

        $stmt->bind_param("s", $email);

        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            // verifying user password
            $salt = $user['salt'];
            $encrypted_password = $user['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }

    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $stmt = $this->conn->prepare("SELECT email from ep_users WHERE email = ?");

        $stmt->bind_param('s', $email);

        $stmt->execute();

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // user existed
            $stmt->close();
            return true;
        } else {
            // user not existed
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
        $salt = substr($salt, 0, 10);
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


    // --------------- BEACON MANAGMENT ----------------
    /**
     * Check Beacon is existed or not
     * Compare ID, longitude and latitude
     * @author Antoine MAYSLICH
     * @return true, false
     */
    public function isBeaconExisted($id, $longitude, $latitude){
        $stmt = $this->conn->prepare("SELECT * FROM ep_beacon WHERE unique_id = ? OR (longitude = ? AND latitude = ?)");

        $stmt->bind_param('sdd', $id, $longitude, $latitude);

        $stmt->execute();

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // beacon existed
            $stmt->close();
            return true;
        } else {
            // beacon not existed
            $stmt->close();
            return false;
        }
    }

    /**
     * Storing new Beacon
     * return beacon details
     * @author Antoine MAYSLICH
     * @return flase, $beacon parameters
     */
    public function addBeacon($id, $longitude, $latitude) {
        $stmt = $this->conn->prepare("INSERT INTO ep_beacon(unique_id, longitude, latitude, created_at) VALUES(?, ?, ?, NOW())");
        $stmt->bind_param("sdd", $id, $longitude, $latitude);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM ep_beacon WHERE unique_id = ?");
            $stmt->bind_param("s", $id);
            $stmt->execute();
            $beacon = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $beacon;
        } else {
            return false;
        }
    }

    /**
     * Beacon removal
     * @param $id
     * @return bool
     */
    public function deleteBeacon($id){
        $stmt = $this->conn->prepare("DELETE FROM ep_beacon WHERE unique_id = ?");
        $stmt->bind_param("s", $id);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful removal
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM ep_beacon WHERE unique_id = ?");
            $stmt->bind_param("s", $id);
            $stmt->execute();
            $stmt->store_result();

            if ($stmt->num_rows > 0) {
                // beacon existed
                $stmt->close();
                return false;
            } else {
                // beacon not existed
                $stmt->close();
                return true;
            }
        } else {
            return false;
        }

    }

    /**
     * Update beacon information
     * @param $id
     * @param $longitude
     * @param $latitude
     * @return array|bool
     */
    public function updateBeacon($id, $longitude, $latitude) {
        $stmt = $this->conn->prepare("UPDATE ep_beacon SET longitude = ?, latitude = ?, updated_at = NOW() WHERE unique_id = ?");
        $stmt->bind_param("dds", $longitude, $latitude, $id);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful update
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM ep_beacon WHERE unique_id = ?");
            $stmt->bind_param("s", $id);
            $stmt->execute();
            $beacon = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $beacon;
        } else {
            return false;
        }
    }

    /**
     *  Update Battery
     */
    public function updateBeaconBattery($id, $node_bat){
        $stmt = $this->conn->prepare("UPDATE ep_beacon SET node_bat = ? WHERE unique_id = ?");
        $stmt->bind_param("ss", $node_bat, $id);
        $stmt->execute();
        $stmt->close();
        return true;

    }

    /**
     *  Update présence
     */
    public function updateBeaconPs($id, $node_ps){
        $stmt = $this->conn->prepare("UPDATE ep_beacon SET node_ps = ? WHERE unique_id = ?");
        $stmt->bind_param("ss", $node_ps, $id);
        $stmt->execute();
        $stmt->close();
        return true;
    }

    /**
     * Searching for all beacon in the area
     * return the list of all the beacon
     */
    public function getBeaconByLocation($minLat, $maxLat, $minLng, $maxLng) {
        $stmt = $this->conn->prepare("SELECT unique_id, latitude, longitude FROM ep_beacon WHERE (latitude BETWEEN ? AND ?) AND (longitude BETWEEN  ? AND ?) AND (node_ps = 0)");
        $stmt->bind_param("dddd",$minLat, $maxLat, $minLng, $maxLng);
        $stmt->execute();
        $result = $stmt->get_result()->fetch_all();
        $stmt->close();
        return $result;
    }

    public function show(){
        if(empty($_GET)){
            $stmt = $this->conn->prepare("SELECT * FROM ep_beacon");
        }else{
            $id=$_GET['node_id'];
            $stmt = $this->conn->prepare("SELECT * FROM ep_beacon WHERE unique_id= ?");
            $stmt->bind_param("s", $id);
        }
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows > 0) {
            echo '<table>';
            echo '<tr>
                  <th>'.'<strong>Node id</strong>'.'</th>
                  <th>'.'<strong>Node latitude</strong>'.'</th>
                  <th>'.'<strong>Node longitude</strong>'.'</th>
                  <th>'.'<strong>Node battery</strong>'.'</th>
                  <th>'.'<strong>Occupied?</strong>'.'</th>
                  <th>'.'<strong>Last Upadte</strong>'.'</th>
                </tr>';

            // output data of each row
            while($row = $result->fetch_assoc()) {
                $currentRow++;

                echo 	'<form class="row">
                        <tr>
                          <td>
                            '.$row['unique_id'].'
                          </td>
                          <td>
                            <input type="text" class="cellsEditable" value='.$row['latitude'].' readonly/>
                          </td>
                          <td>
                            <input type="text" class="cellsEditable" value='.$row['longitude'].' readonly/>
                          </td>
                          <td>
                            '.$row['node_bat'].'
                          </td>
                          <td>
                            '.($row["node_ps"] == 1 ? 'Yes' : 'No').'
                          </td>
                          <td>
                            '.$row["updated_at"].'
                          </td>
                          <td class="buttonCells">
                            <input type="submit" class="buttons" value="Delete" onclick="return deleteButton('.$currentRow.');"/>
                          </td>
                          <td class="buttonCells">
                            <input type="submit" class="buttons" value="Edit" onclick="return editButton('.$currentRow.');"/>
                          </td>
                        </tr>
                    </form>';
            }
            echo '</table>';
        } else {
            echo "0 results";
        }

        $stmt->close();
    }

}
?>