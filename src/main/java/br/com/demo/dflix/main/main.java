package br.com.demo.dflix.main;

import java.util.Scanner;

import br.com.demo.dflix.service.ConsumoApi;

public class main {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=9bf2bfa";

    public void exibeMenu() {
        var nome = leitura.nextLine();
        nome.replace(" ", "+");
		var json = consumo.obterDados(ENDERECO + nome + API_KEY);

    }
}
