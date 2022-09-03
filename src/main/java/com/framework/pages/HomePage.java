package com.framework.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.framework.models.contact_info.ContactInfo;
import com.framework.pages.forms.ContactInfoForm;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static com.framework.constants.SuccessMessage.CONTACT_INFO_CONFIRMATION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;


public class HomePage {
    private ContactInfoForm contactInfoForm;

    private final SelenideElement confirmationMessageTitle = Selenide.$(".row div div h2");
    private final SelenideElement confirmationMessageSubject = Selenide.$("div:nth-child(2) > div > p:nth-child(3)");
    private final SelenideElement errorMessageText = Selenide.$(".alert-danger");


    @Step("Open home page")
    public HomePage open() {
        Selenide.open("");
        return Selenide.page(HomePage.class);
    }

    @Step("Enter contact info and and press 'Submit' button")
    public HomePage sendContactInfo(ContactInfo contactInfo) {
        contactInfoForm.fillNameValue(contactInfo.name());
        contactInfoForm.fillEmailValue(contactInfo.email());
        contactInfoForm.fillPhoneNumberValue(contactInfo.phone());
        contactInfoForm.fillSubjectValue(contactInfo.subject());
        contactInfoForm.fillMessageValue(contactInfo.message());

        contactInfoForm.clickSubmitButton();

        return this;
    }

    @Step("Verify confirmation message title is displayed")
    public HomePage verifyConfirmationMessageTitleDisplayed(ContactInfo contactInfo) {
        Assertions.assertThat(confirmationMessageTitle.text())
                .as("Success message title is not correct")
                .isEqualTo(String.format(CONTACT_INFO_CONFIRMATION_MESSAGE, contactInfo.name()));

        return this;
    }

    @Step("Verify confirmation message subject is displayed")
    public HomePage verifyConfirmationMessageSubjectDisplayed(ContactInfo contactInfo) {
        Assertions.assertThat(confirmationMessageSubject.text())
                .as("Success message subject is not correct")
                .isEqualTo(contactInfo.subject());

        return this;
    }

    @Step("Verify error message is displayed")
    public HomePage verifyErrorMessageDisplayed(String errorMessage) {
        Assertions.assertThat(errorMessageText.text())
                .as("Error message is not correct")
                .contains(errorMessage);

        return this;
    }

}
