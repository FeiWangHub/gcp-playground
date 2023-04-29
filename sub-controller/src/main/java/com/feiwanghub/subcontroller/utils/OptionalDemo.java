package com.feiwanghub.subcontroller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

public class OptionalDemo {

    @Data
    protected class User {
        private String name;
        private String email;
        private Optional<Address> optAddress;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    protected static class Address {
        private String city;
        private String country;

        public static Address getDefault() {
            return new Address("default city", "default country");
        }
    }

    /**
     * 1. create optional obj
     */
    public void createOptional() {
        // 1.1 create empty optional
        Optional<Address> optAddress = Optional.empty();

        // 1.2 create optional with non-null value
        Optional<Address> optAddress2 = Optional.of(new Address());

        // 1.3 create optional with nullable value
        Optional<Address> optAddress3 = Optional.ofNullable(new Address());
    }

    /**
     * 3. judge if the optional obj is present, by isPresent | ifPresent | ifPresentOrElse
     */
    public void isPresent() {
        Optional<Address> notNullOptional = Optional.of(new Address("GuangZhou", "CHINA"));
        System.out.println(notNullOptional.isPresent());//true
        notNullOptional.ifPresent(address -> System.out.println(address.getCity()));//will print
        notNullOptional.ifPresentOrElse(address -> System.out.println(address.getCity()), () -> System.out.println("empty"));//will print city

        Optional<Address> nullOptional = Optional.ofNullable(null);
        System.out.println(nullOptional.isPresent());//false
        nullOptional.ifPresentOrElse(address -> System.out.println(address.getCity()), () -> System.out.println("empty"));//will print empty
    }

    /**
     * 4. get value or default value, by  | orElseGet | orElseThrow
     * orElse(defaultObj) : if optional is null, return defaultObj
     * orElseGet(supplier) : if optional is null, return supplier.get()
     * orElseThrow(supplier) : if optional is null, throw exception
     */
    public void getValueOrDefaultValue() {
        //if not null
        Optional<Address> notNullOptional = Optional.of(new Address("GuangZhou", "CHINA"));
        System.out.println(notNullOptional.orElseGet(Address::getDefault).getCity());//original obj

        //if null
        Optional<Address> nullOptional = Optional.ofNullable(null);

        Address address = nullOptional.orElse(new Address("GuangZhou", "CHINA"));
        System.out.println(address.getCity());//GuangZhou

        Address address2 = nullOptional.orElseGet(() -> new Address("GuangZhou", "CHINA"));
        System.out.println(address2.getCity());//GuangZhou

        Address address3 = nullOptional.orElseGet(Address::getDefault);
        System.out.println(address3.getCity());//ShenZhen

        Address address4 = nullOptional.orElseThrow(() -> new RuntimeException("address is null"));
        System.out.println(address4.getCity());//GuangZhou
    }

    /**
     * 8 filter value
     * input of filter function, is a Predicate function
     * output of filter function, is an Optional obj
     */
    public void filterValue() {
        String password = "123456";
        Optional<String> passwordOptional = Optional.ofNullable(password);
        passwordOptional.filter(pwd -> pwd.length() >= 6).ifPresent(pwd -> System.out.println("password is valid"));

        //判断密码在6-20位之间 方案1
        passwordOptional.filter(pwd -> pwd.length() >= 6 && pwd.length() <= 20).ifPresent(pwd -> System.out.println("password is valid"));

        //判断密码在6-20位之间 方案2
        Predicate<String> len6 = pwd -> pwd.length() >= 6;
        Predicate<String> len20 = pwd -> pwd.length() <= 20;
        var result = passwordOptional.filter(len6.and(len20)).isPresent();
    }

    /**
     * 9 convert | transform value by map | flatMap
     */
    public void convertValue() {
        // get length of name
        String name = "name";
        Optional<String> nameOptional = Optional.ofNullable(name);
        Optional<Integer> intOp = nameOptional.map(String::length);
        System.out.println("length of name is " + intOp.orElse(0));

        // convert password to lowercase, and check if it is valid
        String password = "password";
        Optional<String> opt = Optional.ofNullable(password);

        Predicate<String> len6 = pwd -> pwd.length() > 6;
        Predicate<String> len10 = pwd -> pwd.length() < 10;
        Predicate<String> eq = pwd -> pwd.equals("password");

        boolean result = opt.map(String::toLowerCase).filter(len6.and(len10).and(eq)).isPresent();
        System.out.println(result);

        // map
        Optional<Address> addressOptional = Optional.ofNullable(new Address("GuangZhou", "CHINA"));
        Optional<String> optCity = addressOptional.map(Address::getCity);

        // flatmap
        User optUser = new User();
        Optional<User> userOptional = Optional.of(optUser);
        Optional<String> optCity2 = userOptional.flatMap(user -> user.getOptAddress().map(Address::getCity));
    }

    public static void main(String[] args) {
        OptionalDemo demo = new OptionalDemo();
        // demo.getValueOrDefaultValue();
        // demo.filterValue();
        demo.convertValue();
    }
}