package com.latinad.servicios;

import com.latinad.entidades.Empresa;
import com.latinad.errores.ErrorServicio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.latinad.repositorio.EmpresaRepositorio;

@Service
public class EmpresaServicio {

    @Autowired
    EmpresaRepositorio empresaRepositorio;

    @Transactional
    public void crearEmpresa(String nombre, String url) throws ErrorServicio {

        validar(nombre, url);

        Empresa empresa = new Empresa();

        empresa.setNombre(nombre);
        empresa.setUrl(url);
        empresa.setAlta(true);

        empresaRepositorio.save(empresa);
    }

    @Transactional
    public void modificarEmpesa(String id, String nombre, String url, Boolean alta) throws ErrorServicio {

        validar(nombre, url);

        Optional<Empresa> respuesta = empresaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Empresa empresa = respuesta.get();

            empresa.setNombre(nombre);
            empresa.setUrl(url);
            empresa.setAlta(alta);

            empresaRepositorio.save(empresa);
        }
    }

    @Transactional
    public void habilitar(String id) throws ErrorServicio {
        Optional<Empresa> respuesta = empresaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Empresa empresa = respuesta.get();
            empresa.setAlta(true);
            empresaRepositorio.save(empresa);
        } else {
            throw new ErrorServicio("No se encontró la empresa solicitada");

        }

    }
    
    
    @Transactional
    public void deshabilitar(String id) throws ErrorServicio {
        Optional<Empresa> respuesta = empresaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Empresa empresa = respuesta.get();
            empresa.setAlta(false);
            empresaRepositorio.save(empresa);
        } else {
            throw new ErrorServicio("No se encontró la empresa solicitada");

        }

    }

    @Transactional
    private void validar(String nombre, String url) throws ErrorServicio {

        if (nombre.isEmpty()) {
            throw new ErrorServicio("ERROR: El Nombre de la empresa no puede estar vacio");
        }

        if (url.contains("http://latinad.camdata.co/widget/dashboard/") == false) {
            throw new ErrorServicio("ERROR: La URL es incorrecta o se encuentra vacía");
        }

    }

}
