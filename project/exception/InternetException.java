package exception;

public class InternetException extends RuntimeException
{
    public InternetException()
    {
        super("Could not connect to the internet! Check your network settings and try again.");
    }
}
