

<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 03/01/17
 * Time: 15:17
 */

include '../function.php';

$cin = $_POST['cin'];
ajouterPersonne($cin."testtest","xxxxxxxxx" ,"xxxxxxxx" ,"xxxxxxxx" ,"xxxxxxx" );


$result = array();

$result = getAbonnementListView($cin);

echo json_encode($result);