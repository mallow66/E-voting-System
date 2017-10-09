<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 25/12/16
 * Time: 15:55
 */
include 'functions0.php';

$idPersonne = $_POST["idPersonne"];
$idPersonne = $_POST['idPersonne'];
$nom = $_POST[""];
$prenom = $_POST["prenom"];
$age = $_POST["age"];
$email = $_POST["email"];
$password = $_POST["password"];


$response = array();
$response['succes'] = false;
$response['info'] = "";

if(personneIsHere($nom, $email)){
    $response['succes'] = false;
    $response['info'] = "cet utilisateur existe deja !";


}
else{
    $bool = ajouterPersonne($idPersonne, $nom, $prenom, $age, $email, $password);
    if($bool){
        $response['succes'] = true;$response['info'] = "inscription avec succes ! ";

    }
    else{
        $response['succes'] =false;
        $response['info'] = "echec de l'inscription ! ";
    }
}

echo json_encode($response);
