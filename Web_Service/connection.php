<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 21/12/16
 * Time: 14:56
 */

try
{
    $bdd = new PDO('mysql:host=localhost;dbname=e_voting0;charset=utf8', 'root', '');
}
catch (Exception $e)
{
    echo "erreur";
    die('Erreur : ' . $e->getMessage());

}