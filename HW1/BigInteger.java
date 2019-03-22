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

    boolean sign = false;
    int[] data = new int[200];
     
    public BigInteger(String s)
    {
        boolean isSignExplicit = false;
        if(s.charAt(0) == '+' || s.charAt(0) == '-')
        {
            isSignExplicit = true;
            if (s.charAt(0) == '-') 
            {
                sign = true;
            }
            else 
            {
                sign = false;
            }
        }

        for(int i = s.length() - 1; i >= (isSignExplicit ? 1 : 0); i--)
        {
            data[s.length() - 1 - i] = (int)(s.charAt(i) - '0');
        }
    }
 
    public BigInteger add(BigInteger big)
    {
        BigInteger result = new BigInteger("0");
        if(this.sign != big.sign)
        {
            for(int i = data.length - 1; i >= 0; i--)
            {
                if(this.data[i] == big.data[i])
                {
                    continue;
                }
                else if(this.data[i] > big.data[i])
                {
                    result.sign = this.sign;
                    break;
                }
                else
                {
                    result.sign = big.sign;
                    break;
                }
            }

            boolean carry = false;

            if(result.sign == this.sign)
            {
                for(int i = 0; i < data.length; i++)
                {
                    int tempResult = (int)(this.data[i] - big.data[i]);
                    if(carry)
                    {
                        tempResult--;
                    }
                    if(tempResult < 0)
                    {
                        carry = true;
                        tempResult += 10;
                    }
                    else
                    {
                        carry = false;
                    }
                    result.data[i] = tempResult;
                }
            }
            else
            {
                for(int i = 0; i < data.length; i++)
                {
                    int tempResult = (int)(big.data[i] - this.data[i]);
                    if(carry)
                    {
                        tempResult--;
                    }

                    if(tempResult < 0)
                    {
                        carry = true;
                        tempResult += 10;
                    }
                    else
                    {
                        carry = false;
                    }
                    result.data[i] = tempResult;
                }
            }
        }
        else
        {
            if(this.sign && big.sign)
            {
                result.sign = true;
            }
            else if (!this.sign && !big.sign)
            {
                result.sign = false;
            }

            boolean carry = false;
            for(int i = 0; i < data.length; i++)
            {
                int tempResult = (int)(this.data[i] + big.data[i] + (carry ? 1 : 0));
                if(tempResult / 10 != 0)
                {
                    carry = true;
                }
                else{
                    carry = false;
                }
                result.data[i] = (int)(tempResult % 10);
            }
        }

        return result;
    }
 
    public BigInteger subtract(BigInteger big)
    {
        big.sign = !big.sign;
        return add(big);
    }
 
    public BigInteger multiply(BigInteger big)
    {
        BigInteger result = new BigInteger("0");

        if(this.sign != big.sign)
        {
            result.sign = true;
        }
        else
        {
            result.sign = false;
        }

        for(int i = 0; i < data.length; i++)
        {
            int carry = 0;
            for(int j = i; j < data.length; j++)
            {
                int tempResult = (int)((this.data[j - i] * big.data[i]) + carry);
                carry = (int)((tempResult + result.data[j]) / 10);
                result.data[j] = (int)((tempResult + result.data[j]) % 10);
            }
        }

        return result;
    }
 
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(sign ? "-" : "");

        boolean notHeadFlag = true;
        for(int i = 0; i < data.length; i++)
        {
            int b = data[data.length - 1 - i];

            if(notHeadFlag && (b == 0) && i != data.length - 1)
            {
                continue;
            }
            else
            {
                notHeadFlag = false;
                sb.append((char)(b + '0'));
            }
        }

        if(sb.length() == 2 && sb.charAt(1) == '0' && sb.charAt(0) == '-')
        {
            sb.deleteCharAt(0);
        }

        return sb.toString();
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

        input = input.replace(" ", "");
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
        BigInteger bint1;
        BigInteger bint2;
        BigInteger resultInteger;

        try{
            bint1 = new BigInteger(int1);
            bint2 = new BigInteger(int2);
            resultInteger = new BigInteger("0");
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
