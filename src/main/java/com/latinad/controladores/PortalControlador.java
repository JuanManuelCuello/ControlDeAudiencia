package com.latinad.controladores;

import com.latinad.entidades.Empresa;
import com.latinad.errores.ErrorServicio;
import com.latinad.repositorio.EmpresaRepositorio;
import com.latinad.servicios.EmpresaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private EmpresaServicio empresaServicio;

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        List<Empresa> empresas = empresaRepositorio.findAll();
        modelo.put("empresas", empresas);

        return "index.html";
    }

    @PostMapping("/crearEmpresa")
    public String crearEmpresa(ModelMap modelo, @RequestParam String nombre, @RequestParam String url) {

        try {
            empresaServicio.crearEmpresa(nombre, url);

        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            return "index.html";
        }

        return "index.html";
    }
}
