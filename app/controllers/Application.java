package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.data.Form;
import play.libs.F;
import play.libs.ws.WSResponse;
import play.mvc.*;
import play.libs.ws.WS;

import models.Person;

import views.html.*;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    public static class RegisterEntry {

        private final String hash;
        private final String mot_test;
        private final String vehicle;
        private final String result;
        private final String fuel_type;
        // added comment

        public RegisterEntry(String hash, String mot_test, String vehicle, String result, String fuel_type) {
            this.hash = hash;
            this.mot_test = mot_test;
            this.vehicle = vehicle;
            this.result = result;
            this.fuel_type = fuel_type;
        }

        public String getHash() {
            return hash;
        }

        public String getVehicle() {
            return vehicle;
        }

        public String getResult() {
            return result;
        }

        public String getMot_test() {
            return mot_test;
        }

        public String getFuel_type() {
            return fuel_type;
        }
    }

//        public static Result addPerson(){
//
//            Person person = Form.form(Person.class).bindFromRequest().get();
//
//            person.save();
//            return redirect(routes.index());
//        }

//    public static class RegisterEntry {
//
//        private final String hash;
//        private final String address;
//        private final String street;
//
//        public RegisterEntry(String hash, String address, String street) {
//            this.hash = hash;
//            this.address = address;
//            this.street = street;
//        }
//
//        public String getHash() {
//            return hash;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public String getStreet() {
//            return street;
//        }
//    }

    public F.Promise<Result> index() {
        /*
        return ok(index.render("Your new application is ready - JOHN."));
        */

//        F.Promise<WSResponse> wsResponsePromise = WS.url("http://address.openregister.org/all.json").get();
        F.Promise<WSResponse> wsResponsePromise = WS.url("http://mot-test.openregister.org/all.json").get();
        F.Promise<JsonNode> jsonNodePromise = wsResponsePromise.map(resp -> resp.asJson());

        F.Promise<List<RegisterEntry>> listPromise = jsonNodePromise.map(jsonNode -> transform(jsonNode));

        return listPromise.map(list -> ok(
                register.render(list)));

//        return ok(index.render(
//
//
//                "Your new application is ready - JOHN & Dai 3."));

    }

    public List<RegisterEntry> transform(JsonNode jsonNode) {

        List<RegisterEntry> Result = new ArrayList<>();

        for(JsonNode entry: jsonNode) {
            String hash = entry.get("hash").asText();
            JsonNode subEntry = entry.get("entry");
            String mot_test = subEntry.get("mot-test").asText();
            String vehicle = subEntry.get("vehicle").asText();
            String result = subEntry.get("result").asText();
            String fuel_type = subEntry.get("fuel-type").asText();
            Result.add(new RegisterEntry(hash, mot_test, vehicle, result, fuel_type));
        }

        return Result;
    }
//    public List<RegisterEntry> transform(JsonNode jsonNode) {
//
//        List<RegisterEntry> result = new ArrayList<>();
//
//        for(JsonNode entry: jsonNode) {
//            String hash = entry.get("hash").asText();
//            String address = entry.path("entry.address").asText();
//            String street = entry.path("entry.street").asText();
//            result.add(new RegisterEntry(hash, address, street, mot_test));
//        }
//
//        return result;
//    }
}
