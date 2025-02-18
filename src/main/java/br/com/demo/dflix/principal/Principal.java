package br.com.demo.dflix.principal;

import br.com.demo.dflix.model.DadosFilme;
import br.com.demo.dflix.model.DadosSerie;
import br.com.demo.dflix.model.DadosTemporada;
import br.com.demo.dflix.model.Episodio;
import br.com.demo.dflix.model.Filme;
import br.com.demo.dflix.model.Serie;
import br.com.demo.dflix.repository.FilmeRepository;
import br.com.demo.dflix.repository.SerieRepository;
import br.com.demo.dflix.service.ConsumoApi;
import br.com.demo.dflix.service.ConverteDados;
import enums.Categoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private int OPCAO = -1;
    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private List<DadosFilme> dadosFilme = new ArrayList<>();
    private SerieRepository serieRepository;
    private FilmeRepository filmeRepository;
    private List<Serie> series = new ArrayList<>();
    private List<Filme> filmes = new ArrayList<>();

    public Principal(SerieRepository serieRepository,FilmeRepository filmeRepository){
        this.serieRepository = serieRepository;
        this.filmeRepository = filmeRepository;
    }

    public void exibeMenu() {
        do {
            var menu = """
                    1 - Buscar séries web
                    2 - Buscar episódios web
                    3 - Listar séries buscadas
                    4 - Buscar série por título
                    5 - Buscar filme web
                    6 - Listar filmes buscados
                    7 - Buscar filme por título
                    8 - Buscar filme por gênero
                    9 - Buscar filme com nota maior que
                    10 - Buscar série por gênero
                    11 - Buscar série com nota maior que
                    0 - Sair
                    """;

            System.out.println(menu);
            OPCAO = leitura.nextInt();
            leitura.nextLine();

            switch (OPCAO) {
                case 1:
                try {
                    buscarSerieWeb();
                } catch (Exception e) {
                    System.out.println("Serie não encontrada na API");
                }
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                try {
                    buscarFilmeWeb();
                } catch (Exception e) {
                    System.out.println("Filme não encontrada na API");
                }
                    break;
                case 6:
                    listarFilmesBuscados();
                    break;
                case 7:
                    buscarFilmePorTitulo();
                    break;
                case 8:
                    buscarFilmePorGenero();
                    break;
                case 9:
                    buscarFilmePorNotaMaiorQue();
                    break;
                case 10:
                    buscarSeriePorGenero();
                    break;
                case 11:
                    buscarSeriePorNotaMaiorQue();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (OPCAO != 0);

    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        dadosSeries.add(dados);
        serieRepository.save(new Serie(dados));
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome");
        var nomeSerie = leitura.nextLine();
        var serie = series.stream().filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase())).findFirst();

        if(serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);
            var episodios = temporadas.stream().flatMap(temporada -> temporada.episodios().stream().map(episodio-> new Episodio(temporada.numero(),episodio))).collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada);
        }else{
            System.out.println("Serie não encontrada!");
        }
        
    }

    private void listarSeriesBuscadas() {
        series = serieRepository.findAll();
        series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
    }

    private void buscarSeriePorTitulo(){
        System.out.println("Escolha a série pelo nome: ");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serieBuscada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if(serieBuscada.isPresent()){
            System.out.println("Dados da série: " + serieBuscada.get());
        }else{
             System.out.println("Série não encontrada");
        }
    }

    private void buscarSeriePorGenero(){
        System.out.println("Escolha as séries por gênero: ");
        var genero = leitura.nextLine();
        Optional<List<Serie>> seriesBuscadas = serieRepository.findByGeneroEquals(Categoria.valueOf(genero.toUpperCase()));

        if(seriesBuscadas.isPresent()){
            var seriesQ = seriesBuscadas.get();
            if(seriesQ.isEmpty()){
                System.out.println("Séries com avaliação maior que %s não encontrados".formatted(genero));
            }
            seriesQ.forEach(s-> System.out.println("Dados da série: " + s));
        }
    }

    private void buscarSeriePorNotaMaiorQue(){
        System.out.println("Escolha as séries por avaliação maior que: ");
        var avaliacao = leitura.nextLine();
        Optional<List<Serie>> seriesBuscados = serieRepository.findByAvaliacaoGreaterThanEqual(Double.valueOf(avaliacao));

        if(seriesBuscados.isPresent()){
            var seriesQ = seriesBuscados.get();
            if(seriesQ.isEmpty()){
                System.out.println("Séries com avaliação maior que %s não encontrados".formatted(avaliacao));
            }
            seriesQ.forEach(s-> System.out.println("Dados da série: " + s));
        }
    }

    private void buscarFilmeWeb() {
        DadosFilme dados = getDadosFilme();
        dadosFilme.add(dados);
        filmeRepository.save(new Filme(dados));
        System.out.println(dados);
    }

    private DadosFilme getDadosFilme() {
        System.out.println("Digite o nome do filme para busca");
        var nomeFilme = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeFilme.replace(" ", "+") + API_KEY);
        DadosFilme dados = conversor.obterDados(json, DadosFilme.class);
        return dados;
    }

    private void listarFilmesBuscados() {
        filmes = filmeRepository.findAll();
        filmes.stream().sorted(Comparator.comparing(Filme::getGenero)).forEach(System.out::println);
    }

    private void buscarFilmePorTitulo(){
        System.out.println("Escolha o filme pelo nome: ");
        var nomeFilme = leitura.nextLine();
        Optional<Filme> filmeBuscado = filmeRepository.findByTituloContainingIgnoreCase(nomeFilme);

        if(filmeBuscado.isPresent()){
            System.out.println("Dados do filme: " + filmeBuscado.get());
        }else{
             System.out.println("Filme não encontrado");
        }
    }

    private void buscarFilmePorGenero(){
        System.out.println("Escolha os filmes por gênero: ");
        var genero = leitura.nextLine();
        Optional<List<Filme>> filmesBuscados = filmeRepository.findByGeneroEquals(Categoria.valueOf(genero.toUpperCase()));

        if(filmesBuscados.isPresent()){
            var filmesQ = filmesBuscados.get();
            if(filmesQ.isEmpty()){
                System.out.println("Séries com avaliação maior que %s não encontrados".formatted(genero));
            }
            filmesQ.forEach(s-> System.out.println("Dados do filme: " + s));
        }
    }

    private void buscarFilmePorNotaMaiorQue(){
        System.out.println("Escolha os filmes por avaliação maior que: ");
        var avaliacao = leitura.nextLine();
        Optional<List<Filme>> filmesBuscados = filmeRepository.findByAvaliacaoGreaterThanEqual(Double.valueOf(avaliacao));

        if(filmesBuscados.isPresent()){
            var filmesQ = filmesBuscados.get();
            if(filmesQ.isEmpty()){
                System.out.println("Séries com avaliação maior que %s não encontrados".formatted(avaliacao));
            }
            filmesQ.forEach(s-> System.out.println("Dados do filme: " + s));
        }
    }
}