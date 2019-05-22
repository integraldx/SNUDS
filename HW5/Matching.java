import java.io.*;
import java.util.*;

public class Matching
{
	static StringMatcher sm = new StringMatcher();
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input)
	{
		String value = input.substring(2);
		switch (input.toCharArray()[0])
		{
			case '<':
				try {
					Scanner sc = new Scanner(new File(value));
					String content = "";
					while(sc.hasNextLine())
					{
						content += sc.nextLine() + "\n";
					}
					sc.close();
					System.err.println(content);
					sm.SetOriginString(content);
				}
				catch (FileNotFoundException e)
				{
					System.err.println("File not found.");
				}
				break;
			case '?':
				var patternResult = sm.SearchPattern(value);

				if (patternResult.size() == 0)
				{
					patternResult.add(new Tuple<Integer, Integer>(0, 0));
				}
				for (var i : patternResult)
				{
					System.out.print("(" + i.x + ", " + i.y + ") ");
				}
				System.out.println();
				break;
			case '@':
				try
				{
					var hashResult = sm.SearchByHash(Integer.parseInt(value));

					if (hashResult.size() == 0)
					{
						hashResult.add("EMPTY");
					}

					for (var i : hashResult)
					{
						System.out.print(i + " ");
					}

					System.out.println();
				}
				catch (NumberFormatException e)
				{
					System.err.println("Invalid format");
				}

				break;
		}
	}
}
