package competetives;

import netscape.javascript.JSObject;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Square {
    public static void main(String[] args) {
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
}
