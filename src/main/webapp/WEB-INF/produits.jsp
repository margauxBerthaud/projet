<%--
    Document   : customer
    Created on : 11 avril 2019, 14:50:14
    Author     : Adrien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

        <title>Web Market de ${userName}</title>

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
    </head>
    <body>

        <div class="wrapper">
            <div class="sidebar" data-color="blue"  data-image="assets/img/sidebar-2.jpg">

                <!--   you can change the color of the sidebar using: data-color="blue | azure | green | orange | red | purple" -->


                <div class="sidebar-wrapper">
                    <div class="logo">
                        <a href="#" class="simple-text">
                            Bienvenue ${userName}!
                        </a>
                    </div>

                    <ul class="nav">

                        <li >
                            <a href="CustomerController?action=SHOW_CLIENT">
                                <i class="pe-7s-user"></i>
                                <p>Votre Profil</p>
                            </a>
                        </li>

                        <li class="active">
                            <a href="CustomerController?action=SHOW_PRODUIT">
                                <i class="pe-7s-news-paper"></i>
                                <p>Liste des Produits</p>

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
                            <a class="navbar-brand" href="#">Nous vous souhaitons la bienvenue chez ${userName} !</a>
                        </div>
                        <div class="collapse navbar-collapse">


                        </div>
                    </div>

                </nav>


                <div class="content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-8">
                                <div class="card">
                                    
                                    <div class="content">

                                        <div class="row">
                                            <div class="col-md-5">
                                                
                                                   
                                                <div class="col-md-4">
                                                    <div class="form-group">

                                                        
                                                    </div>
                                                </div>
                                            </form>
                                        </div>


                                    </div>
                                </div>
                                <div class="card">
                                    <div class="header">
                                        <h4 class="title">Effectuer une commande : </h4>
                                    </div>
                                    <div class="content">
                                        <form method='POST' action="CustomerController">
                                            <div class="row">
                                                <div class="col-md-5">
                                                    <div class="form-group">
                                                        <label>Produit</label>
                                                        <br>
                                                        <select name="produit" class="select-custom selectpicker">
                                                            <c:forEach var="item" items="${listeProduit}">
                                                                <option value="${item.DESCRIPTION}">${item.DESCRIPTION}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label>Quantité</label>
                                                        <input type="number" class="form-control" placeholder="Quantite" value="" name="quantite">
                                                        <input type="hidden" name="action" value="ADD_COMMANDE">
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group ">
                                                        <button type="submit" class="btn btn-info btn-fill pull-right">Ajouter</button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                        </form>
                                    </div>
                                </div>
                                <div><h4>${message}</h4></div>
                            </div>

                            <div class="col-md-4">
                                

                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="card" >
                                    <div class="header">
                                        <h4 class="title">Liste des produits </h4>
                                        <p class="category">Voici la liste des produits </p>
                                    </div>
                                    <div class="content table-responsive table-full-width">
                                        <table class="table table-hover table-striped">
                                            <thead>

                                            <th>Identifiant du Produit</th>
                                            <th>Prix </th>
                                            <th>Produit</th>
                                            


                                            </thead>
                                            <tbody>
                                                <c:forEach var="p" items="${listeProduit}">
                                                    <tr>

                                                        <td >
                                                            ${p.PRODUCT_ID}
                                                        </td>
                                                        <td>
                                                            
                                                            <fmt:setLocale value = "en_US"/>
                                                            <fmt:formatNumber value = "${p.PURCHASE_COST}" type = "currency"/>
                                                        </td>
                                                        <td >
                                                            ${p.DESCRIPTION}
                                                        </td>
                                                        <td >
                                                            <c:forEach var="item" items="${code}">
                                                                <fmt:setLocale value = "en_US"/>
                                                                <fmt:formatNumber value = "${(((100-item.taux) * p.PURCHASE_COST)/100)}" type = "currency"/>
                                                                
                                                            </c:forEach>
                                                        </td>

                                                    </tr>
                                                </c:forEach> 
                                            </tbody>
                                        </table>

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

</html>
