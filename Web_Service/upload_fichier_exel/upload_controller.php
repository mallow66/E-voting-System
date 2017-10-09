<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 30/12/16
 * Time: 22:04
 */
require_once "Classes/PHPExcel.php";
include '../function.php';

define('doc_root',$_SERVER['DOCUMENT_ROOT']."/Web_Service/");
$file_name =$_FILES["fichierExcel"]["name"];
$tmp_name = $_FILES["fichierExcel"]["tmp_name"];
$b = move_uploaded_file($tmp_name,doc_root. "/upload_fichier_exel/" . basename($file_name));
if($b){
    echo "succes import<br>";
    importerFichierExcel2($file_name);
}



