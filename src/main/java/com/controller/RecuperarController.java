/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.dao.TelefonosDao;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.entitys.Telefonos;
import com.dao.UsuariosDao;
import com.entitys.Usuarios;
import com.util.Cifrar;
import com.util.GeneradorCodigos;
import com.util.httpSendMsg;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author jorge
 */
@Controller
public class RecuperarController {

    HttpSession sesion;
    String codigo;
    int contador = 0;
    String mensaje;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public HttpSession getSesion() {
        return sesion;
    }

    public void setSesion(HttpSession sesion) {
        this.sesion = sesion;
    }

    @RequestMapping("recuperar.htm")
    public ModelAndView Recuperar() {
        ModelAndView mav = new ModelAndView();
        try {

            mav.setViewName("recuperar/recuperar");
        } catch (Exception ex) {
            ex.printStackTrace();
            mensaje = "Ha ocurrido un error al obtener la vista";
            mav.addObject("mensaje", mensaje);

            if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                mav.setViewName("login/login");

            } else {
                mav.setViewName("login/login");
            }
        }
        return mav;
    }

    @RequestMapping(value = "validarRecuperar.htm", method = RequestMethod.POST)
    public ModelAndView ValidarRecuperar(HttpServletRequest request) {
        String mensaje = null;
        sesion = request.getSession();
        ModelAndView mav = new ModelAndView();
        Telefonos telefono = new Telefonos();
        TelefonosDao telDao = new TelefonosDao();
        UsuariosDao userDao = new UsuariosDao();
        Usuarios usuario = new Usuarios();
        try {
            //cargamos los datos en un objeto usuario
            String codigo = (request.getParameter("codigo"));
            String tel = (request.getParameter("telefono"));
            String telArea = (codigo + "-" + tel);
            System.out.print("enviando el codigo a " + telArea);
            String mensajeCodigo = "InterCity Registration Code " + this.getCodigo();
            System.err.print(mensajeCodigo);
            httpSendMsg msgHelper = new httpSendMsg();
            String resultmsg = msgHelper.sendMsg(telArea, mensajeCodigo);

            telefono = telDao.getTelefono(telArea);
            usuario = userDao.getUsuario(telefono.getUsuarios().getIdUsuario());
            if (usuario != null) {
                mensaje = null;
                String sesUser = telefono.getTelefonoArea();
                sesion.setAttribute("usuario", sesUser);
                mav.setViewName("recuperar/recuperarPhone");
            } else {
                mensaje = "No account was found associated with the phone " + telArea;
                mav.setViewName("recuperar/recuperar");
            }

        } catch (Exception e) {
            mensaje = null;
            e.printStackTrace();
            mensaje = "Sorry, service is not available, try later";
            mav.setViewName("recuperar/recuperar");
        }
        mav.addObject("mensaje", mensaje);
        return mav;
    }

    @RequestMapping("recuperarPhone.htm")
    public ModelAndView getConfirm() {

        ModelAndView mav = new ModelAndView();
        try {
            String mensaje = null;
            mensaje = "Enter the code you received on your phone";
            mav.addObject("mensaje", mensaje);
            mav.setViewName("recuperar/recuperarPhone");
            mav.addObject("codigo", codigo);
        } catch (Exception e) {
            mensaje = null;
            e.printStackTrace();
            mensaje = "Sorry, service is not available, try later";
            mav.setViewName("recuperar/recuperarPhone");
        }

        return mav;
    }

    @RequestMapping(value = "validarRecuperarPhone.htm", method = RequestMethod.POST)
    public ModelAndView ValidarPhone(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();

        try {
            String codigo2 = request.getParameter("codigo");
            String mensaje = null;
            sesion = request.getSession();
            if (sesion.getAttribute("usuario") == null) {
                mav.setViewName("login/login");

            } else {
                if (this.getCodigo().compareTo(codigo2) == 0) {

                    Telefonos telefono = new Telefonos();
                    TelefonosDao telDao = new TelefonosDao();
                    String idtel = (sesion.getAttribute("usuario")).toString();
                    System.out.print("el telefono a buscar para ingresar el codigo es " + idtel);
                    telefono = telDao.getTelefono(idtel);
                    telefono.setCodigoConfirm(this.getCodigo());
                    if (telDao.updateTelefono(telefono)) {
                        mav.setViewName("recuperar/setPassword");
                        //this.setCodigo(null);
                    } else {
                        mensaje = "The code is correct but could not be loaded into your account";
                        mav.addObject("mensaje", mensaje);
                        mav.setViewName("telefonos/confirmPhone");
                    }
                } else {

                    mensaje = "The code you entered is not correct, please try again.";
                    mav.addObject("mensaje", mensaje);
                    mav.setViewName("telefonos/confirmPhone");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Sorry, view unavailable at this time";
            mav.addObject("mensaje", mensaje);

            if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                mav.setViewName("login/login");

            } else {
                mav.setViewName("login/login");
            }
        }

        return mav;
    }

    @RequestMapping("recuperarPassword.htm")
    public ModelAndView RecuperarPassword() {
        ModelAndView mav = new ModelAndView();

        try {
            mav.setViewName("recuperar/recuperarPassword");
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Sorry, view unavailable at this time";
            mav.addObject("mensaje", mensaje);

            if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                mav.setViewName("telefonos/confirmPhone");

            } else {
                mav.setViewName("telefonos/confirmPhone");
            }
        }

        return mav;
    }

    @RequestMapping(value = "validarNewPassword.htm", method = RequestMethod.POST)
    public ModelAndView validarNewPassword(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String mensaje = null;
        sesion = request.getSession();
        try {
            if (sesion.getAttribute("usuario") == null) {
                mav.setViewName("login/login");

            } else {

                Telefonos telefono = new Telefonos();
                TelefonosDao telDao = new TelefonosDao();
                String idtel = (sesion.getAttribute("usuario")).toString();
                Cifrar varCifrar = new Cifrar();
                String newPassword = varCifrar.Encriptar(request.getParameter("password"));
                telefono = telDao.getTelefono(idtel);
                UsuariosDao userDao = new UsuariosDao();

                Usuarios usuario = userDao.getUsuario(telefono.getUsuarios().getIdUsuario());
                usuario.setPassword(newPassword);

                if (userDao.updateUsuarios(usuario)) {
                    sesion.invalidate();
                    mav.setViewName("logout");
                    mensaje = "Enter your new data to enter the system";
                    this.createCodigo();
                } else {
                    mensaje = "Sorry, service is not available, try later";
                    mav.addObject("mensaje", mensaje);
                    mav.setViewName("recuperar/setPassword");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            mav.setViewName("recuperar/setPassword");
        }

        mav.setViewName("login/login");
        return mav;
    }

    @RequestMapping(value = "cambiarPassword")
    public ModelAndView cambiarPassword(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String mensaje = null;
        sesion = request.getSession();
        try {
            if (sesion.getAttribute("usuario") == null) {
                mav.setViewName("login/login");

            } else {

                Telefonos telefono = new Telefonos();
                TelefonosDao telDao = new TelefonosDao();
                String idtel = (sesion.getAttribute("usuario")).toString();
                Cifrar varCifrar = new Cifrar();

                telefono = telDao.getTelefono(idtel);
                UsuariosDao userDao = new UsuariosDao();

                Usuarios usuario = userDao.getUsuario(telefono.getUsuarios().getIdUsuario());
                if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                    mav.setViewName("viewsAdmin/cambiarPasswordAdmin");
                    System.out.println("el usuario es administrador");
                } else {
                    mav.setViewName("panel/cambiarPassword");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            mav.setViewName("panel/panel");
        }

        return mav;
    }

    @RequestMapping(value = "validarCambiarPassword", method = RequestMethod.POST)
    public ModelAndView validarcambiarPassword(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String mensaje = null;
        sesion = request.getSession();
        try {
            if (sesion.getAttribute("usuario") == null) {
                mav.setViewName("login/login");

            } else {

                Telefonos telefono = new Telefonos();
                TelefonosDao telDao = new TelefonosDao();
                String idtel = (sesion.getAttribute("usuario")).toString();
                Cifrar varCifrar = new Cifrar();
                String newPassword = varCifrar.Encriptar(request.getParameter("password"));
                String passActual = varCifrar.Encriptar(request.getParameter("passwordActual"));
                telefono = telDao.getTelefono(idtel);
                UsuariosDao userDao = new UsuariosDao();

                Usuarios usuario = userDao.getUsuario(telefono.getUsuarios().getIdUsuario());

                if (passActual.compareTo(usuario.getPassword()) == 0) {
                    System.out.print("Los Password Coinciden");
                    usuario.setPassword(newPassword);
                    if (userDao.updateUsuarios(usuario)) {
                        System.out.print("Se ha actualizado el password");
                        mav.setViewName("panel/cambiarPassword");
                        mensaje = "Change Password Succes";
                        mav.addObject("mensaje", mensaje);
                        this.createCodigo();
                    } else {
                        mensaje = "Failed to update password on server";
                        System.out.print("No se ha podido actualizar el password en el servidor");
                        mav.addObject("mensaje", mensaje);
                        mav.setViewName("panel/cambiarPassword");
                    }

                } else {
                    System.out.print(" El password ingresado es invalido");
                    mav.setViewName("panel/cambiarPassword");
                    mensaje = "The current password is incorrect";
                    mav.addObject("mensaje", mensaje);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            mav.setViewName("recuperar/setPassword");
        }

        return mav;
    }

    @ModelAttribute("codigo")
    public String obtenerCodigo() {

        if (this.getCodigo() == null) {
            this.setCodigo(this.createCodigo());
            return this.getCodigo();

        } else {
            return this.getCodigo();
            // return this.createCodigo();
        }
    }

    public String createCodigo() {
        GeneradorCodigos codigosHelper = new GeneradorCodigos();
        String varCod = codigosHelper.getCodigo();
        //System.out.print(varCod);
        return varCod;
    }

}
