package Tree;

import java.util.PriorityQueue;

class MedianFinder {

    PriorityQueue<Integer> pqMin;
    PriorityQueue<Integer> pqMax;

    public MedianFinder() {
        pqMin = new PriorityQueue<>();
        pqMax = new PriorityQueue<>((a,b)->(b-a));
    }

    public void addNum(int num) {
        pqMin.add(num);
        pqMax.add(pqMin.remove());
        if (pqMin.size() < pqMax.size()) {
            pqMin.add(pqMax.remove());
        }
    }

    public double findMedian() {
        if (pqMin.size() == pqMax.size()) {
            return (pqMin.peek()+pqMax.peek())/2;
        }
        return pqMin.peek();
    }
}