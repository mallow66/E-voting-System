<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 13/01/17
 * Time: 21:34
 */

include '../function.php';


//$cin = $_POST['cin'];
$token = $_POST['token'];



insertUserFirbase2($token);
