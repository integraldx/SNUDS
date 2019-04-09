import java.io.*;
import java.util.*;



public class CalculatorTest
{
	static Vector<String> expressions = new Vector<String>();
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
				System.out.println("ERROR");
			}
		}
	}

	private static void parseCommand(String input)
	{
		Stack<Character> operators = new Stack<Character>();
		String operand = "";


		for(char c : input.toCharArray())
		{
			switch(c)
			{
				case '+':
				while(true)
				{
					if(operators.isEmpty())
						break;
					char op = operators.peek();
					if(op == '+' || op == '-')
					{
						break;
					}
					else
					{
						operators.pop();
						expressions.add(op + "");
					}
				}
				operators.push('+');
				expressions.add(operand);
				operand = "";
				break;

				case '-':

				case '*':

				case '/':

				case '%':

				case '^':

				case '(':

				case ')':

				default:
				operand = operand + c;
			}
		}
		expressions.add(operand);
		expressions.add(operators.pop() + "");
	}

	private static int calculate()
	{
		Stack<Integer> st = new Stack<Integer>();
		for(String s: expressions)
		{
			switch(s)
			{
				case "+":
				st.push(st.pop() + st.pop());
				break;

				case "-":

				case "*":

				case "/":

				case "%":

				case "~":

				case "^":

				case "(":

				case ")":

				default:
				st.push(Integer.parseInt(s));
				break;
			}
		}

		return st.pop();
	}

	private static void command(String input)
	{
		input = input.replace(" ", "");
		input = input.replace("\t", "");
		parseCommand(input);
		
		int result = calculate();
		System.out.println(result);
	}
}
