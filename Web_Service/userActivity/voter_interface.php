<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 05/01/17
 * Time: 22:13
 */
include '../function.php'; 

$electeur = $_POST['electeur']; 
$election = $_POST['election']; 
$candidat = $_POST['candidat'];


$response = array(); 
$response['succes']= false;

if(incrementVoteCanVoteCandidat($candidat, $election)){
    if(setAVoteElecteur($electeur, $election)){
        $response['succes'] = true;
    }
}

echo json_encode($response);