package com.sama_consulting.prolabmobile5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        VerifyLoginSettingsIncorrect.class,
        VerifyLoginSettingsCorrect.class,
        VerifyDateFunctionality.class,
        VerifySearchFunctionalityWithCorrectDate.class,
        VerifySearchFunctionalityWithCorrectID.class,
        VerifySearchFunctionalityWithWrongDate.class,
        VerifySearchFunctionalityWithWrongID.class,
        VerifyUnvalidateFunctionality.class,
        VerifyValidateFunctionality.class
})

public class CompleteTestSuite {
}
