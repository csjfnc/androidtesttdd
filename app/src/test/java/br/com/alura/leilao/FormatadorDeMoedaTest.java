package br.com.alura.leilao;

import org.junit.Test;

import br.com.alura.leilao.formatter.FormatadorDeMoeda;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class FormatadorDeMoedaTest {

    @Test
    public void deve_FormatarMoeda_QuandoRecebeValorDouble(){
        FormatadorDeMoeda formatadorDeMoeda = new FormatadorDeMoeda();
        String moedaFormatada = formatadorDeMoeda.formata(500.00);

        assertThat(moedaFormatada, is(equalTo("R$ 500,00")));

    }
}
