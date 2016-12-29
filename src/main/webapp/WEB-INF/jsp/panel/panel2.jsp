
<%@ page session="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title><fmt:message key="msg.TituloPaginaConfirmarCodigo" /></title>
        <jsp:include page="../shared/theme3/user/headLogin.jsp" />
        <script>
            function validar() {
                if (document.form.password.value != document.form.confirmPassword.value)
                {
                    alert('¡los datos de los campos no coinciden, intente de nuevo');
                    document.form.password.focus();
                    return false;
                    // 
                } else
                {
                    /* Si todo está OK se prosigue con lo que sea: */
                    alert('¡Cambio exitoso, se redigira a la pantalla de  login para que ingrese sus nuevos datos!');
                    document.form.submit;
                    return true;
                }
            }
        </script>

    </head>

    <body class="login-img3-body">

        <div class="container">
            <center> <img src="${pageContext.request.contextPath}/resources/image/logo.png" /> </center>

            <form name="form" class="login-form" method="POST"  onsubmit="return validar()" action="validarNewPassword.htm">        
                <div class="login-wrap">
                    <center> 
                        <label><fmt:message key="msg.IngreseCodigo" /><br> ${codigo}</label>
                    </center>

                    <div class="input-group">
                        <span class="input-group-addon"><i class="icon_phone"></i></span>
                        <label for="Codigo" class="sr-only"><fmt:message key="msg.Codigo" /></label>
                         <label for="Telefono" class="sr-only">Telefono</label>
                                <input type="tel" value="${sessionScope.usuario}" readonly  name="telefono" id="telefono" class="form-control" placeholder="Numero de telefono" required autofocus>
                                <label for="inputPassword" class="sr-only">Password</label>
                                <input type="password" name="password"  id="inputPassword" class="form-control" placeholder="<fmt:message key="msg.Password" />" required >
                                <label for="confirmPassword" class="sr-only">Confiramar Password</label>
                                <input type="password" name="confirmPassword" id="inputPassword" class="form-control" placeholder="<fmt:message key="msg.ConfirmarPassword" />" required>
                                <input class="btn btn-lg btn-warning btn-block" type="submit" name="btnenvio"value="<fmt:message key="msg.BotonConfirmar" />" >
                    </div>
                    <input  class="btn btn-primary btn-lg btn-block" type="submit"  name="btnenvio"value="<fmt:message key="msg.Confirmar" />" >                    
                </div>
                <div>
                    <center>
                        <br>
                        <a href="login.htm"><fmt:message key="msg.RegresarLogin" /></a>

                    </center>
                </div>
                <div id="Error">
                    <center>
                        <h4>
                            <label> ${mensaje}</label>
                        </h4>
                    </center>
                </div>
            </form>
        </div>
    </body>
</html>
