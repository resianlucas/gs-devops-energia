package br.com.reciclagem.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.reciclagem.model.Dispositivo;
import br.com.reciclagem.model.Usuario;
import br.com.reciclagem.repository.DispositivoRepository;
import br.com.reciclagem.repository.UsuarioRepository;

@Controller
@RequestMapping("/dispositivos")
public class DispositivoController {

    @Autowired
    DispositivoRepository dispositivoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    // Exibir todos os dispositivos cadastrados
    @GetMapping()
    public ModelAndView index() {
        List<Dispositivo> dispositivos = dispositivoRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("dispositivos");
        modelAndView.addObject("dispositivos", dispositivos);
        return modelAndView;
    }

    // Exibir dispositivos associados a um usuário específico
    @GetMapping("/{id}")
    public ModelAndView get(@PathVariable Long id) {
        List<Dispositivo> dispositivos = dispositivoRepository.findByUsuario_Id(id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("usuario-dispositivos");
        modelAndView.addObject("usuario", usuario.orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
        modelAndView.addObject("dispositivos", dispositivos);
        return modelAndView;
    }


    // Formulário para adicionar um novo dispositivo para um usuário
    @GetMapping("/new/{id}")
    public ModelAndView create(@PathVariable Long id, Dispositivo dispositivo) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o ID: " + id));

        // Configurar o dispositivo com valores iniciais padrão
        dispositivo.setPotencia(1.0);
        dispositivo.setHorasPorDia(1);
        dispositivo.setDiasPorMes(1);

        // Criar o ModelAndView e passar os dados necessários
        ModelAndView modelAndView = new ModelAndView("dispositivo-form");
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("dispositivo", dispositivo);

        return modelAndView;
    }

    // Salvar um novo dispositivo para um usuário
    @PostMapping("/new/{id}")
    public String save(@PathVariable Long id, @Valid Dispositivo dispositivo, BindingResult result, Model model) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (result.hasErrors()) {
            model.addAttribute("usuarioId", usuario.getId());
            model.addAttribute("nomeUsuario", usuario.getName());
            return "dispositivo-form";
        }

        // Associar o dispositivo ao usuário
        dispositivo.setUsuario(usuario);

        // Salvar o dispositivo
        dispositivoRepository.save(dispositivo);

        // Redirecionar para a lista de dispositivos do usuário
        return "redirect:/dispositivos/" + id;
    }    

}
