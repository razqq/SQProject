package com.example.demo.validators;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Constants {

    @AllArgsConstructor
    @Getter
    public enum TEACHERS {
        RADULESCU_VLAD("Radulescu Vlad"),
        IFTENE_ADRIAN("Iftene Adrian");

        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum CLASS_TYPE {
        LABORATORY("Laboratory"),
        SEMINARY("Seminary"),
        COURSE("Course");

        private final String type;
    }

    @AllArgsConstructor
    @Getter
    public enum ROOM_TYPE {
        LABORATORY("Laboratory"),
        SEMINARY("Seminary"),
        COURSE("Course");

        private final String type;
    }

    @AllArgsConstructor
    @Getter
    public enum ROOM_NAME {
        C401("C401"),
        C2("C2"),
        C203("C203"),
        C411("C411");

        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum GROUP_NAME {
        A1("A1"),
        A2("A2");

        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum SUBJECT {
        TEHNOLOGII_WEB("Tehnologii Web"),
        PROGRAMARE_AVANSATA("Programare avansata"),
        SISTEME_EMBEDDED("Sisteme Embedded");

        private final String name;
    }
}
