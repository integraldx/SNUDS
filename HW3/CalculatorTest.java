import java.io.*;
import java.util.*;

import javax.lang.model.util.ElementScanner6;




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
				if (input.compareTo("q") == 0 || input.compareTo("Q") == 0)
					break;

				command(input);
			}
			catch (Exception e)
			{
				System.out.print("ERROR\n");
			}
		}
	}

	private static void parseCommand(String input) throws Exception
	{
		Stack<Character> operators = new Stack<Character>();
		String operand = "";
		boolean isShouldOperand = true;
		boolean isShouldOperator = false;

		for(char c : input.toCharArray())
		{
			switch(c)
			{
				case '+':
				if(!operand.equals(""))
					expressions.add(operand);
				operand = "";
				while(true)
				{
					if(operators.isEmpty())
						break;
					char op = operators.peek();
					if(op == '(')
					{
						break;
					}
					operators.pop();
					expressions.add(op + "");
				}
				operators.push('+');
				isShouldOperand = true;
				isShouldOperator = false;
				break;

				case '-':
				if(isShouldOperand)
				{
					operand = "";
					while(true)
					{
						if(operators.isEmpty())
							break;
						char op = operators.peek();
						if(op == '(' || op == '+' || op == '-' || op == '*' ||op == '/' || op == '%' || op == '~')
						{
							break;
						}
						operators.pop();
						expressions.add(op + "");
					}
					operators.push('~');
					isShouldOperand = true;
					isShouldOperator = false;
				}
				else
				{
					if(!operand.equals(""))
						expressions.add(operand);
					operand = "";
					while(true)
					{
						if(operators.isEmpty())
							break;
						char op = operators.peek();
						if(op == '(')
						{
							break;
						}
						operators.pop();
						expressions.add(op + "");
					}
					operators.push('-');
					isShouldOperand = true;
					isShouldOperator = false;
				}
				break;

				case '*':
				if(!operand.equals(""))
					expressions.add(operand);
				operand = "";
				while(true)
				{
					if(operators.isEmpty())
						break;
					char op = operators.peek();
					if(op == '+' || op == '-' || op == '(')
						break;
					operators.pop();
					expressions.add(op + "");
				}
				operators.push('*');
				isShouldOperand = true;
				isShouldOperator = false;
				break;

				case '/':
				if(!operand.equals(""))
					expressions.add(operand);
				operand = "";
				while(true)
				{
					if(operators.isEmpty())
						break;
					char op = operators.peek();
					if(op == '+' || op == '-' || op == '(')
						break;
					operators.pop();
					expressions.add(op + "");
				}
				operators.push('/');
				isShouldOperand = true;
				isShouldOperator = false;
				break;

				case '%':
				if(!operand.equals(""))
					expressions.add(operand);
				operand = "";
				while(true)
				{
					if(operators.isEmpty())
						break;
					char op = operators.peek();
					if(op == '+' || op == '-' || op == '(')
						break;
					operators.pop();
					expressions.add(op + "");
				}
				operators.push('%');
				isShouldOperand = true;
				isShouldOperator = false;
				break;

				case '^':
				if(!operand.equals(""))
					expressions.add(operand);
				operand = "";
				while(true)
				{
					if(operators.isEmpty())
						break;
					char op = operators.peek();
					if(op == '+' || op == '-' || op == '*' || op == '/' || op == '%' || op == '~' || op == '(' || op == '^')
						break;
					operators.pop();
					expressions.add(op + "");
				}
				operators.push('^');
				isShouldOperand = true;
				isShouldOperator = false;
				break;

				case '(':
				operand = "";
				operators.push('(');
				isShouldOperand = true;
				break;

				case ')':
				if(!operand.equals(""))
					expressions.add(operand);
				operand = "";
				while(operators.peek() != '(')
					expressions.add(operators.pop() + "");
				operators.pop();
				isShouldOperand = false;
				break;

				case ' ':
				case '\t':
				if(!operand.equals(""))
				{
					expressions.add(operand);
					operand = "";
					isShouldOperator = true;
					isShouldOperand = false;
					break;
				}
				else
				{
					break;
				}

				default:
				if(isShouldOperator)
				{
					Exception e = new Exception();
					throw e;
				}
				operand = operand + c;
				isShouldOperand = false;
				break;
			}
		}
		if(!operand.equals(""))
			expressions.add(operand);
		while(!operators.isEmpty())
			expressions.add(operators.pop() + "");
	}

	private static long calculate() throws Exception
	{
		Stack<Long> st = new Stack<Long>();
		long temp;
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
				temp = st.pop();
				st.push(st.pop() / temp);
				break;

				case "%":
				temp = st.pop();
				st.push(st.pop() % temp);
				break;

				case "~":
				st.push(-st.pop());
				break;

				case "^":
				temp = st.pop();
				if((st.peek() == 0 && temp == 0) || temp < 0)
				{
					throw new Exception();
				}
				st.push((long)Math.pow(st.pop(), temp));
				break;

				default:
				st.push(Long.parseLong(s));
				break;
			}
		}

		return st.pop();
	}

	private static void command(String input) throws Exception
	{
		expressions.clear();
		parseCommand(input);
		long result = calculate();

		StringBuilder sb = new StringBuilder();
		for(String s : expressions)
		{
			sb.append(s + " ");
		}
		System.out.print(sb.toString() + "\n");
		System.out.print(result + "\n");
	}
}
