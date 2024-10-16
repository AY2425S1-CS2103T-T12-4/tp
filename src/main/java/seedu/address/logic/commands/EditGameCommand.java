package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLLEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.game.Role;
import seedu.address.model.game.SkillLevel;
import seedu.address.model.game.Username;

/**
 * Represents a command to edit information about a game.
 * This game is stored within a {@code Person}.
 */
public class EditGameCommand extends Command {
    public static final String COMMAND_WORD = "editgame";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the game "
            + "under the person specified"
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_GAME + "GAMENAME] "
            + "[" + PREFIX_USERNAME + "USERNAME] "
            + "[" + PREFIX_SKILLLEVEL + "SKILLLEVEL] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GAME + "Valorant "
            + PREFIX_USERNAME + "johndoe123"
            + PREFIX_SKILLLEVEL + "Gold 1"
            + PREFIX_ROLE + "Duelist";

    public static final String MESSAGE_EDIT_GAME_SUCCESS = "Edited Game: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_GAME = "This game already exists in the address book.";

    private final Index index;
    private final String gameName;
    private final EditGameDescriptor editGameDescriptor;

    /**
     * Default constructor for an edit game command.
     */
    public EditGameCommand() {
        this.index = null;
        this.gameName = null;
        this.editGameDescriptor = null;
    }

    /**
     * @param index of the person in the filtered person list to edit
     * @param editGameDescriptor details to edit the game with
     */
    public EditGameCommand(Index index, String gameName, EditGameDescriptor editGameDescriptor) {
        requireNonNull(index);
        requireNonNull(editGameDescriptor);

        this.index = index;
        this.gameName = gameName;
        this.editGameDescriptor = editGameDescriptor;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("This command should return an edited game");
    }

    /**
     * Creates and returns a {@code Game} with the details of {@code gameToEdit}
     * edited with {@code editGameDescriptor}.
     */
    private static Game createEditedGame(Game gameToEdit, EditGameDescriptor editGameDescriptor) {
        assert gameToEdit != null;

        Username updatedUsername = editGameDescriptor.getUsername().orElse(gameToEdit.getUsername());
        SkillLevel updatedSkillLevel = editGameDescriptor.getSkillLevel().orElse(gameToEdit.getSkillLevel());
        Role updatedRole = editGameDescriptor.getRole().orElse(gameToEdit.getRole());

        return new Game(gameToEdit.gameName, updatedUsername, updatedSkillLevel, updatedRole);
    }

    /**
     * Stores the details to edit the game with. Each non-empty field will
     * replace the corresponding field of the game.
     */
    public static class EditGameDescriptor {
        private Username username;
        private SkillLevel skillLevel;
        private Role role;

        public EditGameDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditGameDescriptor(EditGameDescriptor toCopy) {
            setUsername(toCopy.username);
            setSkillLevel(toCopy.skillLevel);
            setRole(toCopy.role);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public void setUsername(Username username) {
            this.username = username;
        }

        public Optional<Username> getUsername() {
            return Optional.ofNullable(username);
        }

        public void setSkillLevel(SkillLevel skillLevel) {
            this.skillLevel = skillLevel;
        }

        public Optional<SkillLevel> getSkillLevel() {
            return Optional.ofNullable(skillLevel);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditGameCommand.EditGameDescriptor)) {
                return false;
            }

            EditGameCommand.EditGameDescriptor otherEditGameDescriptor = (EditGameCommand.EditGameDescriptor) other;
            return Objects.equals(username, otherEditGameDescriptor.username)
                    && Objects.equals(skillLevel, otherEditGameDescriptor.skillLevel)
                    && Objects.equals(role, otherEditGameDescriptor.role);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("username", username)
                    .add("skillLevel", skillLevel)
                    .add("role", role)
                    .toString();
        }
    }
}
