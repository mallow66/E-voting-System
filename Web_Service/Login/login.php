<?php
session_start();
?>
<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 02/01/17
 * Time: 09:36
 */
include "../function.php"; 



$email = $_POST['email']; 
$password = $_POST['password'];
$_SESSION['cin'] = ""; 

$response = array();

$personne = loginPersonne($email, $password);
if($personne!= -1){
    $response['succes'] = true;
    $response['cin'] = $personne['cin'];
    $_SESSION['cin'] = $personne['cin'];
    $response['prenom'] = $personne['prenom'];
    $response['nom'] = $personne['nom'];
    $response['type'] = getTypePersonne($personne['cin']);

}
else{
    $response['succes'] = false;
    $response['info'] = "Information erroné";
}

echo json_encode($response);