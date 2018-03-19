import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;
 
public class wc {

	static int c_num = 0;
	static int w_num = 0;

    public static TreeMap<Character, Integer> Pross(String str) {
        char[] charArray = str.toCharArray();
 
        TreeMap<Character, Integer> tm = new TreeMap<Character, Integer>();
 
        for (int x = 0; x < charArray.length; x++) {
            if (!tm.containsKey(charArray[x])) {
                tm.put(charArray[x], 1);
            } 
            else {
                int count = tm.get(charArray[x]) + 1;
                tm.put(charArray[x], count);
            }
            c_num++;
        }
        return tm;
    }
 
    public static void words(String str) {
    	char[] charArray = str.toCharArray();
    	for (int i = 0; i < charArray.length; i++)
    	{
    		if (charArray[i] == ' ' || charArray[i] == ',' || charArray[i] == '\n')
    		{
    			w_num++;
    			if (charArray[i] == ' ' && charArray[i - 1] == ',')
    				w_num--;
    			if (charArray[i] == '\n' && 
    				(charArray[i - 1] == '\n' || 
    					charArray[i - 1] == '{' || charArray[i - 1] == '}'))
    				w_num--;
    		}
    	}
    }

    public static void main(String[] args) {

        BufferedReader br = null;
        FileReader fr = null;
        int line = 0;
        int has;
        int input = -1, output = -1;
        String str_c = "-c";
        String str_w = "-w";
        String str_l = "-l";
        String str_o = "-o";
        String str = "";
        String filename = "result.txt";
        String str_output = "";
        boolean out = true;
        StringBuffer sb  = new StringBuffer();

        try {
        	for (int i = 0; i < args.length; i++)
        	{
        		has = args[i].indexOf(".");
        		if (has != -1)
        		{
        			if (input == -1)
        				input = i;
        			else
        				output = i;
        		}
        	}
        	str = System.getProperty("user.dir");
        	str += "\\";
        	fr = new FileReader(str + args[input]);
            br = new BufferedReader(fr);
            while ((str = br.readLine()) != null) {
                sb.append(str);
                sb.append('\n');
                ++line;
            }
            str = sb.toString();
            str = str.substring(0, str.length() - 1);
            System.out.println("\n文件内容:\n" + str);
            TreeMap<Character, Integer> tm = Pross(str);
            System.out.println("\n字符统计结果为：\n" + tm);
            for (int i = 0; i < args.length; i++)
            {
            	if (args[i].equals(str_c))
            	{
            		System.out.println("\n字符总数为：" + c_num);
            		str_output = str_output + args[input] + "," 
            		+ "字符数：" + c_num + "\r\n";
            	}
            	if (args[i].equals(str_w))
            	{
            		words(str);
            		System.out.println("\n单词总数为：" + w_num);
            		str_output = str_output + args[input] + "," 
            		+ "单词数：" + w_num + "\r\n";
            	}
            	if (args[i].equals(str_l))
            	{
            		System.out.println("\n文件行数: " + line);
            		str_output = str_output + args[input] + "," 
            		+ "行数：" + line + "\r\n";
            	}
            	if (args[i].equals(str_o))
            	{
            		if (output == -1)
            		{
            			System.out.println("\n不能单独使用-o参数！\n");
            			out = false;
            		}
            		else
            			filename = args[output];
            	}
            }
        	if (out == true)
        	{
        		File file = new File(filename);
	        	if (!file.exists())
	        		file.createNewFile();
	        	FileWriter fileWriter = new FileWriter(file.getName(), true);
	        	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	        	bufferedWriter.write(str_output);
	        	bufferedWriter.close();
        	}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}