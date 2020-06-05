package br.com.alura.leilao;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import br.com.alura.leilao.exceptions.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exceptions.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exceptions.UsuarioJaDeuCincoLancesException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LeilaoTest {

    private final Leilao CONSOLE = new Leilao("Console");
    private final Usuario FRANCISCO = new Usuario("Francisco");
    private final double DELTA = 0.0001;

    @Test
    public void deve_DevolverDescricao_QuandoRecebeDescricao() {
        //assertEquals("console", CONSOLE.getDescricao());
        assertThat(CONSOLE.getDescricao(), is(equalTo("Console")));
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(FRANCISCO, 400));
        //assertEquals(400, CONSOLE.getMaiorLance(), 0.0001);
        assertThat(CONSOLE.getMaiorLance(), closeTo(400.00, DELTA));
    }

    /*
    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeMaisdeUmLanceEmOrdemCrescente(){
        CONSOLE.propoe(new Lance(FRANCISCO, 400));
        CONSOLE.propoe(new Lance(new Usuario("Joao"), 600));
        assertEquals(600, CONSOLE.getMaiorLance(), 0.0001);
    }
    @Test
    public void deve_DevolverMenorLance_QuandoRecebeMaisdeUmLanceEmOrdemDecrescente(){
        CONSOLE.propoe(new Lance(FRANCISCO, 600));
        CONSOLE.propoe(new Lance(new Usuario("Joao"), 400));
        assertEquals(400, CONSOLE.getMenorLance(), 0.0001);
    }
    */

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(FRANCISCO, 400.0));
        //assertEquals(400, CONSOLE.getMenorLance(), 0.0001);
        assertThat(CONSOLE.getMenorLance(), is(closeTo(400.0, DELTA)));
    }

    //Test Driven Development
    @Test
    public void deve_DevolverTresMaioresLance_QuandoRecebeExatosTresLances() {
        CONSOLE.propoe(new Lance(FRANCISCO, 300));
        CONSOLE.propoe(new Lance(new Usuario("Joao"), 400));
        CONSOLE.propoe(new Lance(FRANCISCO, 500));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.getTresMaioresLances();
        //assertEquals(3, tresMaioresLancesDevolvidos.size());
//        assertThat(tresMaioresLancesDevolvidos, hasSize(equalTo(3)));
//        assertThat(tresMaioresLancesDevolvidos, hasItem(new Lance(FRANCISCO, 500)));
//        assertThat(tresMaioresLancesDevolvidos, contains(
//                new Lance(FRANCISCO, 500),
//                new Lance(new Usuario("Joao"), 400),
//                new Lance(FRANCISCO, 300)
//
//        ));

        assertThat(tresMaioresLancesDevolvidos, both(Matchers.<Lance>hasSize(3))
                .and(contains(
                        new Lance(FRANCISCO, 500),
                        new Lance(new Usuario("Joao"), 400),
                        new Lance(FRANCISCO, 300)
                )));
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoNaoRecebeLances() {
        List<Lance> tresMaioresLances = CONSOLE.getTresMaioresLances();
        assertEquals(0, tresMaioresLances.size());
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasUmNumero() {
        CONSOLE.propoe(new Lance(FRANCISCO, 300.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.getTresMaioresLances();

        assertEquals(1, tresMaioresLancesDevolvidos.size());
        assertEquals(300, tresMaioresLancesDevolvidos.get(0).getValor(), 0.0001);
    }

    @Test
    public void deve_DevolverOsTresMaioresLances_QuandoRecebeApenasDoisLance() {
        CONSOLE.propoe(new Lance(FRANCISCO, 300.0));
        CONSOLE.propoe(new Lance(new Usuario("Marcos"), 400.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.getTresMaioresLances();

        assertEquals(2, tresMaioresLancesDevolvidos.size());
        assertEquals(300.0, tresMaioresLancesDevolvidos.get(1).getValor(), 0.0001);
        assertEquals(400.0, tresMaioresLancesDevolvidos.get(0).getValor(), 0.0001);
    }

    @Test
    public void deve_DevolverValorZeroParaMaiorLance_QuandoNaoTiverLance() {
        double maiorLanceDevolvido = CONSOLE.getMaiorLance();
        assertEquals(0.0, maiorLanceDevolvido, 0000.1);
    }

    @Test
    public void deve_DevolverValorZeroParaMenorLance_QuandoNaoTiverLance() {
        double menorLanceDevolvido = CONSOLE.getMenorLance();
        assertEquals(0.0, menorLanceDevolvido, 000.1);
    }

    @Test(expected = LanceMenorQueUltimoLanceException.class)
    public void naoDeve_AdicionarLance_QuandoForMenorQueOMaiorLance() {

        CONSOLE.propoe(new Lance(FRANCISCO, 500.0));
        CONSOLE.propoe(new Lance(new Usuario("Marcos"), 400.0));

       /*
        try{
            CONSOLE.propoe(new Lance(new Usuario("Marcos"), 00.0));
            fail("Era esperado uma exception");
        }catch (RuntimeException e){
            assertEquals("Lance Menor que o ultimo lance", e.getMessage());

        }
        */
    }

    @Test(expected = LanceSeguidoDoMesmoUsuarioException.class)
    public void naoDeve_AdicionarLance_QuandoForOMesmoUsuarioDoUltimoLance() {
        CONSOLE.propoe(new Lance(FRANCISCO, 500));
        CONSOLE.propoe(new Lance(new Usuario("Francisco"), 600));

        //Primeira forma de testar com exceptions
       /* try {
            CONSOLE.propoe(new Lance(new Usuario("Francisco"), 600));
            fail();
        }catch (RuntimeException e){
            assertEquals("Mesmo usuario do ultimo lance", e.getMessage());
        }
        */
    }

    @Test(expected = UsuarioJaDeuCincoLancesException.class)
    public void naoDeve_AdicionarLance_QuandoUsuarioDerCincoLances() {

        CONSOLE.propoe(new Lance(new Usuario("Marcos"), 100));
        CONSOLE.propoe(new Lance(new Usuario("Francisco"), 200));
        CONSOLE.propoe(new Lance(new Usuario("Marcos"), 300));
        CONSOLE.propoe(new Lance(new Usuario("Francisco"), 400));
        CONSOLE.propoe(new Lance(new Usuario("Marcos"), 500));
        CONSOLE.propoe(new Lance(new Usuario("Francisco"), 600));
        CONSOLE.propoe(new Lance(new Usuario("Marcos"), 700));
        CONSOLE.propoe(new Lance(new Usuario("Francisco"), 800));
        CONSOLE.propoe(new Lance(new Usuario("Marcos"), 900));
        CONSOLE.propoe(new Lance(new Usuario("Francisco"), 1000));
        CONSOLE.propoe(new Lance(new Usuario("Marcos"), 1100));

        //Primeria forma de usar exceptions
        /*
        try {
            CONSOLE.propoe(new Lance(new Usuario("Marcos"), 1100));
            fail("Era esperado uma excessao");
        }catch (RuntimeException ex){
            assertEquals("Usuario deu cinco lances", ex.getMessage());
        }
        */
    }
}