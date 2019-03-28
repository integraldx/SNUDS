import java.io.BufferedReader;
import java.io.InputStreamReader;
  
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "Invalid Input!";

    boolean sign = false;

    /**
     * Contains absolute numbers. [0] is lowest digit, [199] is highest digit.
     */
    int[] data = new int[200];
     
    /**
     * Gets string and parse into BigInteger. 
     * 
     * @param stringToParse
     */
    public BigInteger(String stringToParse)
    {
        boolean isSignExplicit = false;
        if(stringToParse.charAt(0) == '+' || stringToParse.charAt(0) == '-')
        {
            isSignExplicit = true;
            if (stringToParse.charAt(0) == '-') 
            {
                sign = true;
            }
            else 
            {
                sign = false;
            }
        }

        for(int i = stringToParse.length() - 1; i >= (isSignExplicit ? 1 : 0); i--)
        {
            data[stringToParse.length() - 1 - i] = (int)(stringToParse.charAt(i) - '0');
        }
    }
 
    /**
     * Adds this and another BigInteger and returns new BigInteger
     * @param rightOperand BigInteger to add
     * @return new BigInteger
     */
    public BigInteger add(BigInteger rightOperand)
    {
        // initialize result
        BigInteger result = new BigInteger("0");

        // check each sign
        if(this.sign != rightOperand.sign)
        {
            // different sign - subtraction

            // evaluate sign
            for(int i = data.length - 1; i >= 0; i--)
            {
                if(this.data[i] == rightOperand.data[i])
                {
                    continue;
                }
                else if(this.data[i] > rightOperand.data[i])
                {
                    result.sign = this.sign;
                    break;
                }
                else
                {
                    result.sign = rightOperand.sign;
                    break;
                }
            }

            // actual calculation
            boolean carry = false;
            if(result.sign == this.sign)
            {
                // this is absolute big
                for(int i = 0; i < data.length; i++)
                {
                    int tempResult = (int)(this.data[i] - rightOperand.data[i]);
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
                // rightOperand is absolute big
                for(int i = 0; i < data.length; i++)
                {
                    int tempResult = (int)(rightOperand.data[i] - this.data[i]);
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
            // equal sign - addition

            // evaluate sign
            if(this.sign && rightOperand.sign)
            {
                result.sign = true;
            }
            else if (!this.sign && !rightOperand.sign)
            {
                result.sign = false;
            }

            // actual calculation
            boolean carry = false;
            for(int i = 0; i < data.length; i++)
            {
                int tempResult = (int)(this.data[i] + rightOperand.data[i] + (carry ? 1 : 0));
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
 
    /**
     * Subtracts big from this. 
     * Acts as sign-flipped addition.
     * @param rightOperand BigInteger to subtract
     * @return new BigInteger
     */
    public BigInteger subtract(BigInteger rightOperand)
    {
        // sign flip
        rightOperand.sign = !rightOperand.sign;

        return add(rightOperand);
    }
 

    /**
     * Multiply this and big.
     * Pretty expensive operation.
     * @param rightOperand BigInteger to multiply
     * @return new BigInteger
     */
    public BigInteger multiply(BigInteger rightOperand)
    {
        // initializing result
        BigInteger result = new BigInteger("0");

        // evaluate sign
        if(this.sign != rightOperand.sign)
        {
            result.sign = true;
        }
        else
        {
            result.sign = false;
        }

        // actual calculation - shift, multiply, add 
        for(int i = 0; i < data.length; i++)
        {
            int carry = 0;
            for(int j = i; j < data.length; j++)
            {
                int tempResult = (int)((this.data[j - i] * rightOperand.data[i]) + carry);
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
  
    /**
     * Gets calculation expression and evaluates into BigInteger.
     * Whitespace between chars is allowed, explicit sign expression is also allowed.
     * @param input String expression of calculation.
     * @return Result BigInteger
     * @throws IllegalArgumentException
     */
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // Filter Whitespace
        input = input.replace(" ", "");

        // Fetch operand
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

        // initializing for calculation
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
            // parse failed
            throw e;
        }

        // calculate
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
