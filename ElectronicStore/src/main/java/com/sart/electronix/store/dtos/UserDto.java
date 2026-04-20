package com.sart.electronix.store.dtos;

import com.sart.electronix.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 3, max = 25, message = "Invalid name!")
    private String name;

//    @Email(message = "Invalid User Email!")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid Email")
    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "Password is required!")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid Gender!")
    private String gender;

    @NotBlank(message = "Write something about yourself!")
    private String about;

    //@Pattern

    //Custom Validator
    @ImageNameValid
    private String imageName;

    private Set<RoleDto> roles = new HashSet<>();

}
