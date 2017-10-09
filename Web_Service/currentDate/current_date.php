<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 07/01/17
 * Time: 01:02
 */

include '../function.php';


$response = array();
$date = getCurrentDate();
$response['current_date'] = $date;

echo json_encode($response);
