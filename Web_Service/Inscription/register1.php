<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 31/12/16
 * Time: 12:25
 */

include '../function.php'; 
$cin = $_POST['cin'];
$response = array(); 
$response['exist'] = false;
$response['actif'] = false;
$response['succes'] = "succes";
$response['nom']="a";
$response['prenom'] = "b";


if(personneExist($cin)){
    $response['exist'] = true;
    if(!personneEstActif($cin)){
        $personne = getPersonneNonActif($cin);
        $response['nom'] = $personne['nom'];
        $response['prenom'] = $personne['prenom'];
        $response['email'] = $personne['email'];
        $code = getCode($cin); 
        //sendEmail
        $messageEmail = "Bonjour ". $personne['prenom']. ",\n Votre Code d'inscription est :  ".$code;
        mail($personne['email'], "Code d'inscription ", $messageEmail);
    }
    else{
        $response['actif'] = true;
    }



}

echo json_encode($response);
