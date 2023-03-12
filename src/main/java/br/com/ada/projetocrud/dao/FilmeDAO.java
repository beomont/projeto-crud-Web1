package br.com.ada.projetocrud.dao;

import br.com.ada.projetocrud.model.Filme;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilmeDAO {

    private static List<Filme> filmes = new ArrayList<>();
    private static int proximoId = 1;
    private static ObjectMapper objectMapper = new ObjectMapper();


    static {
        try {
            filmes = objectMapper.readValue(
                    new File("src/main/java/br/com/ada/projetocrud/database/filmes.json")
                    , new TypeReference<>() {
                    });

            if (filmes.size() != 0) {
                int indexUltimoId = filmes.size() - 1;
                proximoId = filmes.get(indexUltimoId).getId() + 1;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void adicionar(Filme filme) {
        filme.setId(proximoId++);
        filmes.add(filme);

        salvarJson();
    }

    public void atualizar(Filme filme) {
        for (int i = 0; i < filmes.size(); i++) {
            Filme film = filmes.get(i);
            if (film.getId() == filme.getId()) {
                filmes.set(i, filme);
                salvarJson();
                break;
            }
        }
    }

    public void remover(int id) {
        filmes.removeIf(film -> film.getId() == id);
        salvarJson();
    }

    public Filme buscarPorId(int id) {
        return filmes.stream()
                .filter(film -> film.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Filme> buscarTodos() {
        return filmes;
    }

    public List<Filme> buscarPrincipais() {
        Comparator<Filme> filmesLikesComparator = Comparator.comparing(Filme::getLike).reversed();

        System.out.println(filmesLikesComparator.toString());
        return filmes.stream()
                .sorted(filmesLikesComparator)
                .limit(3)
                .collect(Collectors.toList());
    }

    public void atualizarLike(int id) {
        for (int i = 0; i < filmes.size(); i++) {
            Filme film = filmes.get(i);
            if (film.getId() == id) {
                film.setLike(film.getLike() + 1);
                salvarJson();
                break;
            }
        }
    }

    public void atualizarDeslike(int id) {
        for (int i = 0; i < filmes.size(); i++) {
            Filme film = filmes.get(i);
            if (film.getId() == id) {
                if(film.getLike() > 0) {
                    film.setLike(film.getLike() - 1);
                    salvarJson();
                    break;
                }
            }
        }
    }

    public static void salvarJson() {
        try {
            objectMapper.writeValue(
                    new File("src/main/java/br/com/ada/projetocrud/database/filmes.json"),
                    filmes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
