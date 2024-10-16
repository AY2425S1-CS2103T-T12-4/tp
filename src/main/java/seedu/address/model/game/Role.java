package seedu.address.model.game;

/**
 * Describes the Person's skill level in a Game.
 */
public class Role {
    private final String role;

    /**
     * Constructs a {@code Role}.
     *
     * @param role a valid skill level or rank.
     */
    public Role(String role) {
        this.role = role;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return this.role;
    }
}
