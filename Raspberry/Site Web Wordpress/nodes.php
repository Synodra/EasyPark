<?php
$method = $_SERVER['REQUEST_METHOD'];

switch($method){
	case 'POST':
		add($_POST['node_id'],$_POST['node_latitude'],$_POST['node_longitude']);
		break;
	case 'GET':
		show();
		break;
	default:
		break;
}

function add($id,$latitude,$longitude){
	$servername = "localhost";
	$username = "root";
	$password = "password";
	$dbname = "easypark_nodes";
	
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 


	$insertsql = "INSERT INTO ESIGELEC (node_id, node_latitude, node_longitude) VALUES($id, $latitude, $longitude);";
	$result = $conn->query($insertsql);

	$conn->close();
}

function show(){
	$servername = "localhost";
	$username = "root";
	$password = "password";
	$dbname = "easypark_nodes";
	
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 
	
	$sql = "SELECT * FROM ESIGELEC";
	$result = $conn->query($sql);
	
	if ($result->num_rows > 0) {
	echo '<table>';
		echo '<tr>
		  <td>'.'<strong>Node id</strong>'.'</td>
		  <td>'.'<strong>Node latitude</strong>'.'</td>
		  <td>'.'<strong>Node longitude</strong>'.'</td>
		  <td>'.'<strong>Node battery</strong>'.'</td>
		  <td>'.'<strong>Occupied?</strong>'.'</td>
		  <td>'.'<strong>Last Upadte</strong>'.'</td>
		</tr>';
	
		// output data of each row
		while($row = $result->fetch_assoc()) {
			echo '<tr>
			  <td>'.$row['node_id'].'</td>
			  <td>'.$row['node_latitude'].'</td>
			  <td>'.$row['node_longitude'].'</td>
			  <td>'.$row['node_bat'].'</td>
			  <td>'.($row["node_ps"] == 1 ? 'Yes' : 'No').'</td>
			  <td>'.$row["node_last_update"].'</td>
			</tr>';
		}
	echo '</table>';
	} else {
		echo "0 results";
	}
	
	$conn->close();
}
?>