package pl.mariodev.creditapp.core;


import pl.mariodev.creditapp.core.exception.RequirementNotMetCause;
import pl.mariodev.creditapp.core.model.PersonalData;

import java.math.BigDecimal;
import java.util.Optional;

import static pl.mariodev.creditapp.core.Constants.MIN_LOAN_AMOUNT_MORTGAGE;

public class CreditApplicationDecision {
    private final DecisionType type;
    private final Optional<RequirementNotMetCause> requirementNotMetCause;
    private final PersonalData personalData;
    private final Double creditRate;
    private final Integer scoring;

    public CreditApplicationDecision(DecisionType type, PersonalData personalData, Double creditRate, Integer scoring) {
        this.type = type;
        this.personalData = personalData;
        this.creditRate = creditRate;
        this.scoring = scoring;
        this.requirementNotMetCause = Optional.empty();
    }

    public CreditApplicationDecision(DecisionType type, PersonalData personalData, Double creditRate, Integer scoring, RequirementNotMetCause cause) {
        this.type = type;
        this.personalData = personalData;
        this.creditRate = creditRate;
        this.scoring = scoring;
        this.requirementNotMetCause = Optional.of(cause);
    }

    public String getDecisionString(){
        switch (type){
            case POSITIVE:
                return "Congratulations " + personalData.getName() + " " + personalData.getLastName() + ", decision is positive";
            case NEGATIVE_SCORING:
                return "Sorry " + personalData.getName() + " " + personalData.getLastName() + ", decision is negative";
            case CONTACT_REQUIRED:
                return "Sorry " + personalData.getName() + " " + personalData.getLastName() + ",  bank requires additional documents. Our Consultant will contact you.";
            case NEGATIVE_RATING:
                BigDecimal roundedCreditRate = new BigDecimal(creditRate).setScale(2);
                return "Sorry, " + personalData.getName() + " " + personalData.getLastName() + ", decision is negative. Bank can borrow only " + roundedCreditRate;
            case NEGATIVE_REQUIREMENTS_NOT_MET:
                switch (requirementNotMetCause.get()){
                    case TOO_HIGH_EXPENSES:
                        return "Sorry, " + personalData.getName() + " " + personalData.getLastName() + ", decision is negative. Personal expanses are to high";
                    case TOO_LOW_DOWN_AMOUNT:
                        return "Sorry, " + personalData.getName() + " " + personalData.getLastName() + ", decision is negative. Minimum loan amount for mortgage is " + MIN_LOAN_AMOUNT_MORTGAGE;
                }
        }

        return null;
    }

    public Optional<RequirementNotMetCause> getRequirementNotMetCause() {
        return requirementNotMetCause;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public Integer getScoring() {
        return scoring;
    }

    public Double getCreditRate() {
        return creditRate;
    }

    public DecisionType getType() {
        return type;
    }
}
