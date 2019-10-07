package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String showProfile(Principal principal, Model model){

        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user",user);

        return "user_profile";
    }
    @PostMapping("")
    public String updateProfile(@ModelAttribute("user") User userForm, Principal principal, Model model)
    {
        User user = userRepository.findByEmail(principal.getName());
        user.setName(userForm.getName());
        user.setSurname(userForm.getSurname());
        user.setCity(userForm.getCity());
        user.setPhoneNumber(userForm.getPhoneNumber());
        model.addAttribute("user",user);
        return "user_profile";
    }
    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {

        User user = userRepository.findByEmail(principal.getName());

        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        BufferedImage bufferedImage = ImageIO.read(convFile);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"png", bos);
        convFile.delete();

        byte[] data = bos.toByteArray();

        user.setImage(data);

        userRepository.save(user);

        return "redirect:/user";
    }

}
