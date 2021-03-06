/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.entitys.Detalles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.util.httpCuotas;

/**
 *
 * @author intercitydev
 */
@Controller
public class CuotasController {

    HttpSession sesion;
    String sesionUser;
    String mensaje;

    @RequestMapping("cuotas.htm")
    public ModelAndView getCuotas(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            sesion = request.getSession();

            if (sesion.getAttribute("usuario") == null) {

                mav.setViewName("login/login");

            } else {
                String mensaje = null;
                Detalles detalle = (Detalles) sesion.getAttribute("cuenta");

                String country = detalle.getCiudad();
                if (country == null) {
                    country = "Guatemala";
                }
                String amount = "5";
                httpCuotas cuotasHelper = new httpCuotas();
                String resultado = cuotasHelper.getCuotas(country, amount);
                sesionUser = sesion.getAttribute("usuario").toString();
                //Detalles detalle = (Detalles) sesion.getAttribute("cuenta");
                mav.addObject("country", detalle.getCiudad());
                mav.addObject("resultado", resultado);
                mensaje = "Rates";
                mav.addObject("mensaje", mensaje);
                mav.addObject("amount", amount);
                mav.addObject("country", country);

                if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                    mav.setViewName("viewsAdmin/cuotasAdmin");
                    System.out.println("el usuario es administrador");
                } else {
                    mav.setViewName("panel/cuotas");
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

    @RequestMapping(value = "postCuotas.htm", method = RequestMethod.POST)
    public ModelAndView postCuotas(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();
        try {

            sesion = request.getSession();

            if (sesion.getAttribute("usuario") == null) {

                mav.setViewName("login/login");

            } else {
                String country = request.getParameter("country");
                String amount = request.getParameter("amount");

                Detalles detalle = (Detalles) sesion.getAttribute("cuenta");
                System.out.print(detalle.getCiudad());
                httpCuotas cuotasHelper = new httpCuotas();
                String resultado = cuotasHelper.getCuotas(country, amount);
                sesionUser = sesion.getAttribute("usuario").toString();
                //Detalles detalle = (Detalles) sesion.getAttribute("cuenta");
                mav.addObject("country", detalle.getCiudad());
                mav.addObject("resultado", resultado);
                mensaje = "Rates InterCity";
                mav.addObject("mensaje", mensaje);
                mav.addObject("amount", amount);
                mav.addObject("country", country);

                if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                    mav.setViewName("viewsAdmin/cuotasAdmin");
                    System.out.println("el usuario es administrador");
                } else {
                    mav.setViewName("panel/cuotas");
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mensaje = "Sorry, view unavailable at this time";
            mav.addObject("mensaje", mensaje);

            if (sesion.getAttribute("tipoUsuario").toString().compareTo("Administrador") == 0) {
                mav.setViewName("viewsAdmin/cuotasAdmin");
                System.out.println("el usuario es administrador");
            } else {
                mav.setViewName("panel/cuotas");
            }

        }
        return mav;

    }
}
