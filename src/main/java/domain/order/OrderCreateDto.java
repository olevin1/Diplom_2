package domain.order;

public final class OrderCreateDto {
    String[] ingredients;

    public OrderCreateDto(final String[] ingredients) {
        this.ingredients = ingredients;
    }

    public OrderCreateDto() {
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}