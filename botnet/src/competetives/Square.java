package competetives;

import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Square {
    public static void main(String[] args) {
//        List<Person> personList = new ArrayList<>();
//        personList.add(new Person("bohdan", 13));
//        personList.add(new Person("ivan", 19));
//        personList.add(new Person("yana", 23));
//
//        List<Person> ll = getPersons(personList, 13);
//        ll.forEach(System.out::println);
//
//        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
//        priorityQueue.add(1);
//        priorityQueue.add(9);
//        priorityQueue.add(3);
//
//        while (!priorityQueue.isEmpty()) {
//            System.out.println(priorityQueue.poll());
//        }
//
//        List<String> testSortedList = new ArrayList<>();
//        testSortedList.add("hhhfd");
//        testSortedList.add("hfsdfsdfd");
//        testSortedList.add("hhhfd34234");
//
//        Collections.sort(testSortedList, (o1, o2) -> Integer.compare(countSymbols(o1), countSymbols(o2)));
//        testSortedList.forEach(System.out::println);
//
//
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(7);
//        list.add(5);
//        list.add(15);
//
//        Collections.sort(list, (o1, o2) -> Integer.compare(o2, o1));
//        list.forEach(System.out::println);
        String htmlCodeExample = new String();
        String element = new String();
        String className = new String();

        if (args.length > 1) {
            htmlCodeExample = args[0];
            element = args[1];
            className = args[2];
        } else {
            System.out.println("Usage: java app.java https://example.com elementName className");
            System.exit(0);
        }

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create(htmlCodeExample))
                                .GET()
                                        .build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            htmlCodeExample = httpResponse.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        var test = findElementByClass(element, htmlCodeExample);
        var ll = filterByClass(className,test);

        ll.forEach(i -> {
            System.out.println(i.trim() + "\n");
        });
    }
    public static List<String> findElementByClass(String tag, String htmlCodeExample) {
        List<String> tags = new ArrayList<>();

        boolean found = false;
        StringBuilder result = new StringBuilder();

        if (htmlCodeExample.trim().contains(tag)) {
            for (int i = 0; i < htmlCodeExample.length(); i++) {
                char c = htmlCodeExample.charAt(i);

                if (found) {
                    if (c != '/' || htmlCodeExample.charAt(i + 1) != tag.charAt(0)) {
                        result.append(c);
                    } else {
                        tags.add(result.append("/" + tag + ">").toString());
                        found = false;
                        result = new StringBuilder();
                    }
                } else {
                    if (c == '<' && htmlCodeExample.charAt(i + 1) == tag.charAt(0)) {
                        result.append(c);
                        found = true;
                    }
                }
            }
        }
        return tags;
    }

    public static List<String> filterByClass(String tagClass, List<String> list) {
        List<String> resultList = new ArrayList<>();

        for (String result : list) {
            if (result.contains(tagClass)) {
                resultList.add(result);
            }
        }
        return resultList;
    }

    public static Set<String> findHttpAddress(String htmlCodeExample) {
        Set<String> links = new HashSet<>();

        StringBuilder result = new StringBuilder();
        boolean found = false;

        if (htmlCodeExample.trim().contains("http")) {
            for (int i = 0; i < htmlCodeExample.length(); i++) {
                char c = htmlCodeExample.charAt(i);

                if (found) {
                    if (c != '"') {
                        result.append(c);
                    } else {
                        links.add(result.toString());
                        result = new StringBuilder();
                        found = false;
                    }
                } else {
                    if (c == 'h' && htmlCodeExample.charAt(i + 1) == 't') {
                        result.append(c);
                        found = true;
                    }
                }
            }
        } else {
            System.out.println(-1);
        }
        return links;
    }

    public static Integer countSymbols(String o1) {
        int o1Res = 0;

        for (int i = 0; i < o1.length(); i++) {
            if (o1.charAt(i) == 'h') {
                o1Res += 1;
            }
        }

        return o1Res;
    }

    public static <T> List<T> getPersons(List<T> personList, int filterAge) {
        List<T> result = new ArrayList<>();

        for (T object : personList) {
            if (object instanceof Person) {
                Person person = (Person) object;
                if (person.getAge() > filterAge) {
                    result.add(object);
                }
            }
        }
        return result;
    }

    public static void game2(String... args) {
        List<String> items = List.of(args);
        List<String> tested = items.stream().map(String::toUpperCase).collect(Collectors.toList());
        tested.forEach(i -> System.out.println(i.length() > 1));
    }

    public void game1() {
        int n = 2;
        int testResult = 0;

        int result = 0;

        for (int j = 1000; j < n * 1000 + 1; j++) {
            String num = String.valueOf(j);
            for (int i = 0; i < 4; i++) {
                testResult += Integer.parseInt(String.valueOf(num.charAt(i)));
            }
            if (testResult == n) {
                result += 1;
            }
            testResult = 0;
        }
        System.out.println(result);
    }

    public static Set<String> getList() {
        Set<String> results = new HashSet<>();
        generateWordList("", "hi", results);
        return results;
    }

    public static void generateWordList(String prefix, String remaining, Set<String> result) {
        int n = remaining.length();
        if (n == 0){
            result.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                generateWordList(
                        prefix + remaining.charAt(i),
                        remaining.substring(0, i) + remaining.substring(i + 1),
                        result
                );
            }
        }
    }
}

class Person {
    private String username;
    private int age;

    public Person(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class TestPerson implements Comparable<TestPerson> {

    @Override
    public int compareTo(TestPerson o) {
        return 0;
    }
}
