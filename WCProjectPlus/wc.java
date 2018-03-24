import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
 
public class wc {

    // 全局变量，用于存储文件路径和参数
    static ArrayList<String> arr_files = new ArrayList<String>();
    static ArrayList<String> arr_inputs = new ArrayList<String>();
    
    // 计算字符数
    public static int chars(String str) {
        char[] charArray = str.toCharArray();
        int c_num = 0;
        for (int i = 0; i < charArray.length; i++)
        {
            c_num++;
        }
        return c_num;
    }

    // 计算单词数
    public static int words(String str) {
    	char[] charArray = str.toCharArray();
        int w_num = 0;
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
        return w_num;
    }

    // 得到当前目录内所有文件（本程序中并没有使用这个方法遍历文件，但是这个方法证实可行）
    public static void getFile(File file) {
        File[] files = file.listFiles();
        for (File a:files) {
            if (!a.isDirectory())
                arr_files.add(a.getAbsolutePath());
            else
                getFile(a);
        }
    }

    public static void main(String[] args) {


        // 定义变量

        // 缓冲区变量
        BufferedReader br = null, e_br = null;
        FileReader fr = null, e_fr = null;
        StringBuffer sb  = new StringBuffer();
        StringBuffer e_sb  = new StringBuffer();

        // 整型变量，用于循环和分支控制
        int line = 0, line_code = 0, line_null = 0, line_comment = 0;
        int input = -1, output = -1, exclude = -1, allfile = -1;
        int file_count = 0;
        int c_num = 0, w_num = 0;

        // 字符串变量，用于存储字符串内容
        String str = "";
        String str_c = "-c";
        String str_w = "-w";
        String str_l = "-l";
        String str_o = "-o";
        String str_a = "-a";
        String str_e = "-e";
        String str_s = "-s";
        String str_read = "";
        String filename = "result.txt";
        String str_output = "";

        // bool变量，用于判断是否满足输出条件
        boolean out = true;

        // 主体
        try {


            // 参数判断
        	for (int i = 0; i < args.length; i++)
        	{
        		if (args[i].indexOf(".") != -1)
        		{
                    if (args[i - 1].indexOf(".") != -1)
                    {
                        arr_inputs.add(args[i]);
                        allfile = i;
                        file_count++;
                    }
                    else if (args[i].indexOf("stopList.txt") != -1)
                        exclude = i;
                    else if (input == -1)
                    {
                        arr_inputs.add(args[i]);
                        input = i;
                        file_count++;
                    }
                    else
                        output = i;
        		}
                if (file_count >= 2)
                    allfile = 1;
        	}

            // 文件读取
        	str = System.getProperty("user.dir");
        	str += "\\";
            if (exclude != -1)
            {
                e_fr = new FileReader(str + args[exclude]);
                e_br = new BufferedReader(e_fr);
                while ((str_read = e_br.readLine()) != null)
                    e_sb.append(str_read);
            }

            str = System.getProperty("user.dir");
            File file_this_dir = new File(str);
            getFile(file_this_dir);
            Iterator<String> iterator1 = arr_files.iterator();
            Iterator<String> iterator2 = arr_inputs.iterator();
            str += "\\";
        
            // 多文件统计主体
            for (int x = 0; x < args.length; x++)
            {
                if (args[x].equals(str_s))
                {
                    if (allfile == -1)
                    {
                        System.out.println("\n-s参数使用错误！请尝试输入单个文件！\n");
                        out = false;
                    }
                    else
                    {
                        // 循环处理多个文件
                        while (iterator2.hasNext())
                        { 

                            // 初始化
                            c_num = 0;
                            w_num = 0;
                            line = 0;
                            line_code = 0;
                            line_null = 0;
                            line_comment = 0;
                            int sb_length = sb.length();
                            sb.delete(0, sb_length);
                            sb_length = e_sb.length();
                            e_sb.delete(0, sb_length);

                            String this_file = iterator2.next();
                            System.out.println(this_file);

                            fr = new FileReader(this_file);
                            br = new BufferedReader(fr);

                            // 文件读取
                            while ((str_read = br.readLine()) != null) 
                            {
                                sb.append(str_read);
                                sb.append('\n');
                                String tempStr = str_read.toString();
                                char[] tempCh = tempStr.toCharArray();
                                int char_num = 0;
                                for (int i = 0; i < tempCh.length; i++)
                                {
                                    char_num++;
                                    if (tempCh[i] == ' ' || tempCh[i] == '\t')
                                        char_num--;
                                }
                                if (char_num < 2)
                                        line_null++;
                                else if (tempStr.indexOf("//") != -1 
                                    || (tempStr.indexOf("/*") != -1 && tempStr.indexOf("*/") != -1))
                                    line_comment++;
                                else
                                    line_code++;
                                line++;
                            }
                            str_read = sb.toString();
                            str_read = str_read.substring(0, str_read.length() - 1);
                            c_num = chars(str_read);
                            w_num = words(str_read);
                            
                            // stopList扣词
                            for (int i = 0; i < args.length; i++)
                            {
                                System.out.println(args[i]);
                                if (args[i].equals(str_e))
                                {
                                    if (exclude == -1)
                                    {
                                        System.out.println("\n不能单独使用-e参数！\n");
                                        out = false;
                                    }
                                    else
                                    {
                                        String[] strArray = str_read.split(" ");
                                        String tempStr = e_sb.toString();
                                        String[] e_str = tempStr.split(" ");
                                        for (int j = 0; j < strArray.length; j++)
                                        {
                                            for (int k = 0; k < e_str.length; k++)
                                            {
                                                System.out.println(strArray[j] + e_str[k]);
                                                if (strArray[j].indexOf(e_str[k]) != -1)
                                                    w_num--;
                                            }

                                        }
                                    }
                                }
                            }

                            // 输出准备
                            for (int i = 0; i < args.length; i++)
                            {
                                if (args[i].equals(str_c))
                                {
                                    // System.out.println("\n字符数：" + c_num);
                                    str_output = str_output + this_file + "," 
                                    + "字符数：" + c_num + "\r\n";
                                }
                                if (args[i].equals(str_w))
                                {
                                    // System.out.println("\n单词数：" + w_num);
                                    str_output = str_output + this_file + ", " 
                                    + "单词数：" + w_num + "\r\n";
                                }
                                if (args[i].equals(str_l))
                                {
                                    // System.out.println("\n行数: " + line);
                                    str_output = str_output + this_file + "," 
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
                                if (args[i].equals(str_a))
                                {
                                    // System.out.println("\n代码行/空行/注释行：" 
                                    //     + line_code + "/" 
                                    //     + line_null + "/" + line_comment);
                                    str_output = str_output + this_file + "," 
                                        + "代码行/空行/注释行: " + line_code + "/" 
                                        + line_null + "/" + line_comment + "\r\n";
                                }
                            }

                            // 输出（若没有指定文件名则输出到result.txt，若没有文件则创建文件）
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
                        }
                    }
                }
            }

            // 单文件处理，原理与多文件一致
            if (allfile == -1)
            {
                // System.out.println(str + args[input]);
                fr = new FileReader(str + args[input]);
                br = new BufferedReader(fr);
                while ((str_read = br.readLine()) != null) 
                {
                    sb.append(str_read);
                    sb.append('\n');
                    String tempStr = str_read.toString();
                    char[] tempCh = tempStr.toCharArray();
                    int char_num = 0;
                    for (int i = 0; i < tempCh.length; i++)
                    {
                        char_num++;
                        if (tempCh[i] == ' ' || tempCh[i] == '\t')
                            char_num--;
                    }
                    if (char_num < 2)
                            line_null++;
                    else if (tempStr.indexOf("//") != -1 
                        || (tempStr.indexOf("/*") != -1 && tempStr.indexOf("*/") != -1))
                        line_comment++;
                    else
                        line_code++;
                    line++;
                }
                str_read = sb.toString();
                str_read = str_read.substring(0, str_read.length() - 1);
                c_num = chars(str_read);
                w_num = words(str_read);

                for (int i = 0; i < args.length; i++)
                {
                    if (args[i].equals(str_e))
                    {
                        if (exclude == -1)
                        {
                            System.out.println("\n不能单独使用-e参数！\n");
                            out = false;
                        }
                        else
                        {
                            String[] strArray = str_read.split(" ");
                            String tempStr = e_sb.toString();
                            String[] e_str = tempStr.split(" ");
                            for (int j = 0; j < strArray.length; j++)
                            {
                                for (int k = 0; k < e_str.length; k++)
                                {
                                    if (strArray[j].indexOf(e_str[k]) != -1)
                                        w_num--;
                                }

                            }
                        }
                    }
                }
                for (int i = 0; i < args.length; i++)
                {
                    if (args[i].equals(str_c))
                    {
                        // System.out.println("\n字符数：" + c_num);
                        str_output = str_output + args[input] + "," 
                        + "字符数：" + c_num + "\r\n";
                    }
                    if (args[i].equals(str_w))
                    {
                        words(str);
                        // System.out.println("\n单词数：" + w_num);
                        str_output = str_output + args[input] + ", " 
                        + "单词数：" + w_num + "\r\n";
                    }
                    if (args[i].equals(str_l))
                    {
                        // System.out.println("\n行数: " + line);
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
                    if (args[i].equals(str_a))
                    {
                        // System.out.println("\n代码行/空行/注释行：" 
                        //     + line_code + "/" 
                        //     + line_null + "/" + line_comment);
                        str_output = str_output + args[input] + "," 
                            + "代码行/空行/注释行: " + line_code + "/" 
                            + line_null + "/" + line_comment + "\r\n";
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