package com.ratmirdudin.demo;

import com.ratmirdudin.demo.entities.Role;
import com.ratmirdudin.demo.entities.User;
import com.ratmirdudin.demo.repositories.RoleRepository;
import com.ratmirdudin.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = roleRepository.save(Role.builder().name("Admin").build());
        Role roleUser = roleRepository.save(Role.builder().name("User").build());

        userRepository.saveAll(List.of(
                        User.builder().firstName("Ratmir").lastName("Dudin").roles(Set.of(roleUser, roleAdmin)).build(),
                        User.builder().firstName("Vova").lastName("Dudin").roles(Set.of(roleUser)).build(),
                        User.builder().firstName("Donbass").lastName("Lobanov").roles(Set.of(roleUser)).build(),
                        User.builder().firstName("Benedict").lastName("Cumberbatch").roles(Set.of(roleUser)).build(),
                        User.builder().firstName("Bandersnatch").lastName("Cumminpatch").roles(Set.of(roleUser)).build(),
                        User.builder().firstName("Beneficial").lastName("Cucumber").roles(Set.of(roleUser)).build(),
                        User.builder().firstName("Cribblewack").lastName("Bumblerack").roles(Set.of(roleUser)).build(),
                        User.builder().firstName("Bandicoot").lastName("Cumbersnoop").roles(Set.of(roleUser)).build(),
                        User.builder().firstName("Candlebrink").lastName("Vandleclink").roles(Set.of(roleUser)).build()
                )
        );

    }
}
