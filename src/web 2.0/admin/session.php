<?php
session_start();
define('IS_AUTHED',isset($_SESSION['authed']));

function noAuthGoLoginPage(){
 if(!IS_AUTHED){
  header("location","login.php");
  die();
 }
}
?>