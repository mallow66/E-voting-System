<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 31/12/16
 * Time: 18:59
 */

include '../function.php'; 

$cin = $_POST['cin'];
$prenom = $_POST['prenom'];
$nom= $_POST['nom'];
$email = $_POST['email'];
$password = $_POST['password']; 

$response = array();
if(ajouterElecteur($cin, $nom, $prenom, $email, $password))
    $response['succes'] = true;
else{
    $response['succes'] = false;
}

echo json_encode($response);