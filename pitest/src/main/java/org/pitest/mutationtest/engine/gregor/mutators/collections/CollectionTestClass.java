package org.pitest.mutationtest.engine.gregor.mutators.collections;

import java.util.*;

import static java.util.Arrays.asList;

public class CollectionTestClass {

    private List<String> stringList = new ArrayList<String>(asList("Hello"));
    private List<String> emptyList = new ArrayList<String>();

    public void addToCollection(String input) {
        stringList.add(input);
    }

    public void verifyContainsString(String token) {
        stringList.contains(token);
    }

    public void method() {
        List<String> methodList = new ArrayList<String>();
        Set<String> methodSet = new HashSet<String>();
        Map<String,String> methodMap = new HashMap<String, String>();

        List<String> nonempty = new ArrayList<String>(methodList);

        String fifth = "fifth";
        String sixth = "sisxth";

        methodSet.clear();
    }

    public static void staticMethod(){
        List<String> methodList = new ArrayList<String>();
        Set<String> methodSet = new HashSet<String>();
        Map<String,String> methodMap = new HashMap<String, String>();

        List<String> nonempty = new ArrayList<String>(methodList);

        String fifth = "fifth";
        String sixth = "sisxth";

        methodSet.clear();
    }
}
