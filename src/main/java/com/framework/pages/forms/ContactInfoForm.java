package com.framework.pages.forms;

import com.codeborne.selenide.ElementsContainer;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;


public class ContactInfoForm extends ElementsContainer {
    private final SelenideElement nameInput = $("#name");
    private final SelenideElement emailInput = $("#email");
    private final SelenideElement phoneInput = Selenide.$("#phone");
    private final SelenideElement subjectInput = Selenide.$("#subject");
    private final SelenideElement messageTextArea = Selenide.$("#description");
    private final SelenideElement submitButton = Selenide.$("#submitContact");

    @Step("Enter '{name}' value into 'Name' input")
    public void fillNameValue(String name) {
        nameInput.scrollTo();
        nameInput.val(name);
    }

    @Step("Enter '{phone}' value into 'Phone' input")
    public void fillPhoneNumberValue(String phone) {
        phoneInput.val(phone);
    }

    @Step("Enter '{email}' value into 'Email' input")
    public void fillEmailValue(String email) {
        emailInput.val(email);
    }

    @Step("Enter '{subject}' value into 'Subject' input")
    public void fillSubjectValue(String subject) {
        subjectInput.val(subject);
    }

    @Step("Enter '{message}' value into 'Message' text area")
    public void fillMessageValue(String message) {
        messageTextArea.val(message);
    }

    @Step("Click 'Submit' button")
    public void clickSubmitButton() {
        submitButton.shouldBe(enabled);
        submitButton.click();
    }

}
