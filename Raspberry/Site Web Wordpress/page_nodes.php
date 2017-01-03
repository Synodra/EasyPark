[insert_php]
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
	  <td>'.'<strong>Occupied?</strong>'.'</td>
	  <td>'.'<strong>Node battery</strong>'.'</td>
	</tr>';

    // output data of each row
    while($row = $result->fetch_assoc()) {
		echo '<tr>
          <td>'.$row['node_id'].'</td>
          <td>'.$row['node_latitude'].'</td>
          <td>'.$row['node_longitude'].'</td>
          <td>'.($row["node_ps"] == 1 ? 'Yes' : 'No').'</td>
          <td>'.$row['node_bat'].'</td>
		</tr>';
    }
echo '</table>';
} else {
    echo "0 results";
}
$conn->close();
[/insert_php]