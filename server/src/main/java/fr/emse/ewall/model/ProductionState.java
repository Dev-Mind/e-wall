package fr.emse.ewall.model;

/**
 * Each production is read and validated by a moderator
 */
public enum ProductionState {
    PENDING,
    VALIDATED,
    CENSORED,
    CATEGORY
}
