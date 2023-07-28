import org.junit.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Stream;

public class ReadLargeFile {

    /**
     * 用于查找衣服的所有类别信息
     * @throws IOException
     */
    @Test
    public void findCategoryOfCloth() throws IOException {
        //String filePath = "path_to_your_8gb_text_file.txt";
        String filePath = "D:\\服装数据集\\Alibaba-Fashion\\item_data.txt\\item_data.txt";

        //创建一个Hashset，用于保存类别信息
        HashSet<String> hashSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.lines().forEach(line -> {
                // Process each line here
                String categoryId = line.split(",")[1];
                hashSet.add(categoryId);
                //System.out.println(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(hashSet.size()); //这个值输出的75 说明只有75个类别信息

        //把类别信息保留到文本文件当中
        String filePath2 = "D:\\服装数据集\\Alibaba-Fashion\\item_data.txt\\category_data2.txt";
        PrintWriter writer = new PrintWriter(new FileWriter(filePath2));

        Iterator<String> iterator = hashSet.iterator();
        while (iterator.hasNext()){
            //应该把这个类别信息保存在磁盘上面
            String dataId = iterator.next();
            writer.println(dataId);
            System.out.println(dataId);
        }
        writer.close();
    }

    /**
     * 用于读取item_data.txt数据，只保留时尚单品和对应的类别两条数据
     */
    @Test
    public void constructItemAndCategoryData() throws IOException {
        String filePath = "D:\\服装数据集\\Alibaba-Fashion\\item_data.txt\\item_data.txt";

        String fileOutputPath = "D:\\服装数据集\\Alibaba-Fashion\\item_data.txt\\item_category.txt";
        PrintWriter pw = new PrintWriter(new FileWriter(fileOutputPath));
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.lines().forEach(line -> {
                // Process each line here
                String itemId = line.split(",")[0]; //读取时尚单品的id信息
                String categoryId = line.split(",")[1];//读取单品对应的类别id
                pw.println(itemId+","+categoryId);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查找用户和所有的套装信息【可以理解为，用户所购买过的所有套装，
     * 既然是套装，里面会包含着上衣、下衣或其他类型的服装】
     * 思路：
     * 1:先从用户数据当中把用户和套装id封装在一起
     * 2：根据套装id把所有的单品找到
     * 3：利用套装当中所有的时尚单品，将其整体替换用户-套装id数据当中的套装id
     */
    @Test
    public void findUserAndOutfitData() throws IOException {
        String filePath = "D:\\服装数据集\\Alibaba-Fashion\\user_data.txt\\user_data.txt";

        String fileOutputPath = "D:\\服装数据集\\Alibaba-Fashion\\user_data.txt\\user_outfit.txt";
        PrintWriter pw = new PrintWriter(new FileWriter(fileOutputPath));
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.lines().forEach(line -> {
                // Process each line here
                String userId = line.split(",")[0]; //读取时尚单品的id信息
                String outfitId = line.split(",")[2];//读取单品对应的类别id
                pw.println(userId + "," + outfitId);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试字符串切割
     * 测试字符串为：12d52,0012;0013;0014,21df
     */
    @Test
    public void testStringSplit(){
        String str = "12d52,0012;0013;0014,21df";
        String userId = str.split(",")[0];
        String outfitId = str.split(",")[2];
        System.out.println(userId);
        System.out.println(outfitId);
    }

    /**
     * 根据user和outfit的数据表，以及outfit和item的数据表
     * 建立user和item的数据表
     * 【这个函数应该会比较耗时，因为你要拿着outfit的id号到outfit和item的映射关系表当中
     * 找到所有的item】
     * 这个操作最好是写两个方法，一个方法专门拿着outfit的id到outfitId-itemId1，itemId2...itemId3
     * 当中找到所有的时尚单品，并且把这个时尚单品给返回出来
     */
    @Test
    public void constructUserAndItem() throws IOException {
        String filePath = "D:\\服装数据集\\Alibaba-Fashion\\user_data.txt\\user_data.txt";
        String fileOutputPath = "D:\\服装数据集\\Alibaba-Fashion\\user_data.txt\\user_item.txt";
        PrintWriter pw = new PrintWriter(new FileWriter(fileOutputPath));
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.lines().forEach(line -> {
                // Process each line here
                String userId = line.split(",")[0]; //读取时尚单品的id信息
                String outfitId = line.split(",")[2];//读取单品对应的类别id
                //查询outfitId对应的所有时尚单品，然后写到文件当中
                try {
                    String allItems = getAllItems(outfitId);
                    pw.println(userId + "," + allItems);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAllItems(String outfitId) throws IOException { //

        String outfitDataPath = "D:\\服装数据集\\Alibaba-Fashion\\outfit_data.txt\\outfit_data.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(outfitDataPath));

        String line = bufferedReader.readLine();
        while (line != null) {
            //System.out.println(line);
            // read next line
            String[] data = line.split(",");
            if (data[0].equals(outfitId)) {
                return data[1];
            }
            line = bufferedReader.readLine();
        }
        return "没找到...";
    }

    @Test
    public void filePathTest(){
        String filePath = "/E:/houlei/DemoTest.jar/";
        String substring = filePath.substring(1, filePath.lastIndexOf("/") + 1);
        System.out.println(substring);
    }


}

