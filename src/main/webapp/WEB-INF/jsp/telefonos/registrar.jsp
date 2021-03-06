<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title><fmt:message key="msg.TituloPaginaRegistrar" /></title>
        <jsp:include page="../shared/theme3/user/headLogin.jsp" />

        <script>
            function validar() {
                if (document.form.password.value != document.form.confirmPassword.value)
                {
                    alert('Passwords do not match, try again');
                    document.form.password.focus();
                    return false;
                    // 
                } else
                {
                    /* Si todo está OK se prosigue con lo que sea: */
                   
                    document.form.submit;
                    return true;
                }
            }
        </script>
        <script type="text/javascript">

            function checkifempty()
            {
                if (!document.form.condicion.checked)
                {
                    document.form.btnenvio.disabled = true;

                } else
                {
                    document.form.btnenvio.disabled = false;
                }

            }
        </script>
    </head>

    <body class="login-img3-body">

        <div class="container">
            <center> <img src="${pageContext.request.contextPath}/resources/image/logo.png" /> </center>
            <form name="form" class="login-form" method="POST" onSubmit="return validar()" action="validarRegistro.htm">        
                <div class="login-wrap">
                    <center> <h3 class="panel-primary"><fmt:message key="msg.InformacionCliente" /></h3></center> 
                    <label for="codigoArea" ><fmt:message key="msg.CodigoArea" /></label>

                    <select name="codigo"  required  >
                        <option value="1">Estados Unidos </option> 
                        <option value="1">Canada</option> 
                        <option value="1">Puerto Rico</option> 
                        <option value="1">Republica Dominicana</option> 
                        <option value="502">Guatemala</option> 

                    </select>
                    <div class="input-group">
                        <span class="input-group-addon"><i class="icon_phone"></i></span>
                        <label for="Telefono" class="sr-only"><fmt:message key="msg.Telefono" /></label>
                        <input type="tel" name="telefono" id="telefono" class="form-control" placeholder="<fmt:message key="msg.Telefono" />" required autofocus>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon"><i class="icon_key_alt"></i></span>
                        <label for="inputPassword" class="sr-only"><fmt:message key="msg.Password" /></label>
                        <input type="password" name="password"  id="inputPassword" class="form-control" placeholder="<fmt:message key="msg.Password" />" required >
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon"><i class="icon_key_alt"></i></span>
                        <label for="confirmPassword" class="sr-only"><fmt:message key="msg.ConfirmarPassword" /></label>
                        <input type="password" name="confirmPassword" id="inputPassword" class="form-control" placeholder="<fmt:message key="msg.ConfirmarPassword" />" required>
                    </div>

                    <div class="input-group">
                        <span class="input-group-addon"><i class="icon_contacts"></i></span>
                        <label for="nombres" class="sr-only"><fmt:message key="msg.ConfirmarPassword" /></label>
                        <input type="text" name="nombres" id="nombres" class="form-control" placeholder="<fmt:message key="msg.Nombres" />" required>
                    </div>

                    <div class="input-group">
                        <span class="input-group-addon"><i class="icon_contacts"></i></span>
                        <label for="nombres"class="sr-only"><fmt:message key="msg.ConfirmarPassword" /></label>
                        <input type="text" name="apellidos" id="apellidos" class="form-control" placeholder="<fmt:message key="msg.Apellidos" />" required>
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="condicion" id="condicion" onclick="checkifempty()"/> <fmt:message key="msg.YoAcepto" />   <a target="_blank"  href="politicas.htm"><fmt:message key="msg.TerminosYCondiciones" /> </a>
                        </label>
                    </div>

                    <input  class="btn btn-primary btn-lg btn-block" type="submit" disabled="true" name="btnenvio"value="<fmt:message key="msg.Registrar" />" >                                        
                    <center> <h3> <label> <a href="login.htm"><fmt:message key="msg.RegresarLogin" /></a> </label> </h3></center> 
                </div>
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
            </form>
        </div>
    </body>
</html>
