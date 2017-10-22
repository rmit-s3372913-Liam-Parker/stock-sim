package ultilities;

import java.util.Random;

public class PinGenerator
{
    /**
     * Generates a pin of 5 digits in length.
     * @return A string representation of the pin generated.
     */
    public static String generatePin()
    {
        return generatePin(5);
    }

    /**
     * Generates a random pin value of length
     * @param length The length of the required pin.
     * @return A string representation of the pin generated.
     */
    public static String generatePin(int length)
    {
        StringBuilder pin = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < length; i++)
        {
            pin.append(random.nextInt(10));
        }
        return pin.toString();
    }
}
