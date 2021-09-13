package org.frv.model;

/**
 * @author Roman V. Fedorin
 */
public enum CatalogType {
    ABC,
    AUTHOR,
    GENRE;

    public static CatalogType getTypeByName(String name) {
        switch (name) {
            case "author":
                return AUTHOR;
            case "genre":
                return GENRE;
            default:
                return ABC;
        }
    }
}
