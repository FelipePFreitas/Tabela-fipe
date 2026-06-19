package br.com.alura;

import br.com.alura.service.CarrosService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        CarrosService carrosService = new CarrosService();
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        do {
            System.out.println("**** CONSULTA VEÍCULOS ****");
            System.out.println("1 - CARROS");
            System.out.println("2 - CAMINHÕES");
            System.out.println("3 - MOTOS");
            System.out.println("0 - SAIR");
            System.out.println("___________________________");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    carrosService.consultaMarcasCarros();
                    break;
                case 0:
                    System.out.println("Encerrado");
                    break;
                default:
                    System.out.println("Opção inválida");

            }

        }while (opcao != 0);


    }
}