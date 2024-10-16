package seedu.address.model.game;

public class Skill {
    private final String skillLevel;

    public Skill(String skillLevel) {
        this.skillLevel = skillLevel;
    }


    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return this.skillLevel;
    }
}
