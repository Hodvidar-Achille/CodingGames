package com.hodvidar.formation.java15;

// https://kata-log.rocks/bowling-game-kata
public class BowlingGame {

    static final String ERROR_MESSAGE_NUMBER_PINS_DOWN = "Wrong number of knocked down pins, "
            + "must be between 0 and 10. ";
    static final String ERROR_MESSAGE_GAME_ENDED = "Game already finished";
    private int score = 0;
    private int currentPinsDown = 0;
    private boolean isFirstRollOfFrame = true;
    private int pinsDownInFirstTryOfFrame = 0;
    private boolean previousWasSpare = false;
    private boolean previousWasStrike = false;
    private boolean secondPreviousWasStrike = false;
    private int frame = 0;

    public void roll(final int pinksKnockedDown) {
        if (frame > 10) {
            throw new UnsupportedOperationException(ERROR_MESSAGE_GAME_ENDED);
        }
        if (frame == 10) {
            if (previousWasSpare || previousWasStrike) {
                doRoll(pinksKnockedDown);
                previousWasSpare = false;
                previousWasStrike = false;
                return;
            }
            throw new UnsupportedOperationException(ERROR_MESSAGE_GAME_ENDED);
        }
        if (pinksKnockedDown < 0
                || pinksKnockedDown + currentPinsDown > 10) {
            throw new UnsupportedOperationException(ERROR_MESSAGE_NUMBER_PINS_DOWN);
        }

        doRoll(pinksKnockedDown);
    }

    private void doRoll(final int pinksKnockedDown) {
        score += pinksKnockedDown;

        if (previousWasSpare) {
            score += pinksKnockedDown;
            previousWasSpare = false;
        }
        if (secondPreviousWasStrike) {
            secondPreviousWasStrike = false;
            score += pinksKnockedDown;
        }
        if (previousWasStrike) {
            previousWasStrike = false;
            secondPreviousWasStrike = true;
            score += pinksKnockedDown;
        }

        if (isFirstRollOfFrame) {
            isFirstRollOfFrame = false;
            pinsDownInFirstTryOfFrame = pinksKnockedDown;
            currentPinsDown = pinksKnockedDown;
            if (currentPinsDown == 10) {
                previousWasStrike = true;
                resetVariablesForNextFrame();
            }
            return;
        }
        if (pinsDownInFirstTryOfFrame + pinksKnockedDown == 10) {
            previousWasSpare = true;
        }
        resetVariablesForNextFrame();
    }

    private void resetVariablesForNextFrame() {
        isFirstRollOfFrame = true;
        currentPinsDown = 0;
        pinsDownInFirstTryOfFrame = 0;
        frame += 1;
    }

    public int score() {
        return score;
    }
}
