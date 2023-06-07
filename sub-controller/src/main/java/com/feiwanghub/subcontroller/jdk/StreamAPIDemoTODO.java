package com.feiwanghub.subcontroller.jdk;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.out;

/**
 * <a href="https://mp.weixin.qq.com/s/yl3Go-YDQRN47SVHWN6N8w">参考文章 reference article</a>
 */
public class StreamAPIDemoTODO {

    private final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    /**
     * ParallelStream背后依靠的是ForkJoinPool
     * ForkJoinPool是Java7提供的一个用于并行执行任务的线程池，它使用了一个"无限队列"来保存需要需要执行的任务
     * ForkJoinPool的优势在于它使用了"工作窃取算法"（work-stealing algorithm）来提高CPU的利用率。
     * （如果没有传入期望线程数量，它会使用当前可用CPU数量）
     * <p>
     * ForkJoinPool适合用来处理具有"父子关系"的任务，比如将一个大任务分割成若干个小任务并行执行，然后再将各个小任务的结果合并成一个大任务的结果。
     * 当使用ThreadPoolExecutor时，使用分治法会存在问题，因为ThreadPoolExecutor中的线程无法向 任务队列中再添加一个任务并且在等待该任务完成之后再继续执行。
     * 而使用ForkJoinPool时，就能够让其中的线程创建新的任务，并挂起当前的任务，此时线程就能够从队列中选择子任务执行。
     * <p>
     * Work Stealing工作原理：
     * 1. 每个线程都有一个自己的双端队列，用来存储需要执行的任务
     * 2. 当一个线程的队列为空时，它会从其他线程的队列中"偷"一个任务来执行
     * 3. 当一个线程从其他队列中"偷"一个任务时，它会从队列的尾部"偷"，因为队列的尾部是最新加入的任务，很可能是"热点任务"，执行的速度会更快
     * 4. 当一个线程"偷"到一个任务时，它会将任务放入自己的队列中，然后执行该任务
     * 5. 当一个线程"偷"到一个任务并执行时，它会一直"偷"，直到"偷"不到任务为止
     */
    public void parallelStream() {
        // Stream具有平行处理能力，处理的过程会分而治之，也就是将一个大任务切分成多个小任务，这表示每个任务都是一个操作
        // 可以看到一行简单的代码就帮我们实现了并行输出集合中元素的功能，但是由于并行执行的顺序是不可控的所以每次执行的结果不一定相同。
        numbers.parallelStream().forEach(out::println);

        //如果非得相同可以使用forEachOrdered方法执行终止操作：
        numbers.parallelStream().forEachOrdered(out::println);

        //BaseStream接口中分别提供了并行流和串行流两个方法，这两个方法可以任意调用若干次，也可以混合调用，但最终只会以最后一次方法调用的返回结果为准。
        //下面的例子里以最后一次调用parallel()为准，最终是并行地计算sum：
        numbers.stream()
                .parallel()
                .filter(i -> i % 2 == 0)
                .sequential()
                .map(i -> i + 1)
                .parallel() // 以最后一次调用parallel()为准
                .forEach(out::println);
    }

    public void onClose() {
        //onClose() 方法会返回流对象本身，也就是说可以对改对象进行多次调用
        numbers.stream().onClose(() -> out.println("Stream Done with onClose!")).close();
    }

    public void usefulAPI() {
        out.println("StreamAPIDemo usefulAPI");
        out.println("range() API");
        IntStream.range(1, 10).forEach(out::println);
        int[] intArr = IntStream.range(1, 10).toArray();
        out.println(Arrays.toString(intArr));

        //reduce
        //limit
        //map
        //flatMap
        //findFirst
        //findEachOrdered
        //unordered
        //distinct
        //collect
        //count
        //groupingBy
    }

    void testFlatMap() {
        //flatMap sentence to distinct words
        var lines = Arrays.asList("Hello world", "Java Stream API", "Java 17");
        var uniqueWords = lines.stream()
                .flatMap(line -> Arrays.stream(line.split(" "))) //split line into words stream
                .distinct()
                .toList();
        uniqueWords.forEach(out::println);

        //flatMap nested list
        out.println();
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );

        List<Integer> flattenedList = nestedList.stream()
                .flatMap(List::stream)
                .toList();
        flattenedList.forEach(System.out::println);
    }

    void testGroupingBy(){
        //groupingBy words by length
        var lines = Arrays.asList("Hello world", "Java Stream API", "Java 17");
        var words = lines.stream()
                .flatMap(line -> Arrays.stream(line.split(" "))) //split line into words stream
                .distinct()
                .collect(Collectors.groupingBy(String::length));
        words.forEach((k,v)->out.println(k+"->"+v));

        //groupingBy word and its count
        out.println();
        List<String> words2 = Arrays.asList("apple", "banana", "apple", "cherry", "banana", "apple");
        Map<String, Long> wordCounts = words2.stream()
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
        wordCounts.forEach((word, count) -> System.out.println(word + ": " + count));
    }

    public static void main(String[] args) {
        StreamAPIDemoTODO demo = new StreamAPIDemoTODO();
        //demo.parallelStream();
        //demo.onClose();
        //demo.usefulAPI();
        //demo.testGroupingBy();
        demo.testFlatMap();
    }

}
