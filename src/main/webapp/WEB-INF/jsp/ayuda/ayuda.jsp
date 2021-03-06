<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>InterCity| <fmt:message key="msg.TituloPaginaAyuda" /></title>

        <jsp:include page="../shared/theme2/user/headDashboard.jsp" />

    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <jsp:include page="../shared/theme2/user/topMenu.jsp" />
            <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../shared/theme2/user/leftMenu.jsp" />

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <fmt:message key="msg.Ayuda" />
                        <small></small>

                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="panel.htm"><i class="fa fa-dashboard"></i> <fmt:message key="msg.Escritorio" /></a></li>
                        <li class="active"><fmt:message key="msg.Ayuda" /></li>
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
                                    <div class="box box-success box-solid">
                                        <div class="box-header with-border">
                                            <h3 class="box-title"><fmt:message key="msg.Ayuda" /></h3>

                                            <div class="box-tools pull-right">
                                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                                </button>
                                            </div>
                                            <!-- /.box-tools -->
                                        </div>
                                        <!-- /.box-header -->
                                        <div class="box-body">

                                            <div id="result">

                                                ${resultado}

                                            </div>
                                        </div>
                                        <!-- /.row -->
                                        <div id="result">
                                            <% String msj = (String) request.getAttribute("mensaje");
                                                if (msj != null) {
                                            %>
                                            <div id="Error" class="alert alert-success">
                                                <center>
                                                    <b> <h4><%= msj%></h4></b>
                                                </center>
                                            </div>

                                            <%
                                                } else {
                                                }
                                            %>    

                                            <!-- /.box-body -->
                                        </div>

                                    </div>

                                    <!-- /.box-body -->
                                </div>
                                <!-- /.box -->
                            </div>

                        </div>
                        <!-- /.row -->



                        <!-- =========================================================== -->
                    </div>
                    <!-- /#page-wrapper -->

                </section>
                <!-- /.content -->

            </div>
            <!-- /.row (main row) -->
            <!-- /.content-wrapper -->
            <div class="control-sidebar-bg"></div>
            <jsp:include page="../shared/theme2/user/footer.jsp" />

    </body>
</html>
