package com.example.springbootemailverificationmysql.web;

import com.example.springbootemailverificationmysql.model.VerificationForm;
import com.example.springbootemailverificationmysql.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {
    @Autowired
    VerificationTokenService verificationTokenService;

    @GetMapping("/")
    public  String index(){
        return "redirect:/email-verification";
    }
    @GetMapping("/email-verification")
    public String formGet(Model model){
        model.addAttribute("verificationForm", new VerificationForm());
        return "verification-form";
    }
    @PostMapping("/email-verification")
    public String formPost(@Validated VerificationForm verificationForm, BindingResult bindingResult,Model model){
        if (!bindingResult.hasErrors()) {
            model.addAttribute("noErrors",true);
        }
        model.addAttribute("verificationForm",verificationForm);
        verificationTokenService.createVerification(verificationForm.getEmail());
        return "verification-form";
    }
    @GetMapping("/verify-email")
    @ResponseBody
    public String verifyEmail(String code){
        return verificationTokenService.verifyEmail(code).getBody();
    }
}
