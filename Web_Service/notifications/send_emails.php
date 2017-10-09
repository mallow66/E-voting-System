<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 23/01/17
 * Time: 11:19
 */

include '../function.php';
//envoyer un email contenant les codes des elections dont lequelles l'electeur peut voter

$cin = $_POST['cin'];
$response = array(); 


$tab = getCodesElections($cin);
$email = getEmailPersonne($cin);

$messageEmail = ""; 

foreach ($tab as $ligne){
    $messageEmail  .=  "Election : ". $ligne['nom_election'] . " -- Code : " . $ligne['election']. "\n";
}
mail($email, "Code Elections", $messageEmail);
$response['succes'] = true;
echo json_encode($response);



