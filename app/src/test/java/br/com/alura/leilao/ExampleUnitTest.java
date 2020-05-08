package br.com.alura.leilao;

import org.junit.Test;

import java.util.List;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private final Leilao CONSOLE = new Leilao("console");
    private final Usuario FRANCISCO = new Usuario("Francisco");

    @Test
    public void deve_DevolverDescricao_QuandoRecebeDescricao(){
        assertEquals("console", CONSOLE.getDescricao());
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeApenasUmLance(){
        CONSOLE.propoe(new Lance(FRANCISCO, 400));
        assertEquals(400, CONSOLE.getMaiorLance(), 0.0001);
    }

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

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeApenasUmLance(){
        CONSOLE.propoe(new Lance(FRANCISCO, 400));
        assertEquals(400, CONSOLE.getMenorLance(), 0.0001);
    }

    //Test Driven Development
    @Test
    public void deve_DevolverTresMaioresLance_QuandoRecebeExatosTresLances(){
        CONSOLE.propoe(new Lance(FRANCISCO, 300));
        CONSOLE.propoe(new Lance(new Usuario("Joao"), 400));
        CONSOLE.propoe(new Lance(FRANCISCO, 500));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.getTresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidos.size());
    }
}