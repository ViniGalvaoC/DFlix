package br.com.demo.dflix.main;

import java.util.*;

import br.com.demo.dflix.model.DadosSerie;
import br.com.demo.dflix.model.DadosTemporadaSerie;
import br.com.demo.dflix.service.ConsumoApi;
import br.com.demo.dflix.service.ConverteDados;

public class Principal {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=9bf2bfa";

    public void exibeMenu() {
        var nomeSerie = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporadaSerie> listaTemporadas = new ArrayList<>();
        for (int i = 1; i <= dadosSerie.qtdTemporadas(); i++) {
            json = consumo.obterDados(
                    ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporadaSerie dadosTemporadaSerie =
                    conversor.obterDados(json, DadosTemporadaSerie.class);
            listaTemporadas.add(dadosTemporadaSerie);
        }
        listaTemporadas.forEach(System.out::println);
    }
}
