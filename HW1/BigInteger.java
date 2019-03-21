import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
///
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "Invalid Input!";
  
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");

    byte[] data = new byte[200];
    
  
  
//    public BigInteger(int i)
//    {
//    }
//  
//    public BigInteger(int[] num1)
//    {
//    }
//  
   public BigInteger(String s)
   {
   }
 
   public BigInteger add(BigInteger big)
   {
   }
 
   public BigInteger subtract(BigInteger big)
   {
   }
 
   public BigInteger multiply(BigInteger big)
   {
   }
 
   @Override
   public String toString()
   {
   }
  
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // implement here
        // parse input
        // using regex is allowed
  
        // One possible implementation
        // BigInteger num1 = new BigInteger(arg1);
        // BigInteger num2 = new BigInteger(arg2);
        // BigInteger result = num1.add(num2);
        // return result;
        int operatorIndex = input.indexOf('+');
        if(operatorIndex == 0)
        {
            operatorIndex = input.indexOf('+', 1);
        }
        if(operatorIndex == -1)
        {
            operatorIndex = input.indexOf('-');
            if(operatorIndex == 0)
            {
                operatorIndex = input.indexOf('-', 1);
            }
            if(operatorIndex == -1)
            {
                operatorIndex = input.indexOf('*');
            }
            else
            {
                if(input.charAt(operatorIndex - 1) == '*')
                {
                    operatorIndex -= 1;
                }
            }
        }
        else
        {
            if(input.charAt(operatorIndex - 1) == '-' || input.charAt(operatorIndex - 1) == '*' )
            {
                operatorIndex -= 1;
            }
        }

        String int1 = input.substring(0, operatorIndex);
        char operator = input.charAt(operatorIndex);
        String int2 = input.substring(operatorIndex + 1);

        try{
            BigInteger bint1 = new BigInteger(int1);
            BigInteger bint2 = new BigInteger(int2);
            BigInteger resultInteger = new BigInteger("0");
        }
        catch (IllegalArgumentException e)
        {
            throw e;
        }
        switch(operator)
        {
            case '+':
                resultInteger = bint1.add(bint2);
                break;
            case '-':
                resultInteger = bint1.subtract(bint2);
                break;
            case '*':
                resultInteger = bint1.multiply(bint2);
                break;
        }

        return resultInteger;
    }
  
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
  
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
  
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
  
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
  
            return false;
        }
    }
  
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
