package bg.softuni.travelProject.web;

import bg.softuni.travelProject.model.dto.forgotten_password.ResetPasswordData;
import bg.softuni.travelProject.model.dto.forgotten_password.ResetPasswordEmailDto;
import bg.softuni.travelProject.service.ForgottenPasswordService;
import bg.softuni.travelProject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/password")
public class PasswordResetController {

    private final UserService userService;
    private final ForgottenPasswordService forgottenPasswordService;
    private final LocaleResolver localeResolver;

    public PasswordResetController(UserService userService,
                                   ForgottenPasswordService forgottenPasswordService,
                                   LocaleResolver localeResolver) {
        this.userService = userService;
        this.forgottenPasswordService = forgottenPasswordService;
        this.localeResolver = localeResolver;
    }

    @ModelAttribute("emailDto")
    public ResetPasswordEmailDto resetPasswordEmailDto() {
        return new ResetPasswordEmailDto();
    }

    @GetMapping("/reset")
    public String resetPassword() {
        return "reset-password-request";
    }

    @PostMapping("/reset")
    public String sendResetPasswordEmail(@Valid ResetPasswordEmailDto emailDto,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes,
                                         HttpServletRequest request,
                                         Model model) {

        if (!userService.emailExists(emailDto.getEmail())) {
            bindingResult.addError(new FieldError("emailDto", "email", emailDto.getEmail(), false, null, null,
                    "User with email " + emailDto.getEmail() + " does not  exist!"));
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("emailDto", emailDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.emailDto",
                    bindingResult);
            return "redirect:/password/reset";
        }

        this.forgottenPasswordService.sendResetPasswordEmail(emailDto.getEmail(), localeResolver.resolveLocale(request));
        model.addAttribute("message", "Email with reset password link was sent to " + emailDto.getEmail());

        return "message";
    }

    @GetMapping("/change")
    public String changePassword(@RequestParam(required = false) String token,
                                 Model model) {
        if (token == null) {
            model.addAttribute("message", "Token is empty. Please make sure to copy the entire URL");
            return "message";
        }

        if (forgottenPasswordService.invalidToken(token)) {
            model.addAttribute("message", "This token is invalid");
            return "message";
        }

        if (!model.containsAttribute("data")) {
            ResetPasswordData passwordData = new ResetPasswordData();
            passwordData.setToken(token);
            model.addAttribute("data", passwordData);
        }

        return "change-password";
    }

    @PostMapping("/change")
    public String changePassword(@Valid ResetPasswordData data,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("data", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.data",
                    bindingResult);
            return String.format("redirect:/password/change?token=%s", data.getToken());
        }

        forgottenPasswordService.updatePassword(data.getPassword(), data.getToken());

        redirectAttributes.addFlashAttribute("successMessage",
                "Your password was successfully changed. You can now login into your account.");
        return "redirect:/users/login";
    }
}