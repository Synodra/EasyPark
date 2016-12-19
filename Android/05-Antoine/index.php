<h1> HELLO </h1>

<?
mysqli_connect("db", "root", "password") or die(mysqli_error());
echo "Connected to MySQL<br />";

phpinfo();
?>
