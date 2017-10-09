<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 26/01/17
 * Time: 17:55
 */
include "../function.php";
$response = array();
$cin = $_POST['cin'];
$token = $_POST['token'];


updateToken($token, $cin);

echo json_encode($response);