package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc{


  @Override
  public Stream<String> createStrStream(String... strings) {
    return Arrays.stream(strings);
  }

  @Override
  public Stream<String> toUpperCase(String... strings) {
    return createStrStream(strings).map(s -> s.toUpperCase());
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    return stringStream.filter(s -> !s.contains(pattern));
  }

  @Override
  public IntStream createIntStream(int[] arr) {
    return Arrays.stream(arr);
  }

  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    ArrayList<Integer> list = new ArrayList<>();
    intStream.forEach(i->list.add(i));
    return list;
  }

  @Override
  public IntStream createIntStream(int start, int end) {
    return IntStream.rangeClosed(start, end);
  }

  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    return intStream.asDoubleStream().map(i -> i = Math.sqrt(i));
  }

  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(i -> i%2 == 1);
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return i -> System.out.println(prefix + i +  suffix);
  }

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    Arrays.stream(messages).forEach(printer);
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    intStream.mapToObj(String::valueOf).forEach(printer);
  }

  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    return ints.flatMap(List::stream).map(i -> i * i);
  }

  public static void main(String[] args) {
    LambdaStreamExc lse = new LambdaStreamExcImp();

    // Testing createStrStream
    lse.createStrStream("a", "b", "c", "d").forEach(System.out::println);

    // Testing toUpperCase
    lse.toUpperCase("Samarth", "Ghadi").forEach(System.out::println);

    // Testing filter
    lse.filter(lse.createStrStream("open", "toaster", "execute"), "o").forEach(System.out::println);

    // Testing squareRootIntStream
    lse.squareRootIntStream(lse.createIntStream(16, 25)).forEach(System.out::println);

    // Testing getOdd
    lse.getOdd(lse.createIntStream(new int[]{1, 2, 3, 4, 5})).forEach(System.out::println);

    // Testing getLambdaPrinter
    Consumer<String> printer = lse.getLambdaPrinter("prefix>", "<suffix");
    printer.accept("Message Body");
  }
}
