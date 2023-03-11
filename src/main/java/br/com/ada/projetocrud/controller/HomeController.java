package br.com.ada.projetocrud.controller;

import br.com.ada.projetocrud.dao.FilmeDAO;
import br.com.ada.projetocrud.dao.NoticiaDAO;
import br.com.ada.projetocrud.model.Filme;
import br.com.ada.projetocrud.model.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private FilmeDAO filmeDAO;
    @Autowired
    private NoticiaDAO noticiaDAO;


    @GetMapping
    public String listarHome(Model model) {
        List<Filme> listaFilmes = filmeDAO.buscarPrincipais();
        List<Noticia> listaNoticias = noticiaDAO.buscarPrincipais();
        model.addAttribute("filmes", listaFilmes);
        model.addAttribute("noticias", listaNoticias);
        return "home_listar";
    }

    //FILME
    @GetMapping("/todos-filmes")
    public String listarTodosFilmes(Model model) {
        List<Filme> lista = filmeDAO.buscarTodos();
        model.addAttribute("filmes", lista);
        return "filme_listar";
    }

    @GetMapping("/editar-filme/{id}")
    public String editarFilme(@PathVariable int id, Model model) {
        Filme filme = filmeDAO.buscarPorId(id);
        model.addAttribute("filme", filme);
        return "filme_editar";
    }

    @PostMapping("/editar-filme")
    public String atualizarFilme(Filme filme) {
        filmeDAO.atualizar(filme);
        return "redirect:/home/todos-filmes";
    }

    @GetMapping("todos-filmes/like/{id}")
    public String atualizarLikeFilme(@PathVariable int id) {
        filmeDAO.atualizarLike(id);
        return "redirect:/home/todos-filmes";
    }

    @GetMapping("todos-filmes/deslike/{id}")
    public String atualizarDeslikeFilme(@PathVariable int id) {
        filmeDAO.atualizarDeslike(id);
        return "redirect:/home/todos-filmes";
    }

    @GetMapping("/remover-filme/{id}")
    public String removerFilme(@PathVariable int id) {
        filmeDAO.remover(id);
        return "redirect:/home/todos-filmes";
    }

    @GetMapping("/novo-filme")
    public String novoFilme(Model model) {
        model.addAttribute("filme", new Filme());
        return "filme_novo";
    }

    @PostMapping("/novo-filme")
    public String adicionarFilme(Filme filme) {
        filmeDAO.adicionar(filme);
        return "redirect:/home/todos-filmes";
    }

    //NOTICIA

    @GetMapping("/todas-noticias")
    public String listarTodasNoticias(Model model) {
        List<Noticia> lista = noticiaDAO.buscarTodos();
        model.addAttribute("noticias", lista);
        return "noticia_listar";
    }

    @GetMapping("/editar-noticia/{id}")
    public String editarNoticia(@PathVariable int id, Model model) {
        Noticia noticia = noticiaDAO.buscarPorId(id);
        model.addAttribute("noticia", noticia);
        return "noticia_editar";
    }

    @PostMapping("/editar-noticia")
    public String atualizarNoticia(Noticia noticia) {
        noticiaDAO.atualizar(noticia);
        return "redirect:/home/todas-noticias";
    }

    @GetMapping("/remover-noticia/{id}")
    public String removerNoticia(@PathVariable int id) {
        noticiaDAO.remover(id);
        return "redirect:/home/todas-noticias";
    }

    @GetMapping("/nova-noticia")
    public String novaNoticia(Model model) {
        model.addAttribute("noticia", new Noticia());
        return "noticia_nova";
    }

    @PostMapping("/nova-noticia")
    public String adicionarNoticia(Noticia noticia) {
        noticiaDAO.adicionar(noticia);
        return "redirect:/home/todas-noticias";
    }


}