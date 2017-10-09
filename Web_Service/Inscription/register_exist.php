<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 01/01/17
 * Time: 15:46
 */

include '../function.php';

$cin = $_POST['cin'];
//$email = $_POST['email'];
$password = $_POST['password'];
$code = $_POST['code']; 
$response = array();


if(ajouterPersonneExist2($cin,$password ) && isCodePersonne($cin, $code)){
    $response['succes'] = true;

}

else{
    $response['succes'] = false;
}

echo json_encode($response);