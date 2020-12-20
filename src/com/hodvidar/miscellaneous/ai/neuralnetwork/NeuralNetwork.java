package com.hodvidar.miscellaneous.ai.neuralnetwork;
// https://www.youtube.com/watch?v=aircAruvnKk
// This will be java classes for convolutional neural network for very simple image reconition

// Neural network (in java) To reconize numbers in a screen with 28x28 pixels.

class Node {
    public final int x;
    public final int y;

    public Node() {
        this.x = 0;
        this.y = 0;
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
