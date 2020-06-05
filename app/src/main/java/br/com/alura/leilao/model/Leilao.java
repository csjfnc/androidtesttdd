package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alura.leilao.exceptions.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exceptions.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exceptions.UsuarioJaDeuCincoLancesException;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;
    private double maiorLance = 0.0;
    private double menorLance = 0.0;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public void propoe(Lance lance) {
        lanceNaoValido(lance);
        lances.add(lance);
        double valorLance = lance.getValor();
        if (defineMaiorEMenorLanceParaOPrimeiroLance(valorLance)) return;
        Collections.sort(lances);
        calculaMaiorLance(valorLance);

    }

    private boolean defineMaiorEMenorLanceParaOPrimeiroLance(double valorLance) {
        if (lances.size() == 1) {
            maiorLance = valorLance;
            menorLance = valorLance;
            return true;
        }
        return false;
    }

    private void lanceNaoValido(Lance lance) {
        double valorLance = lance.getValor();
        if (lanceForMenorQueOUltimoLance(valorLance)) throw new LanceMenorQueUltimoLanceException();
        //Primeira forma de testar com exceptions
        // throw new RuntimeException("Lance Menor que o ultimo lance");

        if (temLances()) {
            Usuario novoUsuario = lance.getUsuario();
            if (usuarioForMesmoDoUltimoLance(novoUsuario))
                throw new LanceSeguidoDoMesmoUsuarioException();

            //Primeira forma de testar com Exceptions
            // throw new RuntimeException("Mesmo usuario do ultimo lance");
            if (usuarioDeuCincoLances(novoUsuario)) throw new UsuarioJaDeuCincoLancesException();

            //Primeira forma de testar com exceptions
            //throw new RuntimeException("Usuario deu cinco lances");
        }
    }

    private boolean temLances() {
        return !lances.isEmpty();
    }

    private boolean usuarioDeuCincoLances(Usuario novoUsuario) {
        int lancesDoUsuario = 0;
        for (Lance l : lances) {
            Usuario usuarioExistente = l.getUsuario();
            if (usuarioExistente.equals(novoUsuario)) {
                lancesDoUsuario++;
                if (lancesDoUsuario == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean usuarioForMesmoDoUltimoLance(Usuario novoUsuario) {
        Usuario ultimoUsuario = lances.get(0).getUsuario();
        if (novoUsuario.equals(ultimoUsuario))
            return true;
        return false;
    }

    private boolean lanceForMenorQueOUltimoLance(double valorLance) {
        if (maiorLance > valorLance) {
            return true;
        }
        return false;
    }


    private void calculaMenorLance(double valorLance) {
        if (valorLance < menorLance) {
            menorLance = valorLance;
        }
    }

    private void calculaMaiorLance(double valorLance) {
        if (valorLance > maiorLance) {
            maiorLance = valorLance;
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public double getMaiorLance() {
        return maiorLance;
    }

    public double getMenorLance() {
        return menorLance;
    }

    public List<Lance> getTresMaioresLances() {

        int quantidadeMaximaDeLances = lances.size();

        if (quantidadeMaximaDeLances > 3) {
            quantidadeMaximaDeLances = 3;
        }

        return lances.subList(0, quantidadeMaximaDeLances);
    }

    public int quantidadeLances() {
        return lances.size();
    }
}
