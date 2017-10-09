<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 05/01/17
 * Time: 20:41
 */
include '../function.php'; 

$codeElection = $_POST['code_election'];

$result = getVotesElection($codeElection);

echo json_encode($result);