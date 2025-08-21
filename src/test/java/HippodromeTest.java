import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class HippodromeTest {

    @Test
    public void emptyHippodromeException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    public void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse " + i, i, i));
        }
        Hippodrome h = new Hippodrome(horses);
        assertEquals(horses, h.getHorses());
    }

    @Test
    public void move(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        new Hippodrome(horses).move();
        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    public void getWinner() {
        Horse h1 = new Horse("Horse 1", 1, 2.999999);
        Horse h2 = new Horse("Horse 2", 2, 2);
        Horse h3 = new Horse("Horse 3", 3, 3);
        Horse h4 = new Horse("Horse 4", 4, 1);
        Hippodrome hippodrome = new Hippodrome(List.of(h1, h2, h3, h4));
        assertEquals(h3, hippodrome.getWinner());
    }
}
