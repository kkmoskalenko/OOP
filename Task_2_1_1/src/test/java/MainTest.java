import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    private static final List<Long> primes = new ArrayList<>(Arrays.asList(
            85560014039911L, 53969358049427L, 37965912369533L, 27726309521783L, 39763764476287L, 33734918239357L,
            31268143467193L, 32092567244539L, 28297070125903L, 66195836698597L, 98078449289039L, 90835148647877L,
            9619752371717L, 77977100823953L, 66862481848787L, 81170712731333L, 53938536260963L, 60458850815369L,
            68871899258807L, 56658883350049L, 89510932713767L, 54961440349603L, 34567984255307L, 66169606908583L,
            14177174039863L, 86075454424081L, 39854851109197L, 23959761753509L, 16725111614581L, 65227468984331L,
            40716723104311L, 15409897215373L, 90660784840751L, 10687664528209L, 16402467031487L, 12843740063059L,
            52793255510167L, 45701865145457L, 70048538708171L, 70863049651717L, 61239101530507L, 54965468539291L,
            4874625613603L, 52226285592763L, 30280535205971L, 85270805623519L, 96555227922863L, 86662616957611L,
            57084204729151L, 61077770797339L, 34024293324137L, 91844484177557L, 11466111643787L, 73293493163887L,
            51159587219087L, 89912812006553L, 1313808414773L, 19168061586647L, 7984994516839L, 62659929687473L,
            73984439629363L, 22702730786429L, 66165308262061L, 86270337507979L, 27395121516479L, 50926887871403L,
            29671405786339L, 43109207555041L, 30647373006193L, 64338805068833L, 47820442095689L, 27049485553661L,
            85396577900297L, 29866297486979L, 89257326453653L, 15827506323821L, 66977911465411L, 92605907160161L,
            23971974608053L, 18347768043113L, 85120083114097L, 31923162811951L, 70120511451539L, 3476626204231L,
            22723804057093L, 42654678647011L, 63769570602503L, 58037744355403L, 88773222993373L, 88568591614793L,
            15742236649427L, 22512594754057L, 11242652447479L, 69931587642601L, 12525163344983L, 26444472387689L,
            10870584658361L, 47436049860307L, 97698817343147L, 42019988529223L
    ));

    private static boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        }

        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    private static boolean threadPool(int nThreads, List<Long> numbers) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(nThreads);
        List<Callable<Boolean>> tasks = new ArrayList<>();

        for (long x : numbers) {
            tasks.add(() -> isPrime(x));
        }

        List<Future<Boolean>> taskResults = es.invokeAll(tasks);
        for (Future<Boolean> res : taskResults) {
            if (!res.get()) {
                return false;
            }
        }
        return true;
    }

    @Test
    void sequential() {
        for (long x : primes) {
            assertTrue(isPrime(x));
        }
    }

    @Test
    void threadPool() throws InterruptedException, ExecutionException {
        assertTrue(threadPool(1, primes));
    }

    @Test
    void threadPool2() throws InterruptedException, ExecutionException {
        assertTrue(threadPool(2, primes));
    }

    @Test
    void threadPool3() throws InterruptedException, ExecutionException {
        assertTrue(threadPool(3, primes));
    }

    @Test
    void threadPool4() throws InterruptedException, ExecutionException {
        assertTrue(threadPool(4, primes));
    }

    @Test
    void threadPool5() throws InterruptedException, ExecutionException {
        assertTrue(threadPool(5, primes));
    }

    @Test
    void threadPool6() throws InterruptedException, ExecutionException {
        assertTrue(threadPool(6, primes));
    }

    @Test
    void threadPool7() throws InterruptedException, ExecutionException {
        assertTrue(threadPool(7, primes));
    }

    @Test
    void threadPool8() throws InterruptedException, ExecutionException {
        assertTrue(threadPool(8, primes));
    }

    @Test
    void parallelStream() {
        boolean res = primes
                .parallelStream()
                .allMatch(MainTest::isPrime);
        assertTrue(res);
    }
}