package com.feiwanghub.subcontroller.jdk;

import java.util.*;
import java.util.stream.Collectors;

public class LoopListMapStreamArraysDemo {

    public void loopMap(){
        // 循环遍历Map的4中方法
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 2);

        // 1. entrySet遍历，在键和值都需要时使用（最常用）
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }

        // 2. 通过keySet或values来实现遍历,性能略低于第一种方式
        // 遍历map中的键
        for (Integer key : map.keySet()) {
            System.out.println("key = " + key);
        }
        // 遍历map中的值
        for (Integer value : map.values()) {
            System.out.println("key = " + value);
        }

        // 3. 使用Iterator遍历
        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = it.next();
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }

        // 4. java8 Lambda
        // java8提供了Lambda表达式支持，语法看起来更简洁，可以同时拿到key和value，
        // 不过，经测试，性能低于entrySet,所以更推荐用entrySet的方式
        map.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
    }

    public void loopList(){
        List<String> list = new ArrayList<String>();
        list.add("菜");
        list.add("鸟");
        list.add("教");
        list.add("程");
        list.add("www.runoob.com");

        // 使用iterator遍历
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String value = it.next();
            System.out.println(value);
        }

        // 使用传统for循环进行遍历
        for (int i = 0, size = list.size(); i < size; i++) {
            String value = list.get(i);
            System.out.println(value);
        }

        // 使用增强for循环进行遍历
        for (String value : list) {
            System.out.println(value);
        }
    }

    public void streamApi(){
        //在 Java 8 中, 集合接口有两个方法来生成流：
        //stream() − 为集合创建串行流。
        //parallelStream() − 为集合创建并行流
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        //forEach Stream 提供了新的方法 'forEach' 来迭代流中的每个数据。以下代码片段使用 forEach 输出了10个随机数：
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);

        //forEach 把里所有字符串转换小写
        List<String> strArrays = Arrays.asList("Abc","B", "CD");
        strArrays = strArrays.stream().map(s->s.toLowerCase()).sorted().collect(Collectors.toList());

        //自定义排序 按长度ASC排序
        strArrays = strArrays.stream().sorted(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        }).collect(Collectors.toList());

        //map 获取对应的平方数
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());

        //filter 方法用于通过设置的条件过滤出元素。以下代码片段使用 filter 方法过滤出空字符串：
        List<String> strings2 = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        // 获取空字符串的数量
        long count = strings2.stream().filter(string -> string.isEmpty()).count();

        //limit 方法用于获取指定数量的流。 以下代码片段使用 limit 方法打印出 10 条数据：
        random.ints().limit(10).forEach(System.out::println);

        //sorted 方法用于对流进行排序。以下代码片段使用 sorted 方法对输出的 10 个随机数进行排序：
        random.ints().limit(10).sorted().forEach(System.out::println);

        //并行 parallel parallelStream 是流并行处理程序的代替方法。以下实例我们使用 parallelStream 来输出空字符串的数量：
        List<String> strings3 = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        long count2 = strings3.parallelStream().filter(string -> string.isEmpty()).count();

        //Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);

        //统计 sum min max avg
        List<Integer> numbers2 = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        IntSummaryStatistics stats = numbers2.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());

        //使用reduce来sum
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        int reduceSum = integers.stream().reduce(0, Integer::sum);
        reduceSum = integers.stream().reduce(0, (i,j) -> i+j);
        //使用stream的sum
        int streamSum = integers.stream().mapToInt(i->i).sum();

    }

    /**
     * Arrays常用函数
     */
    public void loopArrays(){
        int[] arrInt = {55,44,33,22,11};
        Integer[] arrINT = {55,44,33,22,11};

        //使用toString方法遍历数组，省可以替代自己编写的for循环
        String str = Arrays.toString(arrInt);
        System.out.println(str);

        //sort, 默认asc (如果是自定义类型，那么自定义的类必须有comparable或者Comparator接口的支持)
        Arrays.sort(arrInt);
        System.out.println(Arrays.toString(arrInt));

        //binarySearch 二分法查找 适用排列的数组
        Arrays.sort(arrInt);//默认ASC
        Arrays.sort(arrINT, Collections.reverseOrder());//反向DESC排序
        int num = Arrays.binarySearch(arrInt, 44);

        //asList 创建List
        List<Integer> asList = Arrays.asList(1,2,3,4);

        //copyOf(arr, int newLength) 复制 copyOfRange(arr, from, to) 截取,to exclusive
        int[] copyOf = Arrays.copyOf(arrInt, arrInt.length);
        int[] copyOfRange = Arrays.copyOfRange(arrInt, 0, 1);//截取1个

        //fill 填充
        int[] fillArr = new int[10];
        Arrays.fill(fillArr, -1);
        System.out.println(Arrays.toString(fillArr));

        //array转化为list 1 asList
        //Arrays.asList(strArray)返回值是java.util.Arrays类中一个私有静态内部类java.util.Arrays.ArrayList，它并非java.util.ArrayList类
        //类具有 set()，get()，contains()等方法，但是不具有添加add()或删除remove()方法,所以调用add()方法会报错
        String[] strArr = new String[]{"a","b","c"};
        List<String> resultList = new ArrayList<>(Arrays.asList(strArr));

        //array转化为list 2 Collections.addAll
        Integer[] intArr = {1,2,3};
        List<Integer> intList = new ArrayList<>(intArr.length);
        Collections.addAll(intList, intArr);

        //array转化为list 3 stream 支持int float类型
        long[] longArr = {1,2,3};
        List<Long> longList = Arrays.stream(longArr).boxed().collect(Collectors.toList());
    }

    public static void listToArray(){
        // Creating a LinkedList of string type by
        // declaring object of List
        List<String> list = new LinkedList<String>();

        // Adding custom element to LinkedList
        // using add() method
        list.add("Geeks");
        list.add("for");
        list.add("Geeks");
        list.add("Practice");

        // Storing it inside array of strings
        String[] arr = new String[list.size()];

        //1. use for loop
        for (int i = 0; i < list.size(); i++)
            arr[i] = list.get(i);

        //2. listToArray()
        arr = list.toArray(new String[0]);

        //3. Stream API
        arr = list.stream().toArray(String[] ::new);
        arr = list.toArray(String[]::new);
    }

    public void arrayToList(){
        Integer[] arr = new Integer[]{1,2,3,4,5};
        List<Integer> integers = Arrays.asList(arr);
    }

    public static void main(String[] args) {
        List<String> strArrays = Arrays.asList("Abc","B", "CD");
        strArrays = strArrays.stream().map(s->s.toLowerCase()).sorted().collect(Collectors.toList());
        Collections.sort(strArrays);
        //自定义排序
        strArrays = strArrays.stream().sorted(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        }).collect(Collectors.toList());
        System.out.println(strArrays);

        int[][] grid = new int[20][20];

    }
}
