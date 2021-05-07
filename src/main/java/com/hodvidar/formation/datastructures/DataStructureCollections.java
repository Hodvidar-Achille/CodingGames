package com.hodvidar.formation.datastructures;

import org.assertj.core.data.MapEntry;

import java.util.*;

public class DataStructureCollections {

    // Array
    private int[] array = new int[1000];

    // Collections
    private Collection<Integer> collection = new ArrayList<>();
    // - List
    private List<Integer> list = new ArrayList<>();
    // -- ArrayList
    private ArrayList<Integer> arrayList = new ArrayList<>();
    // -- LinkedList
    private LinkedList<Integer> linkedList = new LinkedList<>();

    // - Set
    private Set<Integer> set = new HashSet<>();
    // -- Hashset
    private HashSet<Integer> hashset = new HashSet<>();
    // -- SortedSet ? impl ?
    private SortedSet<Integer> sortedSet = new TreeSet<>();
    // -- TreeSet
    private TreeSet<Integer> treeSet = new TreeSet<>();
    // -- LinkedHashSet
    private LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();

    // - Queue
    private Queue<Integer> queue = new ArrayDeque<>();
    // -- Double ended queue
    private Deque<Integer> deque = new ArrayDeque<>();
    // --- ArrayDeque
    private ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();

    // - Map
    private Map<String, Integer> map = new HashMap<>();
    // -- HashMap
    private HashMap<String, Integer> hashMap = new HashMap<>();
    // -- SortedMap
    private SortedMap<String, Integer> sortedMap = new TreeMap<>();
    // -- TreeMap
    private TreeMap<String, Integer> treeMap = new TreeMap<>();
    // -- LinkedHashMap
    private LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();

    public void init() {
        for (int i = 0; i < 100; i++) {
            array[i] = getKindaRandomValue(i);
            collection.add(getKindaRandomValue(i));
            list.add(getKindaRandomValue(i));
            arrayList.add(getKindaRandomValue(i));
            linkedList.add(getKindaRandomValue(i));
            set.add(getKindaRandomValue(i));
            hashset.add(getKindaRandomValue(i));
            sortedSet.add(getKindaRandomValue(i));
            treeSet.add(getKindaRandomValue(i));
            linkedHashSet.add(getKindaRandomValue(i));
            queue.add(getKindaRandomValue(i));
            deque.add(getKindaRandomValue(i));
            arrayDeque.add(getKindaRandomValue(i));
            map.put("" + i, getKindaRandomValue(i));
            hashMap.put("" + i, getKindaRandomValue(i));
            sortedMap.put("" + i, getKindaRandomValue(i));
            treeMap.put("" + i, getKindaRandomValue(i));
            linkedHashMap.put("" + i, getKindaRandomValue(i));
        }
    }

    private static int getKindaRandomValue(int i) {
        return i % 3 == 0 ? i * i * (-1) : i * i;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += "array : [";
        for (int i : array) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "collection : [";
        for (int i : collection) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "list : [";
        for (int i : list) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "arrayList : [";
        for (int i : arrayList) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "linkedList : [";
        for (int i : linkedList) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "set : [";
        for (int i : set) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "hashset : [";
        for (int i : hashset) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "sortedSet : [";
        for (int i : sortedSet) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "treeSet : [";
        for (int i : treeSet) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "linkedHashSet : [";
        for (int i : linkedHashSet) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "queue : [";
        for (int i : queue) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "deque : [";
        for (int i : deque) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "arrayDeque : [";
        for (int i : arrayDeque) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "map : [";
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            representation += "'"+e.getKey()+"':"+e.getValue()+", ";
        }
        representation += "]\n";
        representation += "hashMap : [";
        for (Map.Entry<String, Integer> e : hashMap.entrySet()) {
            representation += "'"+e.getKey()+"':"+e.getValue()+", ";
        }
        representation += "]\n";
        representation += "sortedMap : [";
        for (Map.Entry<String, Integer> e : sortedMap.entrySet()) {
            representation += "'"+e.getKey()+"':"+e.getValue()+", ";
        }
        representation += "]\n";
        representation += "treeMap : [";
        for (Map.Entry<String, Integer> e : treeMap.entrySet()) {
            representation += "'"+e.getKey()+"':"+e.getValue()+", ";
        }
        representation += "]\n";
        representation += "linkedHashMap : [";
        for (Map.Entry<String, Integer> e : linkedHashMap.entrySet()) {
            representation += "'"+e.getKey()+"':"+e.getValue()+", ";
        }
        representation += "]\n";
        return representation;
    }
}
