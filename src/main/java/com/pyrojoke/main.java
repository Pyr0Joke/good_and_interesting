package com.pyrojoke;

import org.eclipse.jetty.util.ArrayQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class main {
    public static void main(String[] args) throws IOException {
//        Person person = new Person("Sasha", "Lipatov");
//        System.out.println(person.toString());
//        Set<Integer> integerSet = new HashSet<>();
//        integerSet.add(5);
//        integerSet.add(2);
//        integerSet.add(3);
//        integerSet.add(3);
//        System.out.println(integerSet);

//
//        Set<String> stringSet = new HashSet<>();
//        stringSet.add("j");
//        stringSet.add("a");
//        stringSet.add("d");
//        System.out.println(stringSet);
//
//        ArrayQueue<String> stringQueue = new ArrayQueue<>();
//        stringQueue.add("j");
//        stringQueue.add("a");
//        stringQueue.add("d");
//        stringQueue.add("t");
//        stringQueue.add("t");
//        Collections.reverse(stringQueue);
//        System.out.println(stringQueue);
//        stringQueue.poll();
//       System.out.println(stringQueue);
//        System.out.println(Collections.frequency(stringQueue, "t"));
//        System.out.println(Collections.max(stringQueue));
//        List<String> unmodifiareList = Collections.unmodifiableList(stringQueue);
//        System.out.println(stringQueue.peek());
//        System.out.println(stringQueue);
//        System.out.println(stringQueue.peek().compareTo("j"));

//
//        System.out.println(stringQueue);
//        stringQueue.peek();
//        System.out.println(stringQueue);
//        System.out.println(stringQueue.poll());
//        System.out.println(stringQueue);
//        stringQueue.remove(1);
//        System.out.println(stringQueue);

//        ArrayDeque<String> stringDueue = new ArrayDeque<>();
//        stringDueue.add("j");
//        stringDueue.add("a");
//        stringDueue.add("d");
//        System.out.println(stringDueue);
//        stringDueue.add("t");
//        System.out.println(stringDueue);
//        System.out.println(stringDueue.poll());

//        SortedSet<Integer> integerSet = new TreeSet<>();
//        integerSet.add(5);
//        integerSet.add(2);
//        integerSet.add(3);
//        System.out.println(integerSet);

//        TreeSet<Integer> treeSet = new TreeSet<>();
//        treeSet.add(5);
//        treeSet.add(2);
//        treeSet.add(3);
//        treeSet.add(3);
//        System.out.println(treeSet);
//        System.out.println();
//        Iterator iterator = treeSet.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

//        List<Integer> integerList = new ArrayList<>();
//        integerList.add(1);
//        integerList.add(2);
//        integerList.add(3);
//        integerList.add(4);
//
//        ListIterator iterator = integerList.listIterator();
//
//        while (iterator.hasNext()){
//            System.out.println(iterator.previous());
//        }

//        Map<Integer, String> map = new HashMap<>();
//        map.put(1,"a");
//        map.put(2,"b");
//        map.put(3,"c");
//        System.out.println(map);

//        long now = System.currentTimeMillis();
//        slow();
//        System.out.println("slow elapsed " + (System.currentTimeMillis() - now) + " ms");
//
//        now = System.currentTimeMillis();
//        fast();
//        System.out.println("fast elapsed " + (System.currentTimeMillis() - now) + " ms");
//        String s = "sa";
//        String s1 = "saa";
//        System.out.println(s.hashCode());
//        System.out.println(s1.hashCode());
//        System.out.println(s.equals("s"+"a"));
//


//        int a =1;
//        if(a==1){
//            try {
//                throw new ArithmeticException("Eto edenica");
//            }
//            catch (ArithmeticException e){
//                e.printStackTrace();
//            }
//        }
//        LinkedList<String> linkedList = new LinkedList<>();
//        linkedList.add("a");
//        linkedList.add("b");
//        linkedList.add("c");
//        linkedList.add("d");
//        linkedList.addFirst("0");
//        System.out.println(linkedList);
//        System.out.println("asdf");
//
//
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.trimToSize();
//
//
//        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
//        linkedHashMap.put("a",1);

//        HashMap<Integer, Integer> hashMap = new HashMap<>();
//        hashMap.put(null,null);
//        hashMap.put(2,1);
//        hashMap.put(3,1);
//        hashMap.put(4,1);
//        System.out.println(hashMap);

//        MyClass myClass = new MyClass();
//        int number = myClass.getNumber();
//        String name = null; //no getter =(
//        printData(myClass);//output 0null
//        try {
//            Field field = myClass.getClass().getDeclaredField("name");
//            field.setAccessible(true);
//            field.set(myClass, "new value");
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        printData(myClass);//output 0default

//        MyClass myClass = null;
//        try {
//            Class clazz = Class.forName(MyClass.class.getName());
//            myClass = (MyClass) clazz.newInstance();
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        System.out.println(myClass);//output created object reflection.MyClass@60e53b93

//        MyClass myClass = null;
//        try {
//            Class clazz = Class.forName(MyClass.class.getName());
//            Constructor[] constructors = clazz.getConstructors();
//            for (Constructor constructor : constructors) {
//                System.out.println("Конструктор:");
//                Class[] paramTypes = constructor.getParameterTypes();
//                for (Class paramType : paramTypes) {
//                    System.out.println("Тип параметра: "+paramType.getName() + " ");
//                }
//                System.out.println();
//            }
//            Class[] params = {int.class, String.class};
//            myClass = (MyClass) clazz.getConstructor(params).newInstance(1, "default2");
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        System.out.println(myClass);//output created object reflection.MyClass@60e53b93
//    }
//
//    public static void printData(Object myClass){
//        try {
//            Method method = myClass.getClass().getDeclaredMethod("printData");
//            method.setAccessible(true);
//            method.invoke(myClass);
//        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        Date date = new Date();
//        String cart = String.valueOf(date.getTime());
//        String counterparty = String.valueOf(date.getTime()+1);
//        String user = String.valueOf(date.getTime()+2);
//        System.out.println(cart);
//        System.out.println(counterparty);
//        System.out.println(user);
//        System.out.println(cart);
//        System.out.println(counterparty);
//        System.out.println(user);
//        AtomicInteger atomicInteger = new AtomicInteger(0);
//        atomicInteger.addAndGet(1);
//        atomicInteger.incrementAndGet();
//        System.out.println(atomicInteger);
//
//        given().spec(RestAssuredSpecifications.getRequestSpecification()).when().get("/lolo").then().statusCode(200).body("name", equalTo("name"))
//                .extract().as(Person.class);

//        Date date = new Date();
//        if(date.getTime()%2==0){
//            System.out.println("Google");
//        }
//        else
//        {
//            System.out.println("Yandex");
//        }

//        given().spec(RestAssuredSpecifications.getRequestSpecification()).when().get("/lolo").then().statusCode(200)
//                .extract().as(Person.class);
//
//        await().atMost(Duration.ofSeconds(10)).untilAsserted(()->
//                    assertThat(given().spec(RestAssuredSpecifications.getRequestSpecification()).when().get("/lolo").then().statusCode(200).toString(), true)
//
//        );
        exec1C("delivery");
    }

    public static void exec1C(String batFineName) throws IOException {
        Runtime.getRuntime().exec("cmd /c start d:/bats/"+batFineName+".bat ");
        boolean flag = true;
        while(flag) {
            String[] cmd = {"tasklist", "/fi", "\"IMAGENAME eq 1cv8c.exe\""};
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("1cv8c.exe")) {
                    System.out.println("Один эс зопущен в первый раз");
                    flag=false;
                    break;
                }
            }
            br.close();
            isr.close();
            is.close();
        }

        flag = true;

        while(flag) {
            String[] cmd = {"tasklist", "/fi", "\"IMAGENAME eq 1cv8c.exe\""};
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            boolean is1CStarted = false;
            List<String> consoleOutputList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                consoleOutputList.add(line);
            }
            for (String consoleLine: consoleOutputList){
                if(consoleLine.contains("1cv8c.exe")){
                    System.out.println("Один эс зопущен и чото делоет");
                    is1CStarted=true;
                }
            }
            flag=is1CStarted;
            br.close();
            isr.close();
            is.close();
        }
        System.out.println("Один эс выключился");
    }

    public int sum(int x, int y){

        return x + y;
    }
    private static void fast()
    {
        StringBuilder s = new StringBuilder();
        for(int i=0;i<100000;i++)
            s.append("*");
    }

    private static void slow()
    {
        String s = "";
        for(int i=0;i<100000;i++)
            s+="*";
    }
}
