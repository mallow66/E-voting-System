<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 07/01/17
 * Time: 17:08
 */

include '../function.php';

$codeElection = $_POST['code_election'];

if(electionisOnResult($codeElection)) {

$response = getElectionResult($codeElection);

}

echo json_encode($response);