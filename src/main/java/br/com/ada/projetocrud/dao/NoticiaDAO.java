package br.com.ada.projetocrud.dao;


import br.com.ada.projetocrud.model.Noticia;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoticiaDAO {

    private static List<Noticia> noticias = new ArrayList<>();
    private static int proximoId = 1;
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        try {
            noticias = objectMapper.readValue(
                    new File("src/main/java/br/com/ada/projetocrud/database/noticias.json")
                    ,new TypeReference<>() {
                    });

            if(noticias.size() != 0){
                int indexUltimoId = noticias.size()-1;
                proximoId = noticias.get(indexUltimoId).getId()+1;
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void adicionar(Noticia noticia) {
        noticia.setId(proximoId++);
        noticias.add(noticia);
        salvarJson();
    }

    public void atualizar(Noticia noticia) {
        for (int i = 0; i < noticias.size(); i++) {
            Noticia not = noticias.get(i);
            if (not.getId() == noticia.getId()) {
                noticias.set(i, noticia);
                salvarJson();
                break;
            }
        }
    }

    public void remover(int id) {
        noticias.removeIf(film -> film.getId() == id);
        salvarJson();
    }

    public Noticia buscarPorId(int id) {
        return noticias.stream()
                .filter(not -> not.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Noticia> buscarTodos() {
        return noticias;
    }

    public List<Noticia> buscarPrincipais() {
        return noticias.stream()
                .limit(3)
                .collect(Collectors.toList());
    }

    public static void salvarJson() {
        try {
            objectMapper.writeValue(
                    new File("src/main/java/br/com/ada/projetocrud/database/noticias.json"),
                    noticias);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
