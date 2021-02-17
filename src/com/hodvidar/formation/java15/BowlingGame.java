package com.hodvidar.formation.java15;

// https://kata-log.rocks/bowling-game-kata
public class BowlingGame {
    // TODO to continue

    int score = 0;
    int currentPinsDown = 0;
    boolean isFirstRollOfFrame = true;
    boolean previousWasSpare = false;
    int frame = 0;

    public void roll(final int pinksKnockedDown) {
        if (pinksKnockedDown < 0 || pinksKnockedDown > 10) {
            throw new UnsupportedOperationException(
                    "Wrong number of knocked down pins, "
                            + "must be between 0 and 10. "
                            + "It was '" + pinksKnockedDown + "'.");
        }
        if (isFirstRollOfFrame) {
            score += pinksKnockedDown;
            isFirstRollOfFrame = false;
            currentPinsDown = pinksKnockedDown;
            if (currentPinsDown == 10) {
                resetVariablesForNextFrame();
            }
            return;
        }

        score += pinksKnockedDown;
        resetVariablesForNextFrame();
        return;
    }

    private void resetVariablesForNextFrame() {
        isFirstRollOfFrame = true;
        currentPinsDown = 0;
        frame += 1;
    }

    public int score() {
        return score;
    }
}
