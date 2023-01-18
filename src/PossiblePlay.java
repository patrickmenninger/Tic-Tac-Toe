public class PossiblePlay {
    private int depth;
    private int score;

    public PossiblePlay(int depth, int score) {
        this.depth = depth;
        this.score = score;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
