<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title> <fmt:message key="msg.TituloPaginaRecargar" />| InterCity</title>

        <jsp:include page="../shared/theme2/user/headDashboard.jsp" />
        <script >
            function imprimir() {
                var objeto = document.getElementById('imprimeme');  //obtenemos el objeto a imprimir
                var ventana = window.open('', '_blank');  //abrimos una ventana vacía nueva
                ventana.document.write(objeto.innerHTML);  //imprimimos el HTML del objeto en la nueva ventana
                ventana.document.close();  //cerramos el documento
                ventana.print();  //imprimimos la ventana
                ventana.close();  //cerramos la ventana
            }

        </script>

    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <jsp:include page="../shared/theme2/user/topMenu.jsp" />
            <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../shared/theme2/admin/leftMenu.jsp" />

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <fmt:message key="msg.EstadoRecarga" />
                        <small></small>

                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="panel.htm"><i class="fa fa-dashboard"></i> <fmt:message key="msg.Escritorio" /></a></li>
                        <li class="active"><fmt:message key="msg.Recarga" /></li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <!-- Small boxes (Stat box) -->
                    <jsp:include page="../shared/theme2/user/rowCenter1.jsp" />
                    <!-- /.row -->
                    <!-- Main row -->
                    <div id="wrapper">

                        <div id="page-wrapper">
                            <div class="row">
                                <!-- /.col -->
                                <div class="col-md-12">
                                    <div id="result">
                                        <% String msj = (String) request.getAttribute("mensaje");
                                            if (msj != null) {
                                        %>
                                        <div id="Error" class="alert alert-danger">
                                            <center>
                                                <b> <h4><%= msj%></h4></b>
                                            </center>
                                        </div>

                                        <%
                                            } else {
                                            }
                                        %>  
                                    </div>
                                    <div class="box box-success box-solid">
                                        <div class="box-header with-border">
                                            <h3 class="box-title"><fmt:message key="msg.TituloVentanaRecargar" /></h3>
                                            <!-- /.box-tools -->
                                        </div>
                                        <!-- /.box-header -->
                                        <div class="box-body">
                                            <div class="col-lg-8">
                                                <div id ="imprimeme">


                                                    <div class="table-responsive">
                                                        <table class="table table-striped table-bordered">
                                                            <thead>
                                                                <tr> 
                                                                    <th> <fmt:message key="msg.Detalle" /></th>
                                                                    <th> <fmt:message key="msg.Informacion" /></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td> <fmt:message key="msg.TransactionCode" />:</td>
                                                                     <td>${resRecarga.getId()} - ${resRecarga.getCode()} </td>

                                                                </tr>
                                                                <tr>
                                                                    <td> <fmt:message key="msg.MontoRecarga" />:</td>
                                                                    <td>${resRecarga.getAmount()}</td>

                                                                </tr>
                                                                <tr>
                                                                    <td> <fmt:message key="msg.Fecha" />:</td>
                                                                    <td>${resRecarga.getRs_datetime()}</td>

                                                                </tr>
                                                                <tr>
                                                                    <td> <fmt:message key="msg.EstadoRecarga" />: </td>
                                                            <b> <td class="text-red">${resRecarga.getRs_status()}</td> </b>

                                                                </tr>
                                                                <tr>
                                                                    <td> <fmt:message key="msg.Detalles" />:</td>
                                                                    <td>${resRecarga.getDetails()}</td>

                                                                </tr>

                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <!-- /.consulta retornada por post -->

                                                </div>

                                            </div>
                                            <div class="col-lg-8">
                                                <center>
                                                    <button onclick="imprimir();" class=" btn btn-primary">
                                                        <fmt:message key="msg.Imprimir" />
                                                    </button>
                                                </center>
                                            </div>
                                            <!-- /.row -->


                                        </div>

                                    </div>

                                    <!-- /.box-body -->
                                </div>
                                <!-- /.box -->
                            </div>


                            <!-- =========================================================== -->
                        </div>
                        <!-- /#page-wrapper -->
                    </div>
                    <!-- /.row (main row) -->

                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
            <div class="control-sidebar-bg"></div>
            <jsp:include page="../shared/theme2/user/footer.jsp" />
        </div>
    </body>
</html>
