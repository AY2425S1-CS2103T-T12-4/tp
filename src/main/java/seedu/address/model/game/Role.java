package seedu.address.model.game;

public class Role {
    private final String role;

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
