<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 01/01/17
 * Time: 17:35
 */
include '../function.php';

$cin = $_POST['cin']; 
$codeElection = $_POST['code_election']; 

$response = array();
$response['succes'] = false;
$response['info'] = "";


if(electionExist($codeElection)){
    if(electeurExistDansAbonnement($cin, $codeElection)){
       if(electeurDejaAbonne($cin, $codeElection)){
           $response['succes'] = false;
           $response['info'] = "vous êtes déjà abonné a cette election !"; 
       }
        else{

            if(abonnerelecteurElection($cin, $codeElection)){
                $response['succes'] = true;
                $response['info'] = "abonnement reussi";
            }
            else{
                $response['succes'] = false;
                $response['info'] = "Echec de l'abonnement pour l'election";
            }
        }
        
    }
    else{
        $response['succes'] = false; 
        $response['info'] = "Vous n'êtes pas sur la liste d'election veuillez contacter l'admin"; 
    }
    
}
else{
    $response['succes'] = false; 
    $response['info'] = "aucune election avec ce code";
}

echo json_encode($response);