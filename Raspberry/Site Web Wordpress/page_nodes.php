<!DOCTYPE html>
<html>
	<head>
	  <meta charset="utf-8" />
	  <title>EasyPark Nodes management</title>
	</head>
	<body>

	<form>
		<label for="new_node_id">Node ID</label>  <input type="text" id="id" class="champ" />
		<label for="new_node_latitude">Latitude</label> <input type="text" id="latitude" class="champ" />
		<label for="new_node_longitude">Longitude</label> <input type="text" id="longitude" class="champ" />
		<input type="submit" id="add" value="Add Node" onclick="return addButton();" /> <input type="reset" id="reset" value="Reset" onclick="return resetButton();" />
		<input type="button" id="refresh" value="Refresh Table" onclick="return refreshButton();" />
	</form>

	<p>Table: <span id="table"></span></p>
	<script>
	refreshButton();
	function addButton(){
		var node_id = document.getElementById('id').value;
		var node_latitude = document.getElementById('latitude').value;
		var node_longitude = document.getElementById('longitude').value;

		var ajaxurl = 'nodes.php';
		var params = 'action=insert&node_id='+node_id+'&node_latitude='+node_latitude+'&node_longitude='+node_longitude;

		var xhr = new XMLHttpRequest();
		xhr.open('POST', ajaxurl, true);
		xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		xhr.openreadystatechange = function(){
			if(this.readyState == 4 && this.status == 200){
				alert(http.responseText);
			}
		}
		xhr.send(params);
	}

	function resetButton(){
		$champ.css({ // on remet le style des champs comme on l'avait défini dans le style CSS
			borderColor : '#ccc',
			color : '#555'
		});
		$erreur.css('display', 'none'); // on prend soin de cacher le message d'erreur
	}

	function refreshButton(){
		var ajaxurl = 'nodes.php';

		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function(){
			if(this.readyState == 4 && this.status == 200){
				document.getElementById('table').innerHTML = this.responseText;
			}
		}
		xhr.open('GET', ajaxurl, true);
		xhr.send(null);
	}
	</script>
	</body>
</html>