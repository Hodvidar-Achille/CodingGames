package com.hodvidar.codingame.puzzles.event.y2019amadeus;

class Action {
    final String command;
    final Coord pos;
    final EntityType item;
    String message;

    private Action(final String command, final Coord pos, final EntityType item) {
        this.command = command;
        this.pos = pos;
        this.item = item;
    }

    static Action none() {
        return new Action("WAIT", null, null);
    }

    static Action move(final Coord pos) {
        return new Action("MOVE", pos, null);
    }

    static Action dig(final Coord pos) {
        return new Action("DIG", pos, null);
    }

    static Action request(final EntityType item) {
        return new Action("REQUEST", null, item);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder(command);
        if (pos != null) {
            builder.append(' ').append(pos);
        }
        if (item != null) {
            builder.append(' ').append(item);
        }
        if (message != null) {
            builder.append(' ').append(message);
        }
        return builder.toString();
    }
}