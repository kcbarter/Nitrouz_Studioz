<?php
/**
 * Created by PhpStorm.
 * User: kcbar
 * Date: 12/29/2018
 * Time: 1:21 PM
 */

//require the autoload file
require_once ('vendor/autoload.php');

session_start();

//create an instance of the base class
$f3 = Base::instance();

//Define a default route
$f3->route('GET /', function() {
    $view = new Template();
    echo $view ->render("views/home.html");
});
