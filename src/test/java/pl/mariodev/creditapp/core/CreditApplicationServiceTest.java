package pl.mariodev.creditapp.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mariodev.creditapp.core.exception.RequirementNotMetException;
import pl.mariodev.creditapp.core.exception.ValidationException;
import pl.mariodev.creditapp.core.model.CreditApplication;
import pl.mariodev.creditapp.core.model.CreditApplicationTestFactory;
import pl.mariodev.creditapp.core.model.Person;
import pl.mariodev.creditapp.core.scoring.ScoringCalculator;
import pl.mariodev.creditapp.core.validation.CompoundPostValidator;
import pl.mariodev.creditapp.core.validation.CreditApplicationValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class CreditApplicationServiceTest {

    @InjectMocks
    private CreditApplicationService cut;

    @Mock
    private ScoringCalculator scoringCalculatorMock;

    @Mock
    private CreditApplicationValidator creditApplicationValidatorMock;

    @Mock
    private PersonScoringCalculatorFactory personScoringCalculatorFactoryMock;

    @Mock
    private CreditRatingCalculator creditRatingCalculatorMock;

    @Mock
    private CompoundPostValidator compoundPostValidatorMock;

    @BeforeEach
    public void init() throws ValidationException, RequirementNotMetException {
        BDDMockito.given(personScoringCalculatorFactoryMock.getCalculator(any(Person.class)))
                .willReturn(scoringCalculatorMock);

        BDDMockito.doNothing()
                .when(creditApplicationValidatorMock)
                .validate(any(CreditApplication.class));

        BDDMockito.doNothing()
                .when(compoundPostValidatorMock)
                .validate(any(CreditApplication.class), anyInt(), anyDouble());
    }

    @Test
    @DisplayName("should return NEGATIVE_SCORING decision, when scoring is < 300")
    public void test1() {
        //given
        CreditApplication creditApplication = CreditApplicationTestFactory.create();
        BDDMockito.given(scoringCalculatorMock.calculate(eq(creditApplication)))
                .willReturn(100);

        //when
        CreditApplicationDecision decision = cut.getDecision(creditApplication);
        //then
        assertEquals(DecisionType.NEGATIVE_SCORING, decision.getType());

    }

    @Test
    @DisplayName("should return CONTACT_REQUIRED decision, when scoring is <= 400")
    public void test2() {
        //given
        CreditApplication creditApplication = CreditApplicationTestFactory.create();
        BDDMockito.given(scoringCalculatorMock.calculate(eq(creditApplication)))
                .willReturn(350);

        //when
        CreditApplicationDecision decision = cut.getDecision(creditApplication);
        //then
        assertEquals(DecisionType.CONTACT_REQUIRED, decision.getType());

    }

    @Test
    @DisplayName("should return NEGATIVE_RATING decision, when scoring is > 400 and credit rating > expected loan amount")
    public void test3() {
        //given
        CreditApplication creditApplication = CreditApplicationTestFactory.create(190000.00);
        BDDMockito.given(scoringCalculatorMock.calculate(eq(creditApplication)))
                .willReturn(450);

        BDDMockito.given(creditRatingCalculatorMock.calculate(eq(creditApplication))).
                willReturn(189000.00);

        //when
        CreditApplicationDecision decision = cut.getDecision(creditApplication);
        //then
        assertEquals(DecisionType.NEGATIVE_RATING, decision.getType());

    }

    @Test
    @DisplayName("should return POSITIVE decision, when scoring is > 400 and credit rating <= expected loan amount")
    public void test4() {
        //given
        CreditApplication creditApplication = CreditApplicationTestFactory.create(150_000.00);
        BDDMockito.given(scoringCalculatorMock.calculate(eq(creditApplication)))
                .willReturn(450);
        BDDMockito.given(creditRatingCalculatorMock.calculate(eq(creditApplication))).
                willReturn(151000.00);
        //when
        CreditApplicationDecision decision = cut.getDecision(creditApplication);
        //then
        assertEquals(DecisionType.POSITIVE, decision.getType());

    }

}