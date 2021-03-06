/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.dao.TelefonosDao;
import com.dao.UsuariosDao;
import com.entitys.Detalles;
import com.jsonEntitys.Account;
import com.entitys.Telefonos;
import com.entitys.Usuarios;
import com.jsonEntitys.AccountLight;
import com.util.httpAccount;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 *
 * @author jorge
 */
@Controller

public class PerfilController {

    HttpSession sesion;
    String sesionUser;
    Account account;
    String mensaje;

    @RequestMapping("perfil.htm")
    public ModelAndView getPerfil(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            sesion = request.getSession();

            mensaje = null;

            if (sesion.getAttribute("usuario") == null) {
                mav.setViewName("login/login");

            } else {
                String sesUser = sesion.getAttribute("usuario").toString();
                System.out.println("el usuario a editar es " + sesUser);
                httpAccount accountHelper = new httpAccount();
                TelefonosDao telDao = new TelefonosDao();
                Telefonos telefono = new Telefonos();
                telefono = telDao.getTelefono(sesUser);

                Usuarios usuario = new Usuarios();
                UsuariosDao userDao = new UsuariosDao();
                usuario = userDao.getUsuario(telefono.getUsuarios().getIdUsuario());

                try {
                    account = accountHelper.getAccountObject(sesUser);
                    String idAccount = account.getId();
                    String idLenguaje = account.getLanguaje_id();
                    String idioma;
                    String notMail = null;
                    String idiomaActual = RequestContextUtils.getLocale(request).getLanguage();
                    
                    if (idiomaActual.compareTo("es") == 0) {
                        if ((account.getNotify_email().compareTo("true") == 0)) {
                            notMail = "Si";
                        } else {
                            notMail = "No";
                        }

                    } else {

                        if ((account.getNotify_email().compareTo("true") == 0)) {
                            notMail = "yes";
                        } else {
                            notMail = "false";
                        }
                    }
                    mav.addObject("notMail", notMail);

                    if (account.getLanguaje_id().compareTo("1") == 0) {
                        idioma = "Español";
                    } else if (account.getLanguaje_id().compareTo("2") == 0) {
                        idioma = "English";
                    } else {
                        idioma = "English";
                    }
                    mav.addObject("idioma", idioma);

                    mav.addObject("telefono", telefono);

                    mav.addObject("user", usuario);
                    mav.addObject("account", account);

                    if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                        mav.setViewName("viewsAdmin/perfilAdmin");
                    } else {
                        mav.setViewName("panel/perfil");
                    }
                } catch (Exception e) {
                    mav.addObject("telefono", telefono);
                    mav.addObject("user", usuario);
                    mav.addObject("account", account);
                    mensaje = "Servidor no disponible";
                    mav.addObject("mensaje", mensaje);

                    if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                        mav.setViewName("viewsAdmin/perfilAdmin");
                    } else {
                        mav.setViewName("panel/panel");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mensaje = "Sorry, view unavailable at this time";
            mav.addObject("mensaje", mensaje);

            if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                mav.setViewName("viewsAdmin/panelAdmin");
                System.out.println("el usuario es administrador");
            } else {
                mav.setViewName("panel/panel");
            }

        }
        return mav;
    }

    @RequestMapping(value = "validarEditarPerfil.htm", method = RequestMethod.POST)
    public ModelAndView validarRegistrarUsuarios(HttpServletRequest request
    ) throws MalformedURLException, IOException {
        ModelAndView mav = new ModelAndView();
        try {
            sesion = request.getSession();

            String mensaje = null;
            if (sesion.getAttribute("usuario") == null) {
                mav.setViewName("login/login");
            } else {
                String sesUser = sesion.getAttribute("usuario").toString();

                TelefonosDao telDao = new TelefonosDao();
                Telefonos telefono = new Telefonos();
                telefono = telDao.getTelefono(sesUser);

                Detalles cuenta = (Detalles) sesion.getAttribute("cuenta");

                // String idUsuario = request.getParameter("idUsuario");
                String idUsuario = cuenta.getIdUsuaro();

                String TelArea = request.getParameter(sesion.getAttribute("usuario").toString());
                String nombres = request.getParameter("nombres");
                String apellidos = request.getParameter("apellidos");
                String direccion = request.getParameter("direccion");
                String ciudad = request.getParameter("ciudad");
                String pais = request.getParameter("pais");
                String codigoPostal = request.getParameter("codigoPostal");
                String email = request.getParameter("email");
                String lenguaje = request.getParameter("languaje");
                boolean notifyEmail = false;
                boolean notifyFlag = false;
                String notmail = request.getParameter("notifyEmail");
                String notflag = request.getParameter("notifyFlag");
                if (notmail.compareTo("true")==0)
                {
                    notifyEmail=true;
                }
                else
                {
                    notifyEmail=false;
                }
                    

                AccountLight accountL = new AccountLight();
                Usuarios usuario = new Usuarios();
                UsuariosDao userDao = new UsuariosDao();

                accountL.setAddress(direccion);
                accountL.setCity(ciudad);
                accountL.setEmail(email);
                accountL.setFirstName(nombres);
                accountL.setLastName(apellidos);
                accountL.setNotifyEmail(notifyEmail);
                accountL.setNotityFlag(notifyFlag);
                accountL.setPostalCode(codigoPostal);
                accountL.setLanguaje_id(lenguaje);

                usuario = userDao.getUsuario(idUsuario);

                usuario.setApellidos(apellidos);
                usuario.setEmail(email);
                usuario.setPais(pais);
                //usuario.setIdUsuario(idUsuario);
                System.out.print("el id del usuario es " + idUsuario);
                usuario.setNombres(nombres);
                usuario.setStatus("Activo");
                httpAccount accountHelper = new httpAccount();

                if (userDao.updateUsuarios(usuario)) {

                    account = accountHelper.getAccountObject(sesUser);
                    System.out.println("\n\n\n Id de usuario " + cuenta.getIdUsuaro());

                    cuenta.setNombres(nombres);
                    cuenta.setApellidos(apellidos);
                    cuenta.setCiudad(ciudad);
                    cuenta.setEmail(email);
                    cuenta.setPais(pais);
                    cuenta.setIdiomaActual(RequestContextUtils.getLocale(request).getLanguage());

                    cuenta.setDireccion(account.getAddress1());
                    cuenta.setCodigoPostal(account.getPostal_code());
                    cuenta.setLenguaje(account.getLanguaje_id());
                    cuenta.setNotifiEmail(account.getNotify_email());
                    cuenta.setNotifiFlag(account.getNotify_flag());
                    cuenta.setSaldo(account.getBalance());
                    System.out.print("El balance de la cuenta es " + cuenta.getSaldo());

                    String idAccount = account.getId();
                    String idioma;

                    if (account.getLanguaje_id().compareTo("1") == 0) {
                        idioma = "Español";
                    } else if (account.getLanguaje_id().compareTo("2") == 0) {
                        idioma = "English";
                    } else {
                        idioma = "English";
                    }

                    mav.addObject("idioma", idioma);
                    mav.addObject("telefono", telefono);
                    mav.addObject("user", usuario);
                    mav.addObject("account", account);
                    sesion.setAttribute("cuenta", cuenta);

                    usuario = userDao.getUsuario(idUsuario);
                    if (usuario.getIdAccount() == null) {
                        System.out.println("No se ha enontrado el accountId del usuario se buscara");
                        usuario.setIdAccount(accountHelper.getIdAccount(TelArea));
                        account = accountHelper.getAccountObject(sesUser);
                        String notMail = null;
                        String idiomaActual = RequestContextUtils.getLocale(request).getLanguage();
                        if (idiomaActual.compareTo("es") == 0) {
                            if ((account.getNotify_email().compareTo("true") == 0)) {
                                notMail = "Si";
                            } else {
                                notMail = "No";
                            }

                        } else {

                            if ((account.getNotify_email().compareTo("true") == 0)) {
                                notMail = "yes";
                            } else {
                                notMail = "false";
                            }
                        }
                        mav.addObject("notMail", notMail);
                        userDao.updateUsuarios(usuario);
                        System.out.println("Se ha registrado el usuario con el servidor en linea ");
                    } else {
                        System.out.println("Se encontro el accountId del usuario ");
                        accountHelper.setAccountObject(accountL, usuario.getIdAccount());
                        account = accountHelper.getAccountObject(sesUser);
                        String notMail = null;
                        String idiomaActual = RequestContextUtils.getLocale(request).getLanguage();
                        if (idiomaActual.compareTo("es") == 0) {
                            if ((account.getNotify_email().compareTo("true") == 0)) {
                                notMail = "Si";
                            } else {
                                notMail = "No";
                            }

                        } else {

                            if ((account.getNotify_email().compareTo("true") == 0)) {
                                notMail = "yes";
                            } else {
                                notMail = "false";
                            }
                        }
                        mav.addObject("notMail", notMail);
                        System.out.println("se ha actualizado el usuario al servidor ");
                    }

                    if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                        mav.setViewName("viewsAdmin/perfilAdmin");
                    } else {
                        mav.setViewName("panel/perfil");
                    }
                } else {
                    if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                        mav.setViewName("viewsAdmin/PerfilAdmin");
                    } else {
                        mav.setViewName("panel/perfil");
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mensaje = "Sorry, view unavailable at this time";
            mav.addObject("mensaje", mensaje);

            if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                mav.setViewName("viewsAdmin/perfilAdmin");
                System.out.println("el usuario es administrador");
            } else {
                mav.setViewName("panel/perfil");
            }

        }

        return mav;
    }

}
