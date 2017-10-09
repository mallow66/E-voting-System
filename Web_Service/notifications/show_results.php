<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 14/01/17
 * Time: 09:50
 */

include '../function.php'; 


if(isset($_POST['afficher_resultat'])){
    
    $election = $_POST['election']; 
    insertResultat($election);
    $tokens = getTokensForElectionResult($election);
    $nomElection = getNomElection($election);
    if($tokens != -1){
       // $message = array("message" => "les Resultats sont disponible pour l'election ".$election);
        $message = array("message" => "les Resultats sont disponible pour l'election ".$nomElection,
                         "idElection" => $election,
                         "nomElection"=>$nomElection);
        $message_status = sendNotification($tokens,$message);

        echo $message_status;
    }
}