import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Find;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {

    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    public void nullMassageException() {
        try {
            new Horse(null, 1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t\t", "\n\n\n\n\n\n"})
    public void blankNullException(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void speedException() {
        String name = "Horse";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, -1, 1));
        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    public void distanceException() {
        String name = "Horse";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, -1));
        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Horse", 1, 1);
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals("Horse", nameValue);
    }

    @Test
    public void getSpeed() {
        double speed = 1.0;
        Horse horse = new Horse("Horse", speed, 1);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void getDistance() {
        double distance = 1.0;
        Horse horse = new Horse("Horse", 1, distance);
        assertEquals(distance, horse.getDistance());
    }

    @Test
    public void zeroDistance() {
        Horse horse = new Horse("Horse", 1);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveUsesGetRandom(){
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("Horse", 1, 1).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 999.999, 0.0})
    public void move(double random) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Horse", 31, 283);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            horse.move();
            assertEquals(283 + 31 +random, horse.getDistance());
        }
    }

}
