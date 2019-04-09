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
				expressions.add(operand);
				operand = "";
				while(true)
				{
					if(operators.isEmpty())
						break;
					char op = operators.peek();
					operators.pop();
					expressions.add(op + "");
				}
				operators.push('+');
				break;

				case '-':
				// FIXME unary operator "-" not implemented
				expressions.add(operand);
				operand = "";
				while(true)
				{
					if(operators.isEmpty())
						break;
					char op = operators.peek();
					operators.pop();
					expressions.add(op + "");
				}
				operators.push('-');
				break;

				case '*':
				expressions.add(operand);
				operand = "";
				while(true)
				{
					if(operators.isEmpty())
						break;
					char op = operators.peek();
					if(op == '+' || op == '-')
						break;
					operators.pop();
					expressions.add(op + "");
				}
				operators.push('*');
				break;

				case '/':

				case '%':

				case '^':

				case '(':

				case ')':

				default:
				operand = operand + c;
				break;
			}
		}
		expressions.add(operand);
		while(!operators.isEmpty())
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
				st.push(-st.pop() + st.pop());
				break;

				case "*":
				st.push(st.pop() * st.pop());
				break;

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
		expressions.clear();
		input = input.replace(" ", "");
		input = input.replace("\t", "");
		parseCommand(input);

		for(String s : expressions)
		{
			System.out.print(s + " ");
		}
		System.out.println();
		
		int result = calculate();
		System.out.println(result);
	}
}
