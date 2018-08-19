package domain;

public class Recipe extends Item {

    // TODO: Override toString -method.
    private final String description;

    public Recipe(int id, String name, String description) {
        super(id, name);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
