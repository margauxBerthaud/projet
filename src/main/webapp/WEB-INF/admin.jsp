<%--
    Document   : admin
    Created on : 5 avril 2019, 15:24:05
    Author     : Adrien
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>


<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        
        <title>Web Market Application</title>

        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
        <meta name="viewport" content="width=device-width" />


        <!-- Bootstrap core CSS     -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />

        <!-- Animation library for notifications   -->
        <link href="assets/css/animate.min.css" rel="stylesheet"/>

        <!--  Light Bootstrap Table core CSS    -->
        <link href="assets/css/light-bootstrap-dashboard.css?v=1.4.0" rel="stylesheet"/>


        <!--  CSS for Demo Purpose, don't include it in your project     -->
        <link href="assets/css/demo.css" rel="stylesheet" />

        <!--  CSS personnalisé    -->    
        <link href="assets/css/custom.css" rel="stylesheet" />


        <!--     Fonts and icons     -->
        <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
        <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300' rel='stylesheet' type='text/css'>
        <link href="assets/css/pe-icon-7-stroke.css" rel="stylesheet" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>


    </head>
    <body>

        <div class="wrapper">
                <div class="sidebar" data-color="blue" data-image="assets/img/sidebar-2.jpg">
                
                <!--
            
                    Tip 1: you can change the color of the sidebar using: data-color="blue | azure | green | orange | red | purple"
                    Tip 2: you can also add an image using data-image tag
            
                -->

                <div class="sidebar-wrapper">
                    <div class="logo">
                        <div class="card card-user">
                            <div class="image">
                                <img class="banniere" src= "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwi4oKmch8jhAhVl1-AKHbuFBAoQjRx6BAgBEAU&url=https%3A%2F%2Fwww.malekal.com%2Fdifference-compte-administrateur-utilisateur%2F&psig=AOvVaw1YARCS5Zm6sAto9tZes7qU&ust=1555070106840816"  alt="..."/>
                            </div>
                        </div>
                        
                       
                        <a href="#" class="simple-text">
                            Admin
                        </a>
                    </div>

                    <ul class="nav">
                        <li class="active">
                            <a href="#">
                                <i class="pe-7s-graph"></i>
                                <p>Dashboard</p>
                            </a>
                        </li>
                        <li>
                            <form class="logout" action="LoginController" method="POST">
                                <input class="form-control " type='submit' name='action' value='Logout'>
                            </form>
                        </li>

                    </ul>
                </div>
            </div>

            <div class="main-panel">
                <nav class="navbar navbar-default navbar-fixed">
                    <div class="container-fluid">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="#">Vérifions les statistiques de notre Web Market</a>
                        </div>

                    </div>
                </nav>


                <div class="content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card">

                                    <div class="header">
                                        <h4 class="title">Chiffre d'affaire par Produit</h4>                                     
                                        <form method='POST' action="AdministratorController">
                                            <input type="date" class="choixdate" name="date_debut_prod">
                                            <input type="date" class="choixdate" name="date_fin_prod">
                                            <input type="hidden" name="action" value="caByProduct">
                                            <input type="submit" value="Envoyer">
                                        </form>
                                    </div>
                                    <div class="content">
                                        <canvas id="myChart"></canvas>

                                        <div class="footer">

                                            <hr>
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="card">
                                    <div class="header">
                                        <h4 class="title">Chiffre d'affaire par Zone Géographique</h4>
                                        
                                    </div>
                                    <div class="content">
                                        <form method='POST' action="AdministratorController">
                                            <input type="date" class="choixdate" name="date_debut_geo">
                                            <input type="date" class="choixdate" name="date_fin_geo">
                                            <input type="hidden" name="action" value="caByGeo">
                                            <input type="submit" value="Envoyer">
                                        </form>
                                        <canvas id="chartGeo"></canvas>
                                        <div class="footer">

                                            <hr>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>



                        <div class="row">
                            <div class="col-md-6">
                                <div class="card ">
                                    <div class="header">
                                        <h4 class="title">Chiffre d'affaire par Client</h4>                                       
                                        <form method='POST' action="AdministratorController">
                                            <input type="date" class="choixdate" name="date_debut_clt">
                                            <input type="date" class="choixdate" name="date_fin_clt">
                                            <input type="hidden" name="action" value="caByCli">
                                            <input type="submit" value="Envoyer">
                                        </form>
                                    </div>
                                    <div class="content">
                                        <canvas id="chartCli"></canvas>

                                        <div class="footer">

                                            <hr>

                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="card ">
                                    <div class="header">
                                        <h4 class="title">Chiffre d'affaire par Catégorie d'article</h4>
   
                                        <form method='POST' action="AdministratorController">
                                            <input type="date" class="choixdate" name="date_debut_ctg">
                                            <input type="date" class="choixdate" name="date_fin_ctg">
                                            <input type="hidden" name="action" value="caByProductCode">
                                            <input type="submit" value="Envoyer">
                                        </form>
                                    </div>
                                    <div class="content">
                                        <canvas id="chartCat"></canvas>

                                        <div class="footer">

                                            <hr>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="card ">
                                    <div class="header">
                                        <h4 class="title">Chiffre d'affaire par ZIP (Code postal)</h4>
                                        
                                        <form method='POST' action="AdministratorController">
                                            <input type="date" class="choixdate" name="date_debut_zip">
                                            <input type="date" class="choixdate" name="date_fin_zip">
                                            <input type="hidden" name="action" value="caByZip">
                                            <input type="submit" value="Envoyer">
                                        </form>
                                    </div>
                                    <div class="content">
                                        <canvas id="chartZip"></canvas>

                                        <div class="footer">

                                            <hr>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <footer class="footer">
                    <div class="container-fluid">

                        <p class="copyright pull-right">
                            &copy; Promotion 2021. Projet réalisé par Margaux Berthaud, Nicolas Vollherbst, Hugo Broucke & Adrien Darribeyros
                        </p>
                    </div>
                </footer>

            </div>
        </div>


    </body>

    <!--   Core JS Files   -->
    <script src="assets/js/jquery.3.2.1.min.js" type="text/javascript"></script>
    <script src="assets/js/bootstrap.min.js" type="text/javascript"></script>

    <!--  Charts Plugin -->
    <script src="assets/js/chartist.min.js"></script>

    <!--  Notifications Plugin    -->
    <script src="assets/js/bootstrap-notify.js"></script>

    <!--  Google Maps Plugin    -->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>

    <!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
    <script src="assets/js/light-bootstrap-dashboard.js?v=1.4.0"></script>

    <!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
    <script src="assets/js/demo.js"></script>

    <script type="text/javascript">
        
    </script>
    <script>
        var ctx = document.getElementById('myChart').getContext('2d');
        var label = [];
        var titre = "Chiffre d'Affaire des produits en $ ${dateProcuct}"
        var ca = [];
        <c:forEach items="${productCA}" var="item" >
        label.push("${item.key}");
        </c:forEach>
        <c:forEach items="${productCA}" var="item" >
        ca.push(${item.value});
        </c:forEach>
        var randomColorGenerator = function () {
            return '#' + (Math.random().toString(16) + '0000000').slice(2, 8);
        };
        var chart = new Chart(ctx, {
            // The type of chart we want to create
            type: 'horizontalBar',

            // The data for our dataset
            data: {
                labels: label,
                datasets: [{
                        label: "Chiffre d'affaire par produit en $, ${dateProduct}.",
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgb(255, 99, 132)',
                        data: ca,
                    }]
            },

            // Configuration options go here
            options: {}
        });
    </script>

    <script>
        var ctx = document.getElementById('chartGeo').getContext('2d');
        var label = [];
        var ca = [];
        <c:forEach items="${geoCA}" var="item" >
        label.push("${item.key}");
        </c:forEach>
        <c:forEach items="${geoCA}" var="item" >
        ca.push(${item.value});
        </c:forEach>
        var chart = new Chart(ctx, {
            // The type of chart we want to create
            type: 'bar',

            // The data for our dataset
            data: {
                labels: label,
                datasets: [{
                        label: "Chiffre d'affaire par zone géographique en $, ${dateGeo}",
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgb(255, 99, 132)',
                        data: ca,
                    }]
            },

            // Configuration options go here
            options: {}
        });
    </script>

    <script>
        var ctx = document.getElementById('chartCli').getContext('2d');
        var label = [];
        var ca = [];
        <c:forEach items="${cliCA}" var="item" >
        label.push("${item.key}");
        </c:forEach>
        <c:forEach items="${cliCA}" var="item" >
        ca.push(${item.value});
        </c:forEach>
        var chart = new Chart(ctx, {
            // The type of chart we want to create
            type: 'bar',

            // The data for our dataset
            data: {
                labels: label,
                datasets: [{
                        label: "Chiffre d'Affaire par client en $, ${dateCli}",
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgb(255, 99, 132)',
                        data: ca,
                    }]
            },

            // Configuration options go here
            options: {}
        });
    </script>

    <script>
        var ctx = document.getElementById('chartCat').getContext('2d');
        var label = [];
        var ca = [];
        <c:forEach items="${productCodeCA}" var="item" >
        label.push("${item.key}");
        </c:forEach>
        <c:forEach items="${productCodeCA}" var="item" >
        ca.push(${item.value});
        </c:forEach>
        var chart = new Chart(ctx, {
            // The type of chart we want to create
            type: 'horizontalBar',

            // The data for our dataset
            data: {
                labels: label,
                datasets: [{
                        label: "Chiffre d'affaire par catégorie d'article en $, ${dateProductCode}",
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgb(255, 99, 132)',
                        data: ca,
                    }]
            },

            // Configuration options go here
            options: {}
        });
    </script>

    <script>
        var ctx = document.getElementById('chartZip').getContext('2d');
        var label = [];
        var ca = [];
        <c:forEach items="${zipCA}" var="item" >
        label.push("${item.key}");
        </c:forEach>
        <c:forEach items="${zipCA}" var="item" >
        ca.push(${item.value});
        </c:forEach>
        var chart = new Chart(ctx, {
            // The type of chart we want to create
            type: 'horizontalBar',

            // The data for our dataset
            data: {
                labels: label,
                datasets: [{
                           label: "Chiffre d'Affaire par code ZIP en $, ${dateZip}",
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgb(255, 99, 132)',
                        data: ca,
                    }]
            },

            // Configuration options go here
            options: {}
        });
    </script>


</html>
