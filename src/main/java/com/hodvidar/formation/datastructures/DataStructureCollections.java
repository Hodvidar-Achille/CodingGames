package com.hodvidar.formation.datastructures;

import java.util.*;

public class DataStructureCollections {

    // Array
    private final int[] array = new int[1000];

    // Collections
    private final Collection<Integer> collection = new ArrayList<>();
    // - List
    private final List<Integer> list = new ArrayList<>();
    // -- ArrayList
    private final ArrayList<Integer> arrayList = new ArrayList<>();
    // -- LinkedList
    private final LinkedList<Integer> linkedList = new LinkedList<>();

    // - Set
    private final Set<Integer> set = new HashSet<>();
    // -- Hashset
    private final HashSet<Integer> hashset = new HashSet<>();
    // -- SortedSet ? impl ?
    private final SortedSet<Integer> sortedSet = new TreeSet<>();
    // -- TreeSet
    private final TreeSet<Integer> treeSet = new TreeSet<>();
    // -- LinkedHashSet
    private final LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();

    // - Queue
    private final Queue<Integer> queue = new ArrayDeque<>();
    // -- Double ended queue
    private final Deque<Integer> deque = new ArrayDeque<>();
    // --- ArrayDeque
    private final ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();

    // - Map
    private final Map<String, Integer> map = new HashMap<>();
    // -- HashMap
    private final HashMap<String, Integer> hashMap = new HashMap<>();
    // -- SortedMap
    private final SortedMap<String, Integer> sortedMap = new TreeMap<>();
    // -- TreeMap
    private final TreeMap<String, Integer> treeMap = new TreeMap<>();
    // -- LinkedHashMap
    private final LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();

    // - Vector
    private final Vector<Integer> vector = new Vector<>();
    // -- Stack
    private final Stack<Integer> stack = new Stack<>();

    private static int getKindaRandomValue(final int i) {
        return i % 3 == 0 ? i * i * (-1) : i * i;
    }

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
            vector.add(getKindaRandomValue(i));
            stack.add(getKindaRandomValue(i));
        }
    }

    @Override
    public String toString() {
        String representation = "";
        representation += "array : [";
        for (final int i : array) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "collection : [";
        for (final int i : collection) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "list : [";
        for (final int i : list) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "arrayList : [";
        for (final int i : arrayList) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "linkedList : [";
        for (final int i : linkedList) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "set : [";
        for (final int i : set) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "hashset : [";
        for (final int i : hashset) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "sortedSet : [";
        for (final int i : sortedSet) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "treeSet : [";
        for (final int i : treeSet) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "linkedHashSet : [";
        for (final int i : linkedHashSet) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "queue : [";
        for (final int i : queue) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "deque : [";
        for (final int i : deque) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "arrayDeque : [";
        for (final int i : arrayDeque) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "map : [";
        for (final Map.Entry<String, Integer> e : map.entrySet()) {
            representation += "'" + e.getKey() + "':" + e.getValue() + ", ";
        }
        representation += "]\n";
        representation += "hashMap : [";
        for (final Map.Entry<String, Integer> e : hashMap.entrySet()) {
            representation += "'" + e.getKey() + "':" + e.getValue() + ", ";
        }
        representation += "]\n";
        representation += "sortedMap : [";
        for (final Map.Entry<String, Integer> e : sortedMap.entrySet()) {
            representation += "'" + e.getKey() + "':" + e.getValue() + ", ";
        }
        representation += "]\n";
        representation += "treeMap : [";
        for (final Map.Entry<String, Integer> e : treeMap.entrySet()) {
            representation += "'" + e.getKey() + "':" + e.getValue() + ", ";
        }
        representation += "]\n";
        representation += "linkedHashMap : [";
        for (final Map.Entry<String, Integer> e : linkedHashMap.entrySet()) {
            representation += "'" + e.getKey() + "':" + e.getValue() + ", ";
        }
        representation += "]\n";
        representation += "vector : [";
        for (final int i : vector) {
            representation += i + ", ";
        }
        representation += "]\n";
        representation += "stack : [";
        for (final int i : stack) {
            representation += i + ", ";
        }
        representation += "]\n";
        return representation;
    }
}
