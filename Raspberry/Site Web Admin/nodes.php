<?php
$servername = "localhost";
$username = "root";
$password = "password";
$dbname = "easypark_nodes";

$method = $_SERVER['REQUEST_METHOD'];
switch($method){
	case 'POST':
		switch($_POST['action']){
			case 'insert':
				add($_POST['node_id'],$_POST['node_latitude'],$_POST['node_longitude']);
				break;
			case 'delete':
				deleteRow($_POST['node_id']);
				break;
			default:
				break;
		}
		break;
	case 'GET':
		show();
		break;
	default:
		break;
}

function add($id,$latitude,$longitude){
	global $servername, $username, $password, $dbname;
	
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

function deleteRow($id){
	global $servername, $username, $password, $dbname;
	
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 

	$deletesql = "DELETE FROM ESIGELEC WHERE node_id=$id;";
	$result = $conn->query($deletesql);

	$conn->close();
}

function show(){
	global $servername, $username, $password, $dbname;
	
	$currentRow = 0;
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 
	
	if(empty($_GET)){
		$sql = "SELECT * FROM ESIGELEC";
	}else{
		$id=$_GET['node_id'];
		$sql = "SELECT * FROM ESIGELEC WHERE node_id=$id";
	}
	$result = $conn->query($sql);
	
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
			
			echo 	'<tr>
					  <td>
						'.$row['node_id'].'
					  </td>
					  <td>
						'.$row['node_latitude'].'
					  </td>
					  <td>
						'.$row['node_longitude'].'
					  </td>
					  <td>
						'.$row['node_bat'].'
					  </td>
					  <td>
						'.($row["node_ps"] == 1 ? 'Yes' : 'No').'
					  </td>
					  <td>
						'.$row["node_last_update"].'
					  </td>
					  <td class="deleteRowButton">
						<form>
							<input type="submit" id="delete" value="Delete" onclick="return deleteButton('.$currentRow.');"/>
						</form>
					  </td>
					</tr>';
		}
		echo '</table>';
		} else {
			echo "0 results";
		}
	
	$conn->close();
}
?>