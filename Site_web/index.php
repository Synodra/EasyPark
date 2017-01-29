<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
      <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>EasyPark Admin Control Panel</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>



    <div id="wrapper">
         <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="adjust-nav">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">
                        <img width="280px" src="assets/img/logoEP.png " />
                    </a>
                </div>

                 <span class="logout-spn" >
                  <a href="#" style="color:#fff;">ADMIN CONTROL PANEL</a>

                </span>
            </div>
        </div>
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                    <li>
                        <a href="index.php"><i class="fa fa-edit "></i>Gestion des Balises  <span class="badge">ACTIVE</span></a>
                    </li>
                    <li >
                        <a href="nodered.html" ><i class="fa fa-desktop "></i>NodeRed <span class="badge">ACTIVE</span></a>
                    </li>
                    <li>
                        <a href="grafana.html"><i class="fa fa-table "></i>Grafana  <span class="badge">ACTIVE</span></a>
                    </li>
                    <li>
                        <a href="rabbitmq.html"><i class="fa fa-table "></i>RabbitMQ  <span class="badge">ACTIVE</span></a>
                    </li>
                </ul>
              </div>
        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12"><br>
                      <h2>  Gestion de Balisses</h2><br><br>
                     <form>
      <label for="new_node_id">Node ID</label>  <input type="text" id="id" class="champ" />
      <label for="new_node_latitude">Latitude</label> <input type="text" id="latitude" class="champ" />
      <label for="new_node_longitude">Longitude</label> <input type="text" id="longitude" class="champ" />
      <input type="submit" id="add" value="Add Node" onclick="return addButton();" />
      <input type="reset" id="reset" value="Reset" onclick="return resetButton();" />
    </form>

    <p>
      Table:  <input type="button" id="refresh" value="Refresh Table" onclick="return refreshButton();" />
      <table id="table" class="table table-striped table-bordered table-hover">
      </table>
    </p>

    <script>
    // show all contents in MySQL database
    selectRequest();
    document.getElementById('editForm').style.display = 'none';

    /********* Button functions **********/
    function addButton(){
      var node_id = document.getElementById('id').value;
      var node_latitude = document.getElementById('latitude').value;
      var node_longitude = document.getElementById('longitude').value;

      if(node_id!==""&&node_latitude!==""&&node_longitude!==""){
        insertRequest(node_id, node_latitude, node_longitude);
      }else{
        alert("Parameters are missing!");
      }
    }

    function resetButton(){
      $champ.css({ // on remet le style des champs comme on l'avait défini dans le style CSS
        borderColor : '#ccc',
        color : '#555'
      });
      $erreur.css('display', 'none'); // on prend soin de cacher le message d'erreur
    }

    function deleteButton(currentRow){
      var node_id = document.getElementById('table').rows[currentRow].cells[0].innerText;
      var result = confirm("Are you resure to delete the node?");

      if(result){
        deleteRequest(node_id);
      }

      selectRequest();
    }

    function editButton(currentRow){
      var tbl= document.getElementById("table");
      var tr=tbl.rows[currentRow];
      var cellsEditable = document.getElementsByClassName("cellsEditable");
      var Buttons = document.getElementsByClassName("buttonCells");
      var i;

      for(i=2*currentRow-2;i<2*currentRow;i++)
      {
        cellsEditable[i].readOnly=false;
        cellsEditable[i].style.backgroundColor = "#cc0";
      }

      for(i=1;i<tbl.rows.length;i++)
      {
        tbl.rows[i].deleteCell(7);
        tbl.rows[i].deleteCell(6);
      }

      var change = document.createElement("INPUT");
      change.setAttribute("type", "submit");
      change.addEventListener('click',function(){
        changeButton(currentRow);
      });
      change.value="Update";
      change.class="buttons";

      var cancel = document.createElement("INPUT");
      cancel.setAttribute("type", "reset");
      cancel.onclick=cancelButton;
      cancel.value="Cancel";
      cancel.class="buttons";

      var changeCell = tr.insertCell(6);
      changeCell.style.border = "none";
      changeCell.appendChild(change);

      var cancelCell = tr.insertCell(7);
      cancelCell.style.border = "none";
      cancelCell.appendChild(cancel);

    }

    function changeButton(currentRow){
      var node_id = document.getElementById('table').rows[currentRow].cells[0].innerText;
      var node_latitude = document.getElementById('table').rows[currentRow].cells[1].childNodes[1].value;
      var node_longitude = document.getElementById('table').rows[currentRow].cells[2].childNodes[1].value;

      updateRequest(node_id, node_latitude, node_longitude);
      selectRequest();
    }

    function cancelButton(){
      selectRequest();
    }

    function refreshButton(){
      selectRequest();
    }

    /********* Request functions **********/
    function insertRequest(node_id, node_latitude, node_longitude){
      var ajaxurl = 'http://synodra.ddns.net/easypark_rest_api/admin_beacon.php';
      var params = 'action=add&beacon_id='+node_id+'&longitude='+node_longitude+'&latitude='+node_latitude;

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

    function deleteRequest(node_id){
      var ajaxurl = 'http://synodra.ddns.net/easypark_rest_api/admin_beacon.php';
      var params = 'action=remove&beacon_id='+node_id;

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

    function updateRequest(node_id, node_latitude, node_longitude){
      var ajaxurl = 'http://synodra.ddns.net/easypark_rest_api/admin_beacon.php';
      var params = 'action=update&beacon_id='+node_id+'&latitude='+node_latitude+'&longitude='+node_longitude;

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

    function selectRequest(node_id){
      var ajaxurl = 'http://synodra.ddns.net/easypark_rest_api/admin_beacon.php';
      var xhr = new XMLHttpRequest();

      xhr.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
          document.getElementById('table').innerHTML = this.responseText;
        }
      }
      if(node_id!==undefined){
        xhr.open('GET', ajaxurl+'?node_id='+node_id, true);
      }else{
        xhr.open('GET', ajaxurl, true);
      }
      xhr.send(null);
    }
    </script>
                    </div>
                </div>
                 <!-- /. ROW  -->
                  <hr />

                 <!-- /. ROW  -->
    </div>
             <!-- /. PAGE INNER  -->
            </div>
         <!-- /. PAGE WRAPPER  -->
        </div>
    <div class="footer">


             <div class="row">
                <div class="col-lg-12" >
                    &copy;  2017 EASYPARK
                </div>
        </div>
        </div>


     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="assets/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="assets/js/bootstrap.min.js"></script>
      <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>


</body>
</html>
