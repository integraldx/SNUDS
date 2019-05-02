import java.io.*;
import java.util.*;

public class SortingTest
{
    public static void main(String args[])
    {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    	try
    	{
    		boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
    		int[] value;	// 입력 받을 숫자들의 배열
    		String nums = br.readLine();	// 첫 줄을 입력 받음
    		if (nums.charAt(0) == 'r')
    		{
    			// 난수일 경우
    			isRandom = true;	// 난수임을 표시

    			String[] nums_arg = nums.split(" ");

    			int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
    			int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
    			int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

    			Random rand = new Random();	// 난수 인스턴스를 생성한다.

    			value = new int[numsize];	// 배열을 생성한다.
    			for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
    				value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
    		}
    		else
    		{
    			// 난수가 아닐 경우
    			int numsize = Integer.parseInt(nums);

    			value = new int[numsize];	// 배열을 생성한다.
    			for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
    				value[i] = Integer.parseInt(br.readLine());
    		}

    		// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
    		while (true)
    		{
    			int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

    			String command = br.readLine();

    			long t = System.currentTimeMillis();
    			switch (command.charAt(0))
    			{
    				case 'B':	// Bubble Sort
    					newvalue = DoBubbleSort(newvalue);
    					break;
    				case 'I':	// Insertion Sort
    					newvalue = DoInsertionSort(newvalue);
    					break;
    				case 'H':	// Heap Sort
    					newvalue = DoHeapSort(newvalue);
    					break;
    				case 'M':	// Merge Sort
    					newvalue = DoMergeSort(newvalue);
    					break;
    				case 'Q':	// Quick Sort
    					newvalue = DoQuickSort(newvalue);
    					break;
    				case 'R':	// Radix Sort
    					newvalue = DoRadixSort(newvalue);
    					break;
    				case 'X':
    					return;	// 프로그램을 종료한다.
    				default:
    					throw new IOException("잘못된 정렬 방법을 입력했습니다.");
    			}
    			if (isRandom)
    			{
    				// 난수일 경우 수행시간을 출력한다.
    				System.out.println((System.currentTimeMillis() - t) + " ms");
    			}
    			else
    			{
    				// 난수가 아닐 경우 정렬된 결과값을 출력한다.
    				for (int i = 0; i < newvalue.length; i++)
    				{
    					System.out.println(newvalue[i]);
    				}
    			}

    		}
    	}
    	catch (IOException e)
    	{
    		System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
    	}
	}
	
    private static int[] DoBubbleSort(int[] value)
    {
		int temp;
		for(int i = 0; i < value.length; i++)
		{
			for(int j = 0; j < value.length - i - 1; j++)
			{
				if(value[j] > value[j + 1])
				{
					temp = value[j];
					value[j] = value[j + 1];
					value[j + 1] = temp;
				}
			}
		}
    	return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoInsertionSort(int[] value)
    {
		int temp;
		for(int i = 0; i < value.length; i++)
		{
			temp = value[i];
			int index = 0;
			for(int j = i; j > 0; j--)
			{
				if(value[j - 1] < temp)
				{
					index = j;
					break;
				}
			}
			for(int k = i - 1; k >= index; k--)
			{
				value[k + 1] = value[k];
			}

			value[index] = temp;
		}
		
    	return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoHeapSort(int[] value)
    {
		int temp;
		// Heapify
		{
			for(int i = value.length / 2 - 1; i >= 0; i--)
			{
				if(value[i] < value[2 * i + 1])
				{
					temp = value[i];
					value[i] = value[2 * i + 1];
					value[2 * i + 1] = temp;
				}
				if(2 * i + 2 >= value.length)
				{
					break;
				}
				if(value[i] < value[2 * i + 2])
				{
					temp = value[i];
					value[i] = value[2 * i + 2];
					value[2 * i + 2] = temp;
				}
			}
		}

		// Sort
		for(int index = value.length - 1; index > 0; index--){
			temp = value[0];
			value[0] = value[index];
			value[index] = temp;

			for(int i = 0; i < index / 2; i++)
			{
				if(value[i] < value[2 * i + 1])
				{
					temp = value[i];
					value[i] = value[2 * i + 1];
					value[2 * i + 1] = temp;
				}

				if(2 * i + 2 >= index)
				{
					break;
				}
				if(value[i] < value[2 * i + 2])
				{
					temp = value[i];
					value[i] = value[2 * i + 2];
					value[2 * i + 2] = temp;
				}
			}
		}
    	return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoMergeSort(int[] value)
    {
		// Leaf case
		if(value.length == 1 || value.length == 0)
		{
			return value;
		}

		// subarray sort
		int[] first = new int[value.length / 2];
		for(int i = 0; i < value.length / 2; i++)
		{
			first[i] = value[i];
		}
		first = DoMergeSort(first);

		int[] second = new int[value.length - (value.length / 2)];
		for(int i = 0 / 2; i < value.length - (value.length / 2); i++)
		{
			second[i] = value[value.length / 2 + i];
		}
		second = DoMergeSort(second);

		// Merge
		int[] result = new int[value.length];
		int resultCounter = 0;
		int firstCounter = 0, secondCounter = 0;
		while(firstCounter < first.length && secondCounter < second.length)
		{
			result[resultCounter] = first[firstCounter] < second[secondCounter] ? first[firstCounter++] : second[secondCounter++];
			resultCounter++;
		}
		while(firstCounter < first.length)
		{
			result[resultCounter] = first[firstCounter];
			firstCounter++;
			resultCounter++;
		}
		while(secondCounter < second.length)
		{
			result[resultCounter] = second[secondCounter];
			secondCounter++;
			resultCounter++;
		}

    	return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoQuickSort(int[] value)
    {
		QuickSort(0, value.length, value);
    	return (value);
	}
	
	private static void QuickSort(int first, int end, int[] value)
	{
		if(end - first <= 1)
		{
			return;
		}

		int temp;
		int pivotIndex = (new Random()).nextInt(end - first) + first;
		int pivot = value[pivotIndex];

		int j = first;
		for(int i = first; i < end; i++)
		{
			if(value[i] < pivot)
			{
				temp = value[i];
				value[i] = value[j];
				value[j] = temp;

				if(j == pivotIndex)
				{
					pivotIndex = i;
				}
				j++;
			}
		}
		temp = value[j];
		value[j] = value[pivotIndex];
		value[pivotIndex] = temp;
		pivotIndex = j;


		QuickSort(first, pivotIndex, value);
		QuickSort(pivotIndex + 1, end, value);
	}


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoRadixSort(int[] value)
    {
    	// TODO : Radix Sort 를 구현하라.
    	return (value);
    }
}
