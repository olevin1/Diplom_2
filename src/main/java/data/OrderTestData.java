package data;

import org.apache.commons.lang3.RandomStringUtils;

public final class OrderTestData {
    /**
     * Ингредиенты для бургеров
     */
    //Булки для бургера
    public static final String FLUORESCENT_BUN = "61c0c5a71d1f82001bdaaa6d";
    public static final String KRATORNAYA_BUN = "61c0c5a71d1f82001bdaaa6c";
    //Соусы для бургера
    public static final String SPICY_X_SAUCE = "61c0c5a71d1f82001bdaaa72";
    public static final String SAUCE_SPECIAL_SPACE_SAUCE = "61c0c5a71d1f82001bdaaa73";
    public static final String SAUCE_TRADITIONAL_GALACTIC = "61c0c5a71d1f82001bdaaa74";
    public static final String ANTARIAN_FLATWALKER_SPIKED_SAUCE = "61c0c5a71d1f82001bdaaa75";
    //Начинки для бургера
    public static final String MEAT_OF_IMMORTAL_CLAMS_PROTOSTOMIA = "61c0c5a71d1f82001bdaaa6f";
    public static final String BEEF_METEORITE = "61c0c5a71d1f82001bdaaa70";
    public static final String MARTIAN_MAGNOLIA_BIO_CUTLET = "61c0c5a71d1f82001bdaaa71";
    public static final String FILLET_OF_LUMINESCENT_TETRAODONTIMFORM = "61c0c5a71d1f82001bdaaa6e";
    public static final String CRISPY_MINERAL_RINGS = "61c0c5a71d1f82001bdaaa76";
    public static final String FRUITS_OF_THE_FALLENIAN_TREE = "61c0c5a71d1f82001bdaaa77";
    public static final String MARTIAN_ALPHA_SACCHARIDE_CRYSTALS = "61c0c5a71d1f82001bdaaa78";
    public static final String MINI_SALAD_EXO_PLANTAGO = "61c0c5a71d1f82001bdaaa79";
    public static final String CHEESE_WITH_ASTEROID_MOLD = "61c0c5a71d1f82001bdaaa7a";

    /**
     * Комбо бургеров
     */
    public static final String[] BURGER_FIRST_CHOICE = {
            FLUORESCENT_BUN,
            SPICY_X_SAUCE,
            MEAT_OF_IMMORTAL_CLAMS_PROTOSTOMIA,
            CRISPY_MINERAL_RINGS
    };

    public static final String[] BURGER_SECOND_CHOICE = {
            FLUORESCENT_BUN,
            SAUCE_SPECIAL_SPACE_SAUCE,
            BEEF_METEORITE,
            FRUITS_OF_THE_FALLENIAN_TREE
    };
    public static final String[] BURGER_THIRD_CHOICE = {
            KRATORNAYA_BUN,
            SAUCE_TRADITIONAL_GALACTIC,
            MARTIAN_MAGNOLIA_BIO_CUTLET,
            MARTIAN_ALPHA_SACCHARIDE_CRYSTALS
    };
    public static final String[] BURGER_FOURTH_CHOICE = {
            KRATORNAYA_BUN,
            ANTARIAN_FLATWALKER_SPIKED_SAUCE,
            FILLET_OF_LUMINESCENT_TETRAODONTIMFORM,
            MINI_SALAD_EXO_PLANTAGO,
            CHEESE_WITH_ASTEROID_MOLD
    };
    public static final String[] EMPTY_INGREDIENTS = {};
    public static final String[] INVALID_INGREDIENTS = {RandomStringUtils.randomAlphanumeric(10, 15)};

    private OrderTestData() {
    }
}