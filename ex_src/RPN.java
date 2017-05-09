package app.exercise.testing;
import java.util.Stack;
import app.exercise.algebra.*;
public class RPN {
	static Stack<CompRational> stack = new Stack();
	public static void main(String[] args)
	{
		for (int i = 0; i < args.length; i++)
		{
			String c = args[i];
			switch (c)
			{
			case "+":
				func("add");
				break;

			case "-":
				func("sub");
				break;

			case "*":
				func("mul");
				break;

			case "/":
				func("div");
				break;

			default:
				stack.push(new CompRational(Long.parseLong(c), (long)1));
				break;
			}
		}
		System.out.println(stack.pop().toString());
	}

	public static boolean isNumeric(String s)
	{
		return java.util.regex.Pattern.matches("\\d+", s);
	}

 //Operationen implementieren
	private static void func(String operator)
	{
		CompRational[] arr = new CompRational[2];
		arr[0] = stack.pop();
		arr[1] = stack.pop();

		if (operator == "add")
		{
			arr[0].add(arr[1]);
			stack.push(arr[0]);
		}
		if (operator == "sub")
		{
			arr[0].sub(arr[1]);
			stack.push(arr[0]);
		}
		if (operator == "mul")
		{
			arr[0].mul(arr[1]);
			stack.push(arr[0]);
		}
		if (operator == "div")
		{
			arr[0].div(arr[1]);
			stack.push(arr[0]);
		}
	}
}
